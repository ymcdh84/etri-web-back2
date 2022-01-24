package com.iljin.apiServer.core.files;

import com.iljin.apiServer.core.config.FileStorageConfig;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import com.iljin.apiServer.core.util.Util;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private final Util util;

    private final Path fileStorageLocation;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FileServiceImpl(Util util, FileRepository fileRepository, FileStorageConfig fileStorageConfig,
                           UserRepository userRepository, ModelMapper modelMapper) {
        this.util = util;
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileException("파일이 저장 될 디렉토리를 생성하지 못했습니다.", e);
        }
    }

    @Override
    public String getOriginalFileName(Long id) {
        Optional<File> uploadedFile = fileRepository.findById(id);
        return uploadedFile.map(uploadFile -> uploadFile.originalName).orElse("fileNameError");
    }

    @Override
    public String getOriginalFileNameByStoredFileName(String fileName) {
        Optional<File> uploadedFile = fileRepository.findByStoredName(fileName);
        return uploadedFile.map(uploadFile -> uploadFile.originalName).orElse("fileNameError");
    }

    @Override
    public File storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String ext = fileName.substring(fileName.lastIndexOf("."));

        UUID uuid = UUID.randomUUID();
        String storedName = uuid.toString() + ext;

        try {
            if (fileName.contains("..")) {
                throw new FileException("파일명에 허용되지 않는 문자가 포함되어 있습니다." + fileName);
            }

            final Path targetLocation = this.fileStorageLocation.resolve(storedName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filePathSave(fileName, storedName);

        } catch (final IOException ex) {
            throw new FileException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.", ex);
        }
    }

//    @Override
//    public File storeFileWithDocumentId(MultipartFile file, Long id) {
//        File result = storeFile(file);
//        result.updateDocumentHId(id);
//        fileRepository.save(result);
//
//        return result;
//    }
//
//    @Override
//    public File storeFileWithDocumentIdAndFileType(MultipartFile file, Long id, String fileType, Long seq) {
//        File result = storeFile(file);
//        result.updateDocumentHIdAndFileTypeAndSeq(id, fileType, seq);
//        fileRepository.save(result);
//
//        return result;
//    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("파일을 찾을 수 없습니다." + fileName);
            }

        } catch (final MalformedURLException ex) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다." + ex);
        }
    }

    @Override
    public Optional<File> getUploadFileInfo(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<File> readAllUploadedFiles(String uploadDir) {
        List fileList = new ArrayList<java.io.File>();
        try {
            Files.list(Paths.get(uploadDir)).filter(Files::isRegularFile).forEach(c -> fileList.add(c.toFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    @Override
    public List<File> readDailyUploadedFiles(String uploadDir) {
        // 일단위로 수정된 파일 찾기
        Instant lastDay = Instant.now().minus(1, ChronoUnit.DAYS);
        final List fileList = new ArrayList<java.io.File>();

        try {
            Files.list(Paths.get(uploadDir)).filter(Files::isRegularFile).filter(path -> {
                try {
                    return Files.getLastModifiedTime(path).toInstant().isAfter(lastDay);
                } catch (final IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }).forEach(c -> fileList.add(c.toFile()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    @Override
    public void copyFile(java.io.File source, java.io.File dest) throws IOException {
        // Only 1GB under file support
        Files.copy(source.toPath(), dest.toPath());
    }

    @Override
    public java.io.File getFileWithPathString(String filePathString) {
        return new java.io.File(filePathString);
    }

//    @Override
//    public boolean updateDocumentHId(Long fileId, Long documentHId) {
//        Optional<File> uploadedFile = fileRepository.findById(fileId);
//        return uploadedFile.map(uploadFile -> {
//            uploadFile.updateDocumentHId(documentHId);
//            fileRepository.save(uploadFile);
//            return true;
//        }).orElse(false);
//    }

    @Override
    public void deleteById(Long id) {
        fileRepository.findById(id).ifPresent(c -> {
            java.io.File file = new java.io.File(this.fileStorageLocation + "/" + c.getStoredName());
            file.delete();

            fileRepository.deleteById(id);
        });
    }

//    @Override
//    public void updateRemark(Long id, String remark, Long seq) {
//        Optional<File> file = fileRepository.findById(id);
//        file.ifPresent(c -> {
//            c.updateRemarkAndSeq(remark, seq);
//            fileRepository.save(c);
//        });
//    }

    @Override
    public Optional<String> getDownloadUrlFromOriginalFileName(String originalName) {
        Optional<File> file = fileRepository.findFirstByOriginalNameContains(originalName);

        if (file.isPresent()) {
            return Optional.of(file.map(File::getDownloadUrl).get());
        } else return Optional.empty();
    }

    @Override
    public List<FileDto> findByOriginalFileName(String fileName) {
        List<File> fileList = fileRepository.findAllByOriginalNameContains(fileName);
        List<FileDto> result = new ArrayList<>();
        for (File file : fileList) {
            FileDto fileDto = modelMapper.map(file, FileDto.class);
            Optional<User> user = userRepository.findById(fileDto.getCreatedBy());
            if (user.isPresent()) {
                fileDto.setCreator(user.get().getUserName());
            }
            result.add(fileDto);
        }
        return result;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String storedFileName, Long id, HttpServletRequest request) {
        String contentType = null;
        Resource resource = null;
        String originalFileName = null;

        try {
            if(id != null) {
                originalFileName = this.getOriginalFileName(id);
            } else {
                originalFileName = this.getOriginalFileNameByStoredFileName(storedFileName);
            }

            resource = this.loadFileAsResource(storedFileName);
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(resource);
    }

    private String getDownloadFileUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/asset/download/")
                .path(fileName)
                .toUriString();
    }

    private File filePathSave(String originalName, String storedName) {
        final File uf = new File(originalName, storedName, getDownloadFileUri(storedName));
        fileRepository.save(uf);

        return uf;
    }

    @Override
    public List<File> uploadAssetFiles(FileDto ufileDto) {
        List<File> list = new ArrayList<>();

        User loginUser = util.getLoginUser();
        String compCd = loginUser.getCompCd();
        String loginId = loginUser.getLoginId();

        String documentHId = ufileDto.getDocumentHId();//문서관리번호
        String fileType = "";

        List<MultipartFile> files = ufileDto.getFiles();

        for(MultipartFile file : files) {
            String uploadFileName =file.getOriginalFilename();
            //IE has file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileName));
            String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            Long fileSize = file.getSize();

            UUID uuid = UUID.randomUUID();
            String storedName = uuid.toString() + ext;

            try {
                if(fileName.contains("..")) {
                    throw new FileNotFoundException("파일명에 허용되지 않는 문자가 포함되어 있습니다." + fileName);
                }

                /*
                 * File IO on Server
                 * */
                Path targetLocation = this.fileStorageLocation.resolve(storedName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                if(!ext.equals(".pdf") && !ext.equals(".tif") && !ext.equals(".tiff") &&
                        !ext.equals(".xls") && !ext.equals(".xlsx") && !ext.equals(".doc") && !ext.equals(".docx")) {
                    /*
                     * create Thumbnail image
                     * */
                    Thumbnails.of(new java.io.File(String.valueOf(targetLocation)))
                            .size(37, 35)
                            .toFile(new java.io.File(String.valueOf(this.fileStorageLocation.resolve("thumbnail-" +storedName))));
                }

                /*
                 * Insert Data to U_FILE Table
                 * */
                File uf = new File();

                uf.setCompCd(compCd);
                uf.setFileType(fileType);
                uf.setDocumentHId(documentHId);
                uf.setSeq(1L);

                uf.setOriginalName(fileName);
                uf.setStoredName(storedName);
                uf.setDownloadUrl(getDownloadFileUri(storedName));
                uf.setFileCat(ext);
                uf.setFileSize(fileSize);

                if(!ext.equals(".pdf") && !ext.equals(".tif") && !ext.equals(".tiff") &&
                        !ext.equals(".xls") && !ext.equals(".xlsx") && !ext.equals(".doc") && !ext.equals(".docx")) {
                    uf.setAttribute1("thumbnail-" +storedName);
                    uf.setAttribute2(getDownloadFileUri("thumbnail-" +storedName));
                }

//                uf.setCreatedBy(Long.valueOf(loginId));
//                uf.setCreationDate(LocalDateTime.now());
//                uf.setModifiedBy(Long.valueOf(loginId));
//                uf.setModifiedDate(LocalDateTime.now());

                fileRepository.save(uf);

                list.add(uf);
            } catch(IOException ex) {
                throw new FileNotFoundException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.", ex);
            }
        }
        return list;
    }

    @Override
    public ResponseEntity<String> deleteAssetFiles(FileDto ufileDto) {
        User loginUser = util.getLoginUser();
        String compCd = loginUser.getCompCd();
        String loginId = loginUser.getLoginId();

        String documentHId = ufileDto.getDocumentHId();

        List<File> list = fileRepository.findByCompCdAndDocumentHId(compCd, documentHId);

        for(File ufile : list) {
            fileRepository.deleteById(ufile.getId());

            String storedName = ufile.getStoredName();
            String attr1 = ufile.getAttribute1();
            String ext = ufile.getFileCat();

            Path targetLocation = this.fileStorageLocation.resolve(storedName);
            Path targetLocation2 = null;
            if(attr1 != null) {
                targetLocation2 = this.fileStorageLocation.resolve(attr1);
            }
            try {
                Files.delete(targetLocation);
                if(!ext.equals(".pdf") && !ext.equals(".tif") && !ext.equals(".tiff") &&
                        !ext.equals(".xls") && !ext.equals(".xlsx") && !ext.equals(".doc") && !ext.equals(".docx")) {
                    Files.delete(targetLocation2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @Override
    public String getOriginalFileNameByChangedFileName(String fileName) {

        Optional<File> ufile = fileRepository.findByStoredName(fileName);
        return ufile.map(uploadFile -> uploadFile.originalName)
                .orElse("fileNameError");
    }
}
