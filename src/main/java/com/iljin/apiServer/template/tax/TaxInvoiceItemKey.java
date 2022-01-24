package com.iljin.apiServer.template.tax;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TaxInvoiceItemKey implements Serializable {
	String issueId;
	String seq;

	@Builder
	public TaxInvoiceItemKey(String issueId, String seq) {
		this.issueId = issueId;
		this.seq = seq;
	}

	/* Default Constructor */
	public TaxInvoiceItemKey() {}
}
