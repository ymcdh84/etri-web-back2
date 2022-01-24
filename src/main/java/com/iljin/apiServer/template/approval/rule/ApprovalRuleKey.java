package com.iljin.apiServer.template.approval.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRuleKey implements Serializable {

    String compCd;
    String docTypeCd;
    String dtlTypeCd;
    String curCd;
    Short ruleSeq;

}
