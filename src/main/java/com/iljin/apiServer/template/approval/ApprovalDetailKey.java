package com.iljin.apiServer.template.approval;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApprovalDetailKey implements Serializable {

    String apprNo;
    Short apprSeq;
}
