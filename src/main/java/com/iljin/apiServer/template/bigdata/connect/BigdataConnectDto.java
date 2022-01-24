package com.iljin.apiServer.template.bigdata.connect;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BigdataConnectDto implements Serializable {
	String siteId;
	String connectId;
	String connectNm;
	String connectTypeCd;
	String connectStatCd;
	String programNm;
	String ip;
	String port;
	String loginId;
	String password;
	LocalDateTime startDateTime;
	LocalDateTime endDateTime;

	/*
	* 연결 조회
	* */
	public BigdataConnectDto(String siteId
							, String connectId
							, String connectNm
							, String connectTypeCd
							, String connectStatCd
							, String programNm
							, String ip
							, String port
							, String loginId
							, String password
							, LocalDateTime startDateTime
							, LocalDateTime endDateTime)
	{
		this.siteId = siteId;
		this.connectId = connectId;
		this.connectNm = connectNm;
		this.connectTypeCd = connectTypeCd;
		this.connectStatCd = connectStatCd;
		this.programNm = programNm;
		this.ip = ip;
		this.port = port;
		this.loginId = loginId;
		this.password = password;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	//카프카 연결 방법 사이트 조회
	String siteNm;

	public BigdataConnectDto(String siteId
							, String siteNm
							, String connectId
							, String connectNm
							, String programNm
							)
	{
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.connectId = connectId;
		this.connectNm = connectNm;
		this.programNm = programNm;
	}
}

