package view;

import service.UserService;
import vendingMachineV3.dto.LoginDto;
import vendingMachineV3.dto.UserDto;
//import vendingMachineV3.service.UserService;
import vendingMachineV3.view.UserViewInterface;


import java.time.LocalDateTime;
import java.util.Scanner;

public class UserView implements UserViewInterface {
    Scanner sc = new Scanner(System.in);
    UserService userService = new UserService();



    @Override
    public boolean registerView() {
        UserDto userDto = new UserDto();

        System.out.println("회원가입 페이지 입니다.\n" +
                "아이디를 입력해주세요.");
        userDto.setUserId(sc.next());
        System.out.println("비밀번호를 입력해주세요.");
        userDto.setPwd(sc.next());
        System.out.println("이름을 입력해주세요.");
        userDto.setUserName(sc.next());
        System.out.println("전화번호를 입력해주세요.");
        userDto.setTelNum(sc.next());
        userDto.setUserMoney(0);
        userDto.setCreatedAt(LocalDateTime.now());

        int 결과 = userService.registerService(userDto);
        if (결과 > 0) {
            System.out.println("회원가입을 축하합니다.");
            return true;
        } else {
            System.out.println("회원가입 실패");
            return false;
        }
    }
    String as;
    LoginDto loginDto;

    @Override
    public LoginDto loginView() {

        System.out.println("로그인 페이지 입니다.\n" +
                "아이디를 입력해주세요.");
        String userId = sc.next();
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

    @Override
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
