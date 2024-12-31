package repository;

import db.DbConnect;
import dto.LoginDto;
import dto.ProductDto;
import dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {
    Connection dbConn = DbConnect.getConnection();
    String sql;
    PreparedStatement psmt;

    public int register(UserDto dto) {

        // DB에 저장하기
        try {
            sql = "INSERT INTO user (u_userId, u_password, u_name, u_phone, u_money," +
                    "createdAt) ";
            sql = sql + "VALUES (?, ?, ?,?,?,?)";
            psmt = dbConn.prepareStatement(sql);
            // 전달할 인자값을 psmt 에 추가
            psmt.setString(1, dto.getU_userId());
            psmt.setString(2, dto.getU_password());
            psmt.setString(3, dto.getU_name());
            psmt.setString(4, dto.getU_phone());
            psmt.setInt(5,dto.getU_money());
            psmt.setTimestamp(6, Timestamp.valueOf(dto.getCreatedAt()));

            // DB 에 저장 요청
            int result = psmt.executeUpdate();
            if (result > 0) {
                System.out.println("회원가입 완료");
                return result;
            }else {
                System.out.println("회원 가입 실패");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int login(LoginDto loginDto) {
        System.out.println("로그인 레포");

        String userId = loginDto.getUserId();
        String pwd = loginDto.getPwd();

        sql = "SELECT u_userId, u_password FROM user WHERE u_userId = ?";

        ResultSet rs = null;

        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                String rsPwd = rs.getString("u_password");
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
    }

    public int insertCoin(LoginDto loginDto, int i) {
        System.out.println("돈충전 레포");
        String loginId = loginDto.getUserId();


        sql = "UPDATE user SET u_money = u_money + ? WHERE u_userId = ?";


        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setInt(1, i);
            psmt.setString(2, loginId);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("돈 충전 성공: ");
            } else {
                System.out.println("충전 실패: 해당 userId가 존재하지 않음.");
            }
            return rowsAffected;
        }  catch (Exception e) {
            // 기타 예외 처리
            System.out.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // 실패 시 반환값
    }

    public int returnMoney(LoginDto loginDto) {
        String loginId = loginDto.getUserId();

        sql = "UPDATE user SET u_money = 0 WHERE u_userId = ?";
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

        String userSql = "SELECT uId FROM user WHERE u_userId = ?";
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

        String productSql = "SELECT p_id, p_name, p_price, p_stock, p_status FROM product";
        try (PreparedStatement psmt1 = dbConn.prepareStatement(productSql);
             ResultSet rs = psmt1.executeQuery()) {
            while (rs.next()) {
                ProductDto productDto = new ProductDto();
                productDto.setpId(rs.getInt("p_id"));
                productDto.setProuductName(rs.getString("p_name"));
                productDto.setPrice(rs.getInt("p_price"));
                productDto.setStock(rs.getInt("p_stock"));
                productDto.setStatus(rs.getBoolean("p_status"));
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
            if (productDto.getProuductName().equals(item)) {
                String sql2 = "UPDATE user SET u_money = u_money - ? WHERE u_userId = ?";
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
                String sql3 = "UPDATE product SET p_stock = p_stock - 1 WHERE p_name = ?";
                try (PreparedStatement psmt3 = dbConn.prepareStatement(sql3)) {
                    psmt3.setString(1, productDto.getProuductName());
                    int stockAffected = psmt3.executeUpdate(); // 재고 감소 업데이트 실행
                    if (stockAffected > 0) {
                        System.out.println("재고가 성공적으로 차감되었습니다.");
                    } else {
                        System.out.println("재고 차감에 실패했습니다.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String sql4 = "INSERT INTO sales(p_id, u_userId, s_date) VALUES(?,?,NOW())";
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
}

