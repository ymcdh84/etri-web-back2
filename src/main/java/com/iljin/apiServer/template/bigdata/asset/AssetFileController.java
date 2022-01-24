package com.iljin.apiServer.template.bigdata.asset;

import com.iljin.apiServer.core.files.File;
import com.iljin.apiServer.core.files.FileDto;
import com.iljin.apiServer.core.files.FileException;
import com.iljin.apiServer.core.files.FileService;
import com.iljin.apiServer.core.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/asset")
public class AssetFileController {

    private final FileService ufileService;

    @Autowired
    public AssetFileController(FileService ufileService) {
        this.ufileService = ufileService;
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<Error> uFileNotFound(FileException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * 자산 이미지 파일 업로드
     * addFiles
     * @param files
     * @param documentHId
     * Desc. ufileDto 다음과 같은 값들을 받는다.
     * 1. 증빙파일들(Multipart files) - files
     * 2. 자산ID - documentHId (문서 저장 전: Empty)
     * */
    @PostMapping(value = {"/addFiles", "/addFiles/{documentHId}"})
    public ResponseEntity<List<File>> uploadAssetFiles(@PathVariable(required = false) String documentHId,
                                                          MultipartFile[] files) {
        List<MultipartFile> fList = new ArrayList<>();
        for(MultipartFile file : files) {
            fList.add(file);
        }

        FileDto ufileDto = new FileDto();
        ufileDto.setDocumentHId(documentHId);
        ufileDto.setFiles(fList);
        List<File> list = ufileService.uploadAssetFiles(ufileDto);

        return new ResponseEntity(list, HttpStatus.OK);
    }

    /**
     * 자산 이미지 파일 삭제
     * removeFiles
     * @param fileDto
     * */
    @PostMapping("/removeFiles")
    public ResponseEntity<String> deleteAssetFiles(@RequestBody FileDto fileDto) {
        return ufileService.deleteAssetFiles(fileDto);
    }


    /**
     * 자산 이미지 파일 다운로드
     * */
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadAssetFile(@PathVariable String fileName
            , @RequestParam(value = "id", required = false) Long id
            , HttpServletRequest request) throws UnsupportedEncodingException {

        String contentType = null;
        Resource resource = null;
        String originalFileName = null;

        try {
            if(id != null) {
                originalFileName = ufileService.getOriginalFileName(id);
            } else {
                originalFileName = ufileService.getOriginalFileNameByChangedFileName(fileName);
            }

            resource = ufileService.loadFileAsResource(fileName);
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        HttpHeaders headers = new HttpHeaders();

        if(contentType == "application/pdf"){
            contentType += "; charset=UTF-8";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"");
        }else{
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"");
        }

        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(resource);
    }

}
