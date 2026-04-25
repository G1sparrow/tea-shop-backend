package com.teashop.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}