package com.iljin.apiServer.template.approval.dlgt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalDelegateKey implements Serializable {
    String adlgId;
    String actId;
    Short adlgSeq;
}
