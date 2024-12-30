import dto.LoginDto;
import view.AdminView;
import view.UserView;

import java.util.Scanner;

public class MachineMain {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            UserView userview = new UserView();
            AdminView adminView = new AdminView();
            boolean b = false;

            while (!b) {
                System.out.println(" 저희 자판기는 회원제로 운영되는 자판기 입니다.\n" +
                        "회원가입은 1번 \n" + "로그인은 2번을 눌러주세요.");
                switch (scanner.nextInt()) {
                    case 1:
                        userview.register();
                        break;
                    case 2:
                        LoginDto loginDto = userview.login();
                        if(loginDto.getUserId().equals("admin")){
                            adminView.test();
                            break;
                        }
                        if (loginDto == null) {
                            System.out.println("로그인이 필요합니다");
                        } else {
                           userview.userBuyView(loginDto);
                        }
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 1, 2, 3번 중 하나를 입력해주세요.");
                }
            }
        }
    }