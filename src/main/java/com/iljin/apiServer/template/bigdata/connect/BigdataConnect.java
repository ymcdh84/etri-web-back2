package com.iljin.apiServer.template.bigdata.connect;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_bigdata_connect")
@IdClass(BigdataConnectKey.class)
public class BigdataConnect {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "connect_id")
	String connectId;

	@Column(name = "connect_nm")
	String connectNm;

	@Column(name = "connect_type_cd")
	String connectTypeCd;

	@Column(name = "connect_stat_cd")
	String connectStatCd;

	@Column(name = "program_nm")
	String programNm;

	@Column(name = "ip")
	String ip;

	@Column(name = "port")
	String port;

	@Column(name = "login_id")
	String loginId;

	@Column(name = "password")
	String password;

	@Column(name = "start_date_time")
	LocalDateTime startDateTime;

	@Column(name = "end_date_time")
	LocalDateTime endDateTime;

	@Builder
	public BigdataConnect(String siteId
			            , String connectId
			            , String connectNm
			            , String connectTypeCd
			            , String connectStatCd
			            , String programNm
			            , String ip
			            , String port
			            , String loginId
			            , String password
	) {
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
	}

	public BigdataConnect updateBigdataConnect( String connectNm
			                                  , String connectTypeCd
			                                  , String connectStatCd
                                              , String programNm
                                              , String ip
                                              , String port
                                              , String loginId
                                              , String password
	) {
		this.connectNm = connectNm;
		this.connectTypeCd = connectTypeCd;
		this.connectStatCd = connectStatCd;
		this.programNm = programNm;
		this.ip = ip;
		this.port = port;
		this.loginId = loginId;
		this.password = password;

		return this;
	}
}
