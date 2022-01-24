package com.iljin.apiServer.template.system.code;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class CodeDto implements Serializable {
	String groupCd;
	String groupNm;
	String groupDesc;
	String compCd;
	String detailCd;
	String detailNm;
	String useYn;
	Integer orderSeq;
	String detailDesc;
	String remark1;
	String remark2;
	String remark3;
	String remark4;
	String remark5;

	/*
	* EA-06-01 공통코드관리
	* 그룹코드 조회
	* */
	public CodeDto(String compCd, String groupCd, String groupNm, String useYn, String groupDesc) {
		this.compCd = compCd;
		this.groupCd = groupCd;
		this.groupNm = groupNm;
		this.useYn = useYn;
		this.groupDesc = groupDesc;
	}
	/*
	* EA-06-01 공통코드관리
	* 그룹코드 클릭 event
	* 상세코드 조회
	* */
	public CodeDto(String compCd, String groupCd, String detailCd, String detailNm, String useYn
			, Integer orderSeq
			, String remark1, String remark2, String remark3, String remark4, String remark5
			, String detailDesc) {
		this.compCd = compCd;
		this.groupCd = groupCd;
		this.detailCd = detailCd;
		this.detailNm = detailNm;
		this.useYn = useYn;
		this.orderSeq = orderSeq;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
		this.remark5 = remark5;
		this.detailDesc = detailDesc;
	}
}
