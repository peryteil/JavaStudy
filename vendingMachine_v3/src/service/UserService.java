package service;

import dto.LoginDto;
import dto.UserDto;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Scanner;

public class UserService {
    UserRepository userRepository = new UserRepository();
    Scanner sc = new Scanner(System.in);


    public int register() {
        UserDto userDto = new UserDto();
        System.out.println("회원가입 페이지 입니다.");

        // 아이디 입력 받기
        System.out.println("아이디를 입력하세요: ");
        String u_userId = sc.next();
        System.out.println("비밀번호를 입력하세요: ");
        String u_password = sc.next();
        System.out.println("이름을 입력하세요: ");
        String u_name = sc.next();
        System.out.println("전화번호를 입력하세요 (예: 010-1234-5678): ");
        String u_phone = sc.next();
        if (!u_phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
            System.out.println("전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678");
            return 0;
        }
        //

        userDto.setU_userId(u_userId);
        userDto.setU_password(u_password);
        userDto.setU_name(u_name);
        userDto.setU_phone(u_phone);
        userDto.setU_money(0);
        userDto.setCreatedAt(LocalDateTime.now());

        int result = userRepository.register(userDto);

        if(result > 0){
            System.out.println("success");
            return 1;
        }else {
            System.out.println("Fail");
        }

        return 0;
    }

    public int loginService(LoginDto loginDto) {
        return userRepository.login(loginDto);
    }

    public int insertCoin(LoginDto loginDto) {
        System.out.println("돈을 넣어주세요.");
        return userRepository.insertCoin(loginDto,sc.nextInt());
    }

    public int returnMoney(LoginDto loginDto) {
        return userRepository.returnMoney(loginDto);
    }

    public int selectMenu(LoginDto loginDto) {
        return userRepository.selectMenu(loginDto);
    }
}
