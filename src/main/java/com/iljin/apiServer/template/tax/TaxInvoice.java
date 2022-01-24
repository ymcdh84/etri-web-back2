package com.iljin.apiServer.template.tax;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_tax_invoice")
public class TaxInvoice {

	@Id
	@Column(name="issue_id", nullable=false)
	String issueId;

	@Column(name = "exch_doc_id")
	String exchDocId;

	@Column(name = "exch_doc_refer_id")
	String exchDocReferId;

	@Column(name = "exch_doc_issue_date_time")
	String exchDocIssueDateTime;

	@Column(name = "tax_inv_doc_issue_id")
	String taxInvDocIssueId;

	@Column(name = "tax_inv_doc_issue_date_time")
	String taxInvDocIssueDateTime;

	@Column(name = "tax_inv_doc_type_code")
	String taxInvDocTypeCode;

	@Column(name = "tax_inv_doc_purpose_code")
	String taxInvDocPurposeCode;

	@Column(name = "tax_inv_doc_amend_code")
	String taxInvDocAmendCode;

	@Column(name = "tax_inv_doc_ori_id")
	String taxInvDocOriId;

	@Column(name = "tax_inv_doc_desc")
	String taxInvDocDesc;

	@Column(name = "tax_inv_doc_ref_id")
	String taxInvDocRefId;

	@Column(name = "tax_inv_doc_ref_accept_st_date_time")
	String taxInvDocRefAcceptStDateTime;

	@Column(name = "tax_inv_doc_ref_accept_end_date_time")
	String taxInvDocRefAcceptEndDateTime;

	@Column(name = "tax_inv_doc_ref_item_quan")
	BigDecimal taxInvDocRefItemQuan;

	@Column(name = "invoicer_id")
	String invoicerId;

	@Column(name = "invoicer_tax_reg_id")
	String invoicerTaxRegId;

	@Column(name = "invoicer_name")
	String invoicerName;

	@Column(name = "invoicer_specified_person")
	String invoicerSpecifiedPerson;

	@Column(name = "invoicer_specified_addr")
	String invoicerSpecifiedAddr;

	@Column(name = "invoicer_type_code")
	String invoicerTypeCode;

	@Column(name = "invoicer_classification_code")
	String invoicerClassificationCode;

	@Column(name = "invoicer_define_dept")
	String invoicerDefineDept;

	@Column(name = "invoicer_define_person")
	String invoicerDefinePerson;

	@Column(name = "invoicer_define_tel")
	String invoicerDefineTel;

	@Column(name = "invoicer_define_uri")
	String invoicerDefineUri;

	@Column(name = "invoicee_id")
	String invoiceeId;

	@Column(name = "invoicee_business_type_code")
	String invoiceeBusinessTypeCode;

	@Column(name = "invoicee_tax_reg_id")
	String invoiceeTaxRegId;

	@Column(name = "invoicee_name")
	String invoiceeName;

	@Column(name = "invoicee_specified_person")
	String invoiceeSpecifiedPerson;

	@Column(name = "invoicee_specified_addr")
	String invoiceeSpecifiedAddr;

	@Column(name = "invoicee_type_code")
	String invoiceeTypeCode;

	@Column(name = "invoicee_classification_code")
	String invoiceeClassificationCode;

	@Column(name = "invoicee_pri_dept")
	String invoiceePriDept;

	@Column(name = "invoicee_pri_person")
	String invoiceePriPerson;

	@Column(name = "invoicee_pri_tel")
	String invoiceePriTel;

	@Column(name = "invoicee_pri_uri")
	String invoiceePriUri;

	@Column(name = "invoicee_second_dept")
	String invoiceeSecondDept;

	@Column(name = "invoicee_second_person")
	String invoiceeSecondPerson;

	@Column(name = "invoicee_second_tel")
	String invoiceeSecondTel;

	@Column(name = "invoicee_scond_uri")
	String invoiceeScondUri;

	@Column(name = "broker_id")
	String brokerId;

	@Column(name = "broker_tax_reg_id")
	String brokerTaxRegId;

	@Column(name = "broker_name")
	String brokerName;

	@Column(name = "broker_specified_person")
	String brokerSpecifiedPerson;

	@Column(name = "broker_specified_addr")
	String brokerSpecifiedAddr;

	@Column(name = "broker_type_code")
	String brokerTypeCode;

	@Column(name = "broker_classification_code")
	String brokerClassificationCode;

	@Column(name = "broker_define_dept")
	String brokerDefineDept;

	@Column(name = "broker_define_person")
	String brokerDefinePerson;

	@Column(name = "broker_define_tel")
	String brokerDefineTel;

	@Column(name = "broker_define_uri")
	String brokerDefineUri;

	@Column(name = "pay_type_code")
	String payTypeCode;

	@Column(name = "pay_amt")
	BigDecimal payAmt;

	@Column(name = "charge_total_amt")
	BigDecimal chargeTotalAmt;

	@Column(name = "tax_total_amt")
	BigDecimal taxTotalAmt;

	@Column(name = "grand_total_amt")
	BigDecimal grandTotalAmt;

	@Column(name = "reg_id")
	String regId;

	@Column(name = "reg_dtm")
	LocalDateTime regDtm;

	@Column(name = "chg_id")
	String chgId;

	@Column(name = "chg_dtm")
	LocalDateTime chgDtm;

	@Builder
	public TaxInvoice(String issueId
					, String exchDocId
					, String exchDocReferId
					, String exchDocIssueDateTime
					, String taxInvDocIssueId
					, String taxInvDocIssueDateTime
					, String taxInvDocTypeCode
					, String taxInvDocPurposeCode
					, String taxInvDocAmendCode
					, String taxInvDocOriId
					, String taxInvDocDesc
					, String taxInvDocRefId
					, String taxInvDocRefAcceptStDateTime
					, String taxInvDocRefAcceptEndDateTime
					, BigDecimal taxInvDocRefItemQuan
					, String invoicerId
					, String invoicerTaxRegId
					, String invoicerName
					, String invoicerSpecifiedPerson
					, String invoicerSpecifiedAddr
					, String invoicerTypeCode
					, String invoicerClassificationCode
					, String invoicerDefineDept
					, String invoicerDefinePerson
					, String invoicerDefineTel
					, String invoicerDefineUri
					, String invoiceeId
					, String invoiceeBusinessTypeCode
					, String invoiceeTaxRegId
					, String invoiceeName
					, String invoiceeSpecifiedPerson
					, String invoiceeSpecifiedAddr
					, String invoiceeTypeCode
					, String invoiceeClassificationCode
					, String invoiceePriDept
					, String invoiceePriPerson
					, String invoiceePriTel
					, String invoiceePriUri
					, String invoiceeSecondDept
					, String invoiceeSecondPerson
					, String invoiceeSecondTel
					, String invoiceeScondUri
					, String brokerId
					, String brokerTaxRegId
					, String brokerName
					, String brokerSpecifiedPerson
					, String brokerSpecifiedAddr
					, String brokerTypeCode
					, String brokerClassificationCode
					, String brokerDefineDept
					, String brokerDefinePerson
					, String brokerDefineTel
					, String brokerDefineUri
					, String payTypeCode
					, BigDecimal payAmt
					, BigDecimal chargeTotalAmt
					, BigDecimal taxTotalAmt
					, BigDecimal grandTotalAmt
					, String regId
					, LocalDateTime regDtm
					, String chgId
					, LocalDateTime chgDtm)
	{
		this.issueId                           = issueId;
		this.exchDocId                         = exchDocId;
		this.exchDocReferId                    = exchDocReferId;
		this.exchDocIssueDateTime              = exchDocIssueDateTime;
		this.taxInvDocIssueId                  = taxInvDocIssueId;
		this.taxInvDocIssueDateTime            = taxInvDocIssueDateTime;
		this.taxInvDocTypeCode                 = taxInvDocTypeCode;
		this.taxInvDocPurposeCode              = taxInvDocPurposeCode;
		this.taxInvDocAmendCode                = taxInvDocAmendCode;
		this.taxInvDocOriId                    = taxInvDocOriId;
		this.taxInvDocDesc                     = taxInvDocDesc;
		this.taxInvDocRefId                    = taxInvDocRefId;
		this.taxInvDocRefAcceptStDateTime      = taxInvDocRefAcceptStDateTime;
		this.taxInvDocRefAcceptEndDateTime     = taxInvDocRefAcceptEndDateTime;
		this.taxInvDocRefItemQuan              = taxInvDocRefItemQuan;
		this.invoicerId                        = invoicerId;
		this.invoicerTaxRegId                  = invoicerTaxRegId;
		this.invoicerName                      = invoicerName;
		this.invoicerSpecifiedPerson           = invoicerSpecifiedPerson;
		this.invoicerSpecifiedAddr             = invoicerSpecifiedAddr;
		this.invoicerTypeCode                  = invoicerTypeCode;
		this.invoicerClassificationCode        = invoicerClassificationCode;
		this.invoicerDefineDept                = invoicerDefineDept;
		this.invoicerDefinePerson              = invoicerDefinePerson;
		this.invoicerDefineTel                 = invoicerDefineTel;
		this.invoicerDefineUri                 = invoicerDefineUri;
		this.invoiceeId                        = invoiceeId;
		this.invoiceeBusinessTypeCode          = invoiceeBusinessTypeCode;
		this.invoiceeTaxRegId                  = invoiceeTaxRegId;
		this.invoiceeName                      = invoiceeName;
		this.invoiceeSpecifiedPerson           = invoiceeSpecifiedPerson;
		this.invoiceeSpecifiedAddr             = invoiceeSpecifiedAddr;
		this.invoiceeTypeCode                  = invoiceeTypeCode;
		this.invoiceeClassificationCode        = invoiceeClassificationCode;
		this.invoiceePriDept                   = invoiceePriDept;
		this.invoiceePriPerson                 = invoiceePriPerson;
		this.invoiceePriTel                    = invoiceePriTel;
		this.invoiceePriUri                    = invoiceePriUri;
		this.invoiceeSecondDept                = invoiceeSecondDept;
		this.invoiceeSecondPerson              = invoiceeSecondPerson;
		this.invoiceeSecondTel                 = invoiceeSecondTel;
		this.invoiceeScondUri                  = invoiceeScondUri;
		this.brokerId                          = brokerId;
		this.brokerTaxRegId                    = brokerTaxRegId;
		this.brokerName                        = brokerName;
		this.brokerSpecifiedPerson             = brokerSpecifiedPerson;
		this.brokerSpecifiedAddr               = brokerSpecifiedAddr;
		this.brokerTypeCode                    = brokerTypeCode;
		this.brokerClassificationCode          = brokerClassificationCode;
		this.brokerDefineDept                  = brokerDefineDept;
		this.brokerDefinePerson                = brokerDefinePerson;
		this.brokerDefineTel                   = brokerDefineTel;
		this.brokerDefineUri                   = brokerDefineUri;
		this.payTypeCode                       = payTypeCode;
		this.payAmt                            = payAmt;
		this.chargeTotalAmt                    = chargeTotalAmt;
		this.taxTotalAmt                       = taxTotalAmt;
		this.grandTotalAmt                     = grandTotalAmt;
		this.regId                             = regId;
		this.regDtm                            = regDtm;
		this.chgId                             = chgId;
		this.chgDtm                            = chgDtm;
	}
}
