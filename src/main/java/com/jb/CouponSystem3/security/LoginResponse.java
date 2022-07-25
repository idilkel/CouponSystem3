package com.jb.CouponSystem3.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class LoginResponse {

    private UUID token;
    private String email;
    //    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private Timestamp loginTime = Timestamp.valueOf(LocalDateTime.now());
    private ClientType type;

    public LoginResponse(UUID token, String email, ClientType type) {
        this.token = token;
        this.email = email;
        this.type = type;
    }
}
