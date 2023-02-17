package com.sy.backEndApiAkilina.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String emailOrNumero;


    @NotBlank
    private String password;

    public String getEmailOrNumero() {
        return emailOrNumero;
    }

    public void setEmailOrNumero(String emailOrNumero) {
        this.emailOrNumero = emailOrNumero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
