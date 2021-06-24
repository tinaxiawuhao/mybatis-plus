package com.example.datasources;

import com.alibaba.fastjson.JSONObject;
import com.cosmoplat.openapi.client.APIClientService;
import com.cosmoplat.openapi.client.callback.IResponseHandler;
import com.cosmoplat.openapi.client.dto.Request;
import com.cosmoplat.openapi.client.dto.RespErrMsg;
import com.cosmoplat.openapi.client.dto.Response;
import com.cosmoplat.openapi.client.http.AuthType;
import com.cosmoplat.openapi.client.http.HttpMethod;
import com.cosmoplat.openapi.client.http.Protocol;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



/**
 * @author wuhao
 * @desc ...
 * @date 2021-06-23 17:25:39
 */
@Slf4j
public class test {
    public static void main(String[] args) {
        APIClientService apiClientService = new APIClientService.Builder()
                .gatewayAddr("openapi-uat.cosmoplat.com").gatewayPort(80)
                .appID("0ad17fcd-788e-4acf-ad17-1162ce7e20b7").appAuthKey("3f57dd018079a2d23d20e1d7c06730b2").requestExpireTime(120000)
                .build();
        Request req = new Request();
        req.setPath("/OZLORH5D/portal/notice/v1/sendSelfBuildNotice");
        req.setAuthType(AuthType.token);
        req.setMethod(HttpMethod.POST);
        req.setProtocol(Protocol.https);
        req.setRequestId(UUID.randomUUID().toString());
        req.setContentType(ContentType.APPLICATION_JSON);
        Map<String, String> headerParams = new HashMap<>();
//        headerParams.put("api_gateway_api-version", "1.0");
//        headerParams.put("Content-Type", "application/json");
        req.setHeaders(headerParams);
        req.setStringBody("{\"isDelay\":false,\"messageAppId\":\"1390127014416302082\",\"messageContent\":\"<p>研发技术平台全员：请自我总结Q1并计划Q2工作，要求如下：</p><p>一、汇报内容包括以下四点</p><p>1、Q1工作总结，重点分析3月实际达成情况；</p><p>2、Q1实际达成与目标的差及关差路径；</p><p>3、Q2工作计划，重点细化4月工作目标；</p><p>4、其他需要<a href=\\\"https://\\\" target=\\\"_blank\\\" style=\\\"background-color: rgb(255, 255, 255);\\\">汇报内容</a></p>\",\"noticeTitle\":\"【团队】 工作汇报通知\",\"originAppId\":\"tian0000000000000001\",\"receiveType\":\"inner\",\"senderName\":\"天云通知\",\"senderUucId\":\"24970\",\"tenantId\":\"368943\",\"uucIds\":[\"28354\"]}");
        Map returnStatusMap = new HashMap(8);
        returnStatusMap.put("status", false);
        returnStatusMap.put("message", "");
        apiClientService.sendRequest(req, new IResponseHandler() {
            @Override
            public void onFailure(Request request, RespErrMsg respErrMsg) {
                log.error("请求异常：{}->{}", respErrMsg.getErrCode(), respErrMsg.getErrMsg());
                throw new RuntimeException(respErrMsg.getErrCode() + "-异常-" + respErrMsg.getErrMsg());
            }

            @Override
            public void onResponse(Request request, Response response) {
                String resBody = response.getBody();
                log.info("-----------返回参数----------{}", resBody);
                JSONObject jsonObject = (JSONObject) JSONObject.parse(resBody);
                if (jsonObject.get("isSuccess") != null && (Boolean) jsonObject.get("isSuccess") == false) {
                    returnStatusMap.put("status", false);
                    returnStatusMap.put("message", jsonObject.get("msg"));
                } else {
                    returnStatusMap.put("status", true);
                    returnStatusMap.put("message", jsonObject.get("msg"));
                }
            }
        });
    }
}
