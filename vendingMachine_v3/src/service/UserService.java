package service;

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

        String u_userId = sc.next();
        String u_password = sc.next();
        String u_name = sc.next();
        String u_phone = sc.next();

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
}
