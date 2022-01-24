package com.iljin.apiServer.template.file;

import com.iljin.apiServer.core.files.File;
import com.iljin.apiServer.core.files.FileResponse;
import com.iljin.apiServer.core.files.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        File uFile = fileService.storeFile(file);

        return new FileResponse(uFile.getId(),
                uFile.getOriginalName(),
                uFile.getStoredName(),
                uFile.getDownloadUrl(),
                file.getContentType(), file.getSize());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFileById(@PathVariable String id) {
        fileService.deleteById(Long.parseLong(id));

        return new ResponseEntity<>("파일이 삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/upload/files")
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{storedFileName:.*}")
    public ResponseEntity<Resource> downloadByStoredName(@PathVariable String storedFileName, @RequestParam(value = "id", required = false) Long id, HttpServletRequest request) {
        return fileService.downloadFile(storedFileName, id, request);
    }
}
