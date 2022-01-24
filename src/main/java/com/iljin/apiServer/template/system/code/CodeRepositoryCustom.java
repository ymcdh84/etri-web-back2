package com.iljin.apiServer.template.system.code;

import com.iljin.apiServer.core.util.Pair;

import java.util.List;

public interface CodeRepositoryCustom {

    List<CodeDto> getGroupCodeDetailList(CodeDto codeDto);
}
