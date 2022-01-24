package com.iljin.apiServer.core.files;

import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
public interface FileService {
    String getOriginalFileName(Long id);
    String getOriginalFileNameByStoredFileName(String fileName);
    File storeFile(MultipartFile file);
//    File storeFileWithDocumentId(MultipartFile file, Long id);
//    File storeFileWithDocumentIdAndFileType(MultipartFile file, Long id, String fileType, Long seq);
    Resource loadFileAsResource(String fileName);
    Optional<File> getUploadFileInfo(Long id);
    List<File> readAllUploadedFiles(String uploadDir);
    List<File> readDailyUploadedFiles(String uploadDir);
    void copyFile(java.io.File source, java.io.File dest) throws IOException;
    java.io.File getFileWithPathString(String filePathString);
//    boolean updateDocumentHId(Long fileId, Long documentHId);
    void deleteById(Long id);
//    void updateRemark(Long id, String remark, Long seq);
    Optional<String> getDownloadUrlFromOriginalFileName(String originalName);
    List<FileDto> findByOriginalFileName(String fileName);

    ResponseEntity<Resource> downloadFile(String storedFileName, Long id, HttpServletRequest request);

    //자산 Image 파일
    @Modifying
    @Transactional
    List<File> uploadAssetFiles(FileDto ufileDto);

    @Modifying
    @Transactional
    ResponseEntity<String> deleteAssetFiles(FileDto ufileDto);

    String getOriginalFileNameByChangedFileName(String fileName);
}
