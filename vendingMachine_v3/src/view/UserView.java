package view;

import dto.LoginDto;
import service.UserService;

import java.util.Scanner;

public class UserView implements UserViewInterface{

    UserService userService = new UserService();
    Scanner sc = new Scanner(System.in);



    public void register() {
        int result = userService.register();
        if (result > 0){
            System.out.println("회원가입 성공");
        }else System.out.println("회원가입 실패");
    }

    public LoginDto login() {
        System.out.println("로그인 페이지 입니다.\n" +
                "아이디를 입력해주세요.");
        String userId =sc.next() ;
        System.out.println("비밀번호를 입력해주세요.");
        String pwd = sc.next();
        LoginDto loginDto = new LoginDto(userId,pwd);

        int result = userService.loginService(loginDto);
        if(result > 0){
            return loginDto;
        }else{
            System.out.println("로그인 실패!");
            return null;
        }
    }

    public void userBuyView(LoginDto loginDto) {
        int menuNum = 0;
        while (menuNum > 0 || menuNum <= 4){
            System.out.println("1. 돈 충전 2. 잔돈반환 3. 메뉴선택 4. 종료=>");
            menuNum = sc.nextInt();
            switch (menuNum){
                case 1:// 돈충전 끝
                    userService.insertCoin(loginDto);
                    break;
                case 2://잔돈반환 끝
                    userService.returnMoney(loginDto);
                    break;
                case 3://메뉴선택 끝
                    userService.selectMenu(loginDto);
                    break;
                case 4://종료 끝
                    return;
                default:
                    System.out.println("다시 입력해주세요.");
                    break;
            }
        }
    }
}
