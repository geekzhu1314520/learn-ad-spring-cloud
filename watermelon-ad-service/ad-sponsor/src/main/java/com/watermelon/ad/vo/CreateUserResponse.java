package com.watermelon.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;

}
