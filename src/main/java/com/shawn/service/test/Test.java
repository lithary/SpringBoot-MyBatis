package com.shawn.service.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class Test {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"bgRetUrl\": \"http://test1234.tunnel.qydev.com/hftgsp/notify.sp\",\n" +
                "    \"principalAmt\": \"399.30\",\n" +
                "    \"fee\": \"199.40\",\n" +
                "    \"proId\": \"XM17010109AWQ\",\n" +
                "    \"subOrdId\": \"950517010113253264028162508\",\n" +
                "    \"inCustId\": \"6000060006112192\",\n" +
                "    \"ordDate\": \"20170220\",\n" +
                "    \"divDetails\": [{\"divCustId\":\"6000060006014887\",\"divAcctId\":\"MDT000002\",\"divAmt\":\"186.35\"},{\"divCustId\":\"6000060006014887\",\"divAcctId\":\"MDT000004\",\"divAmt\":\"13.05\"}],\n" +
                "    \"merCustId\": \"6000060006014887\",\n" +
                "    \"ordId\": \"9510002201504707886\",\n" +
                "    \"subOrdDate\": \"20170101\",\n" +
                "    \"interestAmt\": \"23.95\",\n" +
                "    \"cmdId\": \"Repayment\",\n" +
                "}";

        System.out.println(json);
        JSONObject jsonObject = new JSONObject(json);
        System.out.println(jsonObject.getJSONArray("divDetails").size());

        HftgRepaymentRequest repaymentRequest = JSONUtil.toBean(jsonObject, HftgRepaymentRequest.class);
        System.out.println(repaymentRequest.getDivDetails().get(0).getDivAcctId());
    }
}
