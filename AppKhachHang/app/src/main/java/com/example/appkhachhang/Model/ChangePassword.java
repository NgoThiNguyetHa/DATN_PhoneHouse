package com.example.appkhachhang.Model;

public class ChangePassword {
   private String email;
   private String oldPassword;
   private String newPassword;

    public ChangePassword(String email, String oldPassword, String newPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangePassword() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
