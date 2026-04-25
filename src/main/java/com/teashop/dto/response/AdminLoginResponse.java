package com.teashop.dto.response;

import com.teashop.common.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponse {
    private Admin admin;
    private String token;
}
