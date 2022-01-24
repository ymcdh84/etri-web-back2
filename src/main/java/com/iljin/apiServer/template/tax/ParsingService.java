package com.iljin.apiServer.template.tax;

import java.util.List;

public interface ParsingService {

    // 세금계산서 DB 저장
    public void saveTaxBillToDataBase(List<String> taxIssueIdList) throws Exception;
}
