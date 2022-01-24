package com.iljin.apiServer.core.files;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FileResponse {
    private Long id;
    private String originalName;
    private String storedName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public FileResponse(Long id, String originalName, String storedName, String fileDownloadUri, String fileType, Long size) {
        this.id = id;
        this.originalName = originalName;
        this.storedName = storedName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
