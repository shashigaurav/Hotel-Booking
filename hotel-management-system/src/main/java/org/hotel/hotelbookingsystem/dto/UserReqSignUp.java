package org.hotel.hotelbookingsystem.dto;


import lombok.Data;


@Data
public class UserReqSignUp {
    private String fullName;
    private String email;
    private String password;
    private String phone;

}
