package com.iljin.apiServer.template.system.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_code_hd")
@IdClass(CodeHeaderKey.class)
public class CodeHeader {
	
	@Id
	@Column(name="group_cd", nullable=false)
	String groupCd;

	@Id
	@Column(name="comp_cd", nullable=false)
	String compCd;

	@OneToMany(mappedBy = "codeHeader", fetch = FetchType.LAZY)
	@JsonIgnore
	List<CodeDetail> codeDetails;
	
	@Column(name = "group_nm")
	String groupNm;

	@Column(name = "group_desc")
	String groupDesc;

	@Column(name = "use_yn")
	String useYn;

	@Column(name = "reg_id")
	String regId;

	@CreationTimestamp
	@Column(name = "reg_dtm")
	LocalDateTime regDtm;

	@Column(name = "chg_id")
	String chgId;

	@UpdateTimestamp
	@Column(name = "chg_dtm")
	LocalDateTime chgDtm;

	@Builder
	public CodeHeader(String groupCd, String compCd, String groupNm, String groupDesc, String useYn, String regId, LocalDateTime regDtm, String chgId, LocalDateTime chgDtm) {
		this.groupCd = groupCd;
		this.compCd = compCd;
		this.groupNm = groupNm;
		this.groupDesc = groupDesc;
		this.useYn = useYn;
		this.regId = regId;
		this.regDtm = regDtm;
		this.chgId = chgId;
		this.chgDtm = chgDtm;
	}
	
	public CodeHeader updateCodeHeader(String groupNm, String useYn, String groupDesc, String chgId, LocalDateTime chgDtm) {
		this.groupNm = groupNm;
		this.useYn = useYn;
		this.groupDesc = groupDesc;
		this.chgId = chgId;
		this.chgDtm = chgDtm;

		return this;
	}

	public CodeHeader setCodeHeader(String regId, LocalDateTime regDtm, String chgId, LocalDateTime chgDtm) {
		this.regId = regId;
		this.regDtm = regDtm;
		this.chgId = chgId;
		this.chgDtm = chgDtm;

		return this;
	}
}
