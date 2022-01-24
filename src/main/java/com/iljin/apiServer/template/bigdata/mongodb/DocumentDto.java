package com.iljin.apiServer.template.bigdata.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DocumentDto {
	String key;
	String dataType;
	String value;

	public DocumentDto(String key
			, String dataType
			, String value)
	{
		this.key = key;
		this.dataType = dataType;
		this.value = value;
	}

}
