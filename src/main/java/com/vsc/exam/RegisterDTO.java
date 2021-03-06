package com.vsc.exam;

import javax.validation.constraints.NotBlank;

public class RegisterDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordRepeat;

    public RegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
