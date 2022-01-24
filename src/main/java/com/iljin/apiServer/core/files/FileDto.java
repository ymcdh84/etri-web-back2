package com.iljin.apiServer.core.files;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FileDto {
    //id
    Long id;
    //회사코드
    String compCd;
    //관리구분(모바일,전표,예산)
    String fileType;
    //관리번호(전표,예산)
    String documentHId;
    //파일설명
    String remark;
    //파일순서
    Long seq;
    //원본파일명
    String originalName;
    //저장파일명
    String storedName;
    //파일경로
    String downloadUrl;
    //파일종류 - 추가
    String fileCat;
    //용량(Byte) - 추가
    Long fileSize;
    //썸네일_저장파일명
    String attribute1;
    //썸네일_파일경로
    String attribute2;
    //원본 관리구분(모바일)
    String attribute3;
    //비고4
    String attribute4;
    //비고5
    String attribute5;

    Long createdBy;
    String creator;
    LocalDateTime creationDate;
    Long modifiedBy;
    LocalDateTime modifiedDate;

    /*
     * upload Multipart files
     * */
    List<MultipartFile> files;
}
