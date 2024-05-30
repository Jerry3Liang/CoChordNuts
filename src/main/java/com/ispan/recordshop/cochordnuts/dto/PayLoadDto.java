package com.ispan.recordshop.cochordnuts.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayLoadDto {
    //主題
    private String sub;

    //簽發時間
    private Long iat;

    //過期時間
    private Long exp;

    //JWT 的 ID
    private String jti;

    //使用者名稱
    private String username;

    //使用者擁有的許可權
    private List<String> authorities;
}
