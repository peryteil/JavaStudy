package service;

import dto.ProductDto;
import dto.UserDto;
import repository.AdminRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    Scanner sc = new Scanner(System.in);
    AdminRepository adminRepository = new AdminRepository();


    public void addMenu() {
        ProductDto productDto = new ProductDto();

        System.out.println("자판기에 추가할 물건의 이름을 입력해주세요.");
        productDto.setProuductName(sc.next());
        System.out.println("가격을 입력해주세요.");
        productDto.setPrice(sc.nextInt());
        System.out.println("재고를 추가해주세요.");
        productDto.setStock(sc.nextInt());
        System.out.println("판매 가능한 상품으로 등록할까요?");

        if (sc.next().equalsIgnoreCase("Y")) {
            productDto.setStatus(true);
        } else {
            productDto.setStatus(false);
        }

        int result = adminRepository.addMenu(productDto);
    }

    public List<ProductDto> getAllMenu() {
        return adminRepository.getAllMenu();
    }

    public int deleteMenu() {
        System.out.println("삭제할 물건 이름을 입력하세요.");
        return adminRepository.deleteMenu(sc.next());
    }

    public int updateMenu() {
        System.out.println("수정할 물건 이름을 입력하세요.");
        return adminRepository.updateMenu(sc.next());
    }

    public int addUser() {
        UserDto userDto = new UserDto();
        System.out.println("회원 등록 메뉴입니다.\n" +
                "회원에 아이디를 입력해주세요.");
        userDto.setU_userId(sc.next());
        System.out.println("비밀번호를 입력해주세요.");
        userDto.setU_password(sc.next());
        System.out.println("이름을 입력해주세요.");
        userDto.setU_name(sc.next());
        System.out.println("전화번호를 입력해주세요.");
        userDto.setU_phone(sc.next());
        userDto.setU_money(0);
        userDto.setCreatedAt(LocalDateTime.now());

        if (adminRepository.addUser(userDto) > 0) {
            System.out.println("회원 등록을 완료했습니다.");
            return 1;
        } else {
            System.out.println("회원 등록을 실패했습니다.");
            return 0;
        }
    }

    public List<UserDto> getAllUser() {
        return adminRepository.getAllUser();
    }

    public int deleteUser() {
        System.out.println("삭제할 회원 아이디를 입력하세요.");
        return adminRepository.deleteUser(sc.next());
    }

    public int updateUser() {
        System.out.println("수정할 회원 아이디를 입력하세요.");
        return adminRepository.updateUser(sc.next());
    }

    public int productSales() {
        return adminRepository.productSales();
    }

    public int userSales() {
        return adminRepository.userSales();
    }
}
