package com.ispan.recordshop.cochordnuts.util;

import cn.hutool.json.JSONUtil;
import com.ispan.recordshop.cochordnuts.dto.PayLoadDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;
import java.util.Date;

public class JWTUtil {
    //預設金鑰
    public static final String DEFAULT_SECRET = "ABCDEFGHJKLM23456789npqrstuvwxyz";

    /**
     * 使用 HMAC SHA-256
     * @param payloadStr: 有效酬載
     * @param secret: 金鑰
     * @return JWS 字串
     * @throws JOSEException
     */
    public static String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException {
        //建立 JWS 標頭，設定簽名演算法及類型
        JWSHeader jwsHeader = new JWSHeader
                .Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        //將酬載資訊封裝到 Payload 中
        Payload payload = new Payload(payloadStr);

        //建立 JWS 物件
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //建立 HMAC 簽名器
        JWSSigner jwsSigner = new MACSigner(secret);

        //簽名
        jwsObject.sign(jwsSigner);

        return jwsObject.serialize();
    }

    /**
     * 驗證簽名，提取有效酬載，以 PayloadDto 物件形式傳回
     * @param token: JWS 字串
     * @param secret: 金鑰
     * @return PayloadDto 物件
     * @throws ParseException
     * @throws JOSEException
     */
    public static PayLoadDto verifyTokenByHMAC(String token, String secret) throws ParseException, JOSEException {
        //從 token 中解析 JWS 物件
        JWSObject jwsObject = JWSObject.parse(token);

        //建立 HMAC 驗證器
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        if(!jwsObject.verify(jwsVerifier)){
            throw new JOSEException("token 簽名不合法！");
        }

        String payload = jwsObject.getPayload().toString();
        PayLoadDto payLoadDto = JSONUtil.toBean(payload, PayLoadDto.class);
        if(payLoadDto.getExp() < new Date().getTime()){
            throw new JOSEException("token 已過期！");
        }

        return payLoadDto;
    }
}
