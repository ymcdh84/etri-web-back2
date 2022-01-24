package com.iljin.apiServer.template.tax;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_tax_invoice_item")
@IdClass(TaxInvoiceItemKey.class)
public class TaxInvoiceItem {

	@Id
	@Column(name="issue_id", nullable=false)
	String issueId;

	@Id
	@Column(name = "seq")
	String seq;

	@Column(name = "item_purch_exp_date_time")
	String itemPurchExpDateTime;

	@Column(name = "item_name")
	String itemName;

	@Column(name = "item_inform")
	String itemInform;

	@Column(name = "item_desc")
	String itemDesc;

	@Column(name = "item_charge_unit_quan")
	BigDecimal itemChargeUnitQuan;

	@Column(name = "item_unit_amt")
	BigDecimal itemUnitAmt;

	@Column(name = "item_amt")
	BigDecimal itemAmt;

	@Column(name = "item_calc_amt")
	BigDecimal itemCalcAmt;

	@Column(name = "reg_id")
	String regId;

	@Column(name = "reg_dtm")
	LocalDateTime regDtm;

	@Column(name = "chg_id")
	String chgId;

	@Column(name = "chg_dtm")
	LocalDateTime chgDtm;

	@Builder
	public TaxInvoiceItem(String issueId
						, String seq
						, String itemPurchExpDateTime
						, String itemName
						, String itemInform
						, String itemDesc
						, BigDecimal itemChargeUnitQuan
						, BigDecimal itemUnitAmt
						, BigDecimal itemAmt
						, BigDecimal itemCalcAmt
						, String regId
						, LocalDateTime regDtm
						, String chgId
						, LocalDateTime chgDtm)
	{
		this.issueId               = issueId;
		this.seq                   = seq;
		this.itemPurchExpDateTime  = itemPurchExpDateTime;
		this.itemName              = itemName;
		this.itemInform            = itemInform;
		this.itemDesc              = itemDesc;
		this.itemChargeUnitQuan    = itemChargeUnitQuan;
		this.itemUnitAmt           = itemUnitAmt;
		this.itemAmt               = itemAmt;
		this.itemCalcAmt           = itemCalcAmt;
		this.regId                 = regId;
		this.regDtm                = regDtm;
		this.chgId                 = chgId;
		this.chgDtm                = chgDtm;
	}
}
