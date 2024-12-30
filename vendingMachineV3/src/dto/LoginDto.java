package vendingMachineV3.dto;

public class LoginDto {
    String userId;
    String pwd;
    String userMoney;

    public LoginDto(String userId, String pwd) {
        this.userId = userId;
        this.pwd = pwd;
    }

    public LoginDto(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
