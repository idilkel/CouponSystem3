package com.jb.CouponSystem3.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {

    private int id;
    private ClientType type;
    private LocalDateTime time;
    private String email;
}

