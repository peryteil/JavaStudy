package view;

import vendingMachineV3.dto.LoginDto;
import vendingMachineV3.view.AdminView;

import java.util.Scanner;

public class VMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserView userView = new UserView();
        AdminView adminView = new AdminView();
        boolean b = false;

        while (!b){
            System.out.println("저희 자판기는 회원제로 운영되는 자판기 입니다.\n" +
                    "회원가입은 1번 \n" +
                    "로그인은 2번을 눌러주세요.");
            switch(sc.nextInt()){
                case 1:
                    if (userView.registerView()==true){
                        break;
                    }else return;
                case 2:
                    LoginDto loginDto = userView.loginView();
                    if(loginDto == null){
                        System.out.println("로그인이 필요합니다.");
                    }else {
                        userView.userBuyView(loginDto);
                    }break;
                case 1004:
                    adminView.mainAdminView();
                    break;
            }
        }

    }
}
