package dto;

import java.time.LocalDateTime;

public class UserDto {
    int uId;
    String u_userId;
    String u_password;
    String u_name;
    String u_phone;
    int u_money;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public String toString() {
        return "UserDto{" +
                "uId=" + uId +
                ", u_userId='" + u_userId + '\'' +
                ", u_name=" + u_name +
                ", u_phone=" + u_phone +
                ", u_money=" + u_money +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public UserDto() {
    }

    public UserDto(String u_userId, String u_password, String u_name, String u_phone, int u_money, LocalDateTime createdAt) {
        this.u_userId = u_userId;
        this.u_password = u_password;
        this.u_name = u_name;
        this.u_phone = u_phone;
        this.u_money = u_money;
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getU_userId() {
        return u_userId;
    }

    public void setU_userId(String u_userId) {
        this.u_userId = u_userId;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public int getU_money() {
        return u_money;
    }

    public void setU_money(int u_money) {
        this.u_money = u_money;
    }
}
