package repository;

import vendingMachineV3.db.DbConnect;
import vendingMachineV3.dto.LoginDto;
import vendingMachineV3.dto.ProductDto;
import vendingMachineV3.dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository  {
    Connection dbConn = DbConnect.getConnection();
    vendingMachineV3.repository.AdminRepository adminRepository;

    PreparedStatement psmt = null;
    String sql;
    int result = 0; //쿼리 실행 결과를 담을 변수(성공 : 양수, 실패 : 0)

    public int register(UserDto userDto) {
        System.out.println("회원가입 레포지토리");
        try {
            sql = "INSERT INTO userdto(userId, pwd, userName, telNum, userMoney, createdAt) ";
            sql = sql + "VALUES(?,?,?,?,?,?)";
            psmt = dbConn.prepareStatement(sql);
            //psmt에 값추가
            psmt.setString(1, userDto.getUserId());
            psmt.setString(2, userDto.getPwd());
            psmt.setString(3, userDto.getUserName());
            psmt.setString(4, userDto.getTelNum());
            psmt.setInt(5, userDto.getUserMoney());
            psmt.setTimestamp(6, Timestamp.valueOf(userDto.getCreatedAt()));

            int 결과 = psmt.executeUpdate();
            return 결과;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int login(LoginDto loginDto) {
        System.out.println("로그인 레포");

        String userId = loginDto.getUserId();
        String pwd = loginDto.getPwd();

        sql = "SELECT userId, pwd FROM userdto WHERE userId = ?";

        ResultSet rs = null;

        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                String rsPwd = rs.getString("pwd");
                if (!rsPwd.equals(pwd)) {
                    System.out.println("비밀번호 확인해주세요");
                    return 0;
                }
                System.out.println("로그인 성공");
                return 1;
            } else {
                System.out.println("아이디를 확인해주세요");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psmt != null) psmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        try {
//            psmt = dbConn.prepareStatement(sql);
//            rs= psmt.executeQuery();
//
//            if(!(rs.getString("userId").equals(userId))){
//                System.out.println("아이디를 확인하세요.");
//            } else if(!(rs.getString("pwd").equals(pwd))){
//                System.out.println("비밀번호를 확인하세요.");
//                return 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    public int insertCoin(LoginDto loginDto, int cMoney) {
        System.out.println("돈충전 레포");
        String loginId = loginDto.getUserId();


        sql = "UPDATE userdto SET userMoney = userMoney + ? WHERE userId = ?";


        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setInt(1, cMoney);
            psmt.setString(2, loginId);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("돈 충전 성공: ");
            } else {
                System.out.println("충전 실패: 해당 userId가 존재하지 않음.");
            }
            return rowsAffected;
        } catch (SQLException e) {
            // SQL 예외 처리
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // 실패 시 반환값
    }

    public int returnMoney(LoginDto loginDto) {
        String loginId = loginDto.getUserId();

        sql = "UPDATE userdto SET userMoney = 0 WHERE userId = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, loginId);
            int rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("잔돈 반환 성공 ");
            } else {
                System.out.println("반환 실패: 해당 userId가 존재하지 않음.");
            }
            return rowsAffected;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    public int selectMenu(LoginDto loginDto) {
        Scanner sc = new Scanner(System.in);
        System.out.println("메뉴 선택 레포");
        String loginId = loginDto.getUserId();
        List<ProductDto> productDtoList = new ArrayList<>();
        int uId = 0;

        String userSql = "SELECT uId FROM userdto WHERE userId = ?";
        try (PreparedStatement psmtt = dbConn.prepareStatement(userSql)) {
            psmtt.setString(1,loginId);
            ResultSet rs = psmtt.executeQuery();
            if(rs.next()){
                uId = rs.getInt("uId");
            }else {
                System.out.println("사용자가 없다");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String productSql = "SELECT pId, productName, price, stock, status FROM productdto";
        try (PreparedStatement psmt1 = dbConn.prepareStatement(productSql);
             ResultSet rs = psmt1.executeQuery()) {
            while (rs.next()) {
                ProductDto productDto = new ProductDto();
                productDto.setpId(rs.getInt("pId"));
                productDto.setProductName(rs.getString("productName"));
                productDto.setPrice(rs.getInt("price"));
                productDto.setStock(rs.getInt("stock"));
                productDto.setStatus(rs.getBoolean("status"));
                productDtoList.add(productDto);
            }
            productDtoList.forEach(x -> System.out.println(x));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("원하는 제품명을 입력하세요.");
        String item = sc.next();
        for (ProductDto productDto : productDtoList) {
            if (productDto.getProductName().equals(item)) {
                String sql2 = "UPDATE userdto SET userMoney = userMoney - ? WHERE userId = ?";
                try (PreparedStatement psmt2 = dbConn.prepareStatement(sql2)) {
                    psmt2.setInt(1, productDto.getPrice());
                    psmt2.setString(2, loginId);
                    int rowsAffected = psmt2.executeUpdate(); // 금액 차감 업데이트 실행

                    if (rowsAffected > 0) {
                        System.out.println("금액 차감이 성공적으로 완료되었습니다.");
                    } else {
                        System.out.println("사용자 정보가 없습니다.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 재고 차감
                String sql3 = "UPDATE productdto SET stock = stock - 1 WHERE productName = ?";
                try (PreparedStatement psmt3 = dbConn.prepareStatement(sql3)) {
                    psmt3.setString(1, productDto.getProductName());
                    int stockAffected = psmt3.executeUpdate(); // 재고 감소 업데이트 실행
                    if (stockAffected > 0) {
                        System.out.println("재고가 성공적으로 차감되었습니다.");
                    } else {
                        System.out.println("재고 차감에 실패했습니다.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String sql4 = "INSERT INTO sales(pId, uId, purchaseTime) VALUES(?,?,NOW())";
                try (PreparedStatement psmt4 = dbConn.prepareStatement(sql4)) {
                    psmt4.setInt(1,productDto.getpId());
                    psmt4.setInt(2,uId);
                    int result = psmt4.executeUpdate();

                    if (result > 0){
                        System.out.println("판매 기록이 저장 완료");
                    }else {
                        System.out.println("판매기록 저장 실패");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    public UserDto findById(int uId) {
//        System.out.println("[PhoneBookRepository]-findById");
        UserDto dto = new UserDto();
        ResultSet rs = null;

        sql = "SELECT * FROM userdto WHERE uId = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setLong(1, uId);
            rs = psmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    dto.setuId(rs.getInt("uId"));
                    dto.setUserId(rs.getString("userId"));
                    dto.setPwd(rs.getString("pwd"));
                    dto.setUserName(rs.getString("userName"));
                    dto.setTelNum(rs.getString("telNum"));
                    dto.setUserMoney(rs.getInt("userMoney"));
                    dto.setCreatedAt(rs.getTimestamp("createdAt")
                            .toLocalDateTime());
                    if (rs.getTimestamp("updateAt") != null) {
                        dto.setUpdateAt(rs.getTimestamp("updateAt")
                                .toLocalDateTime());
                    }
                }
            }
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
