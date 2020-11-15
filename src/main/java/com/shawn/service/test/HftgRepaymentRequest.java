package com.shawn.service.test;

import lombok.Data;

import java.util.List;

@Data
public class HftgRepaymentRequest {
    private String cmdId;

    private String merCustId;

    private String proId;

    private String ordId;

    private String ordDate;

    private String subOrdId;

    private String subOrdDate;

    private String outAcctId;

    private String principalAmt;

    private String interestAmt;

    private String fee;

    private String inCustId;

    private List<HftgDivDetail> divDetails;

    private String bgRetUrl;
}
