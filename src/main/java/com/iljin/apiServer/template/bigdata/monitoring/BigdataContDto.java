package com.iljin.apiServer.template.bigdata.monitoring;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataContDto implements Serializable {

	Double seq;
	Double value;

	public BigdataContDto(Double seq
						, Double value)
	{
		this.seq = seq;
		this.value = value;
	}
}
