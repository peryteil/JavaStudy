package repository;

import db.DbConnect;
import dto.ProductDto;
import dto.UserDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminRepository implements AdminRepositoryInterface{
    Scanner sc = new Scanner(System.in);
    Connection dbConn = DbConnect.getConnection();
    PreparedStatement psmt = null;
    String sql;
    int result = 0;

    public List<UserDto> getAllUserList() {
        List<UserDto> dtoList = new ArrayList<>();
        ResultSet rs = null;

        try {
            sql = "SELECT * FROM user ORDER BY uId DESC";
            psmt = dbConn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                UserDto userDto = new UserDto();
                userDto.setuId(rs.getInt("uId"));
                userDto.setU_userId(rs.getString("u_userId"));
                userDto.setU_password(rs.getString("u_password"));
                userDto.setU_name(rs.getString("u_name"));
                userDto.setU_phone(rs.getString("u_phone"));
                userDto.setU_money(rs.getInt("u_money"));
                userDto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                dtoList.add(userDto);
            }
            rs.close();
            psmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtoList;
    }
    public int addMenu(ProductDto productDto) {
        System.out.println("메뉴추가 레포");
        sql = "INSERT INTO product(p_name, p_price, p_stock, p_status) VALUES(?,?,?,?)";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, productDto.getProuductName());
            psmt.setInt(2, productDto.getPrice());
            psmt.setInt(3, productDto.getStock());
            psmt.setBoolean(4, productDto.getStatus());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public List<ProductDto> getAllMenu() {
        sql = "SELECT * FROM product";
        List<ProductDto> productDtoList = new ArrayList<>();

        try (PreparedStatement psmt = dbConn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                ProductDto productDto = new ProductDto();

                productDto.setpId(rs.getInt("p_id"));
                productDto.setProuductName(rs.getString("p_name"));
                productDto.setPrice(rs.getInt("p_price"));
                productDto.setStock(rs.getInt("p_stock"));
                productDto.setStatus(rs.getBoolean("p_status"));

                productDtoList.add(productDto);
            }
            productDtoList.stream().forEach(x -> System.out.println(x));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return productDtoList;
    }

    public int deleteMenu(String findName) {
        sql = "DELETE FROM product WHERE p_name = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, findName);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    public int updateMenu(String findName) {
        String changeName = "";
        int changePrice = 0;
        int changeStock = 0;

        System.out.println(findName + "의 이름을 수정하시겠습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println(findName + "의 이름을 무엇으로 수정하시겠습니까?");
            changeName = sc.next();
        }
        System.out.println("가격을 수정하시겠습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("가격을 얼마로 수정하시겠습니까?");
            changePrice = sc.nextInt();
        }
        System.out.println("재고를 수정하시겠2습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("재고를 얼마로 수정하시겠습니까?");
            changeStock = sc.nextInt();
        }


        System.out.println("판매중인 상품으로 등록하시겠습니까?");
        String y = sc.next();

        sql = "UPDATE product SET p_name = ?, p_price = ?, p_stock = ?, p_status = ? ";
        sql = sql + "WHERE p_name = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, changeName);
            psmt.setInt(2, changePrice);
            psmt.setInt(3, changeStock);
            if (y.equalsIgnoreCase("y")) {
                psmt.setBoolean(4, true);
            } else {
                psmt.setBoolean(4, false);
            }
            psmt.setString(5, findName);
            int result = psmt.executeUpdate();
            if (result > 0) {
                System.out.println("메뉴 수정 완료했습니다.");
            } else {
                System.out.println("메뉴 수정 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println(
                    e.getMessage()
            );
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<UserDto> getAllUser() {
        sql = "SELECT * FROM user";
        List<UserDto> userDtoList = new ArrayList<>();

        try (PreparedStatement psmt = dbConn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                UserDto userDto = new UserDto();

                userDto.setuId(rs.getInt("uId"));
                userDto.setU_userId(rs.getString("u_userId"));
                userDto.setU_password(rs.getString("u_password"));
                userDto.setU_name(rs.getString("u_name"));
                userDto.setU_phone(rs.getString("u_phone"));
                userDto.setU_money(rs.getInt("u_money"));
                userDto.setCreatedAt(rs.getTimestamp("createdAt")
                        .toLocalDateTime());
                if (rs.getTimestamp("updatedAt") != null) {
                    userDto.setUpdatedAt(rs.getTimestamp("updatedAt")
                            .toLocalDateTime());
                }

                userDtoList.add(userDto);
            }
            userDtoList.stream().forEach(x -> System.out.println(x));


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return userDtoList;
    }

    public int addUser(UserDto userDto) {
        try {
            sql = "INSERT INTO user(u_userId, u_password, u_name, u_phone, u_money," +"createdAt) ";
            sql = sql + "VALUES(?,?,?,?,?,?)";
            psmt = dbConn.prepareStatement(sql);
            //psmt에 값추가
            psmt.setString(1, userDto.getU_userId());
            psmt.setString(2, userDto.getU_password());
            psmt.setString(3, userDto.getU_name());
            psmt.setString(4, userDto.getU_phone());
            psmt.setInt(5, userDto.getU_money());
            psmt.setTimestamp(6, Timestamp.valueOf(userDto.getCreatedAt()));

            return psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int updateUser(String findId) {
        String changePwd = "";
        String changeName = "";
        String changeNum = "";

        System.out.println("비밀번호를 수정하시겠습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("수정할 비밀번호 입력");
            changePwd = sc.next();
        }
        System.out.println("이름을 수정하시겠습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("수정할 이름 입력");
            changeName = sc.next();
        }
        System.out.println("전화번호를 수정하시겠습니까?");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("수정 전화번호 입력");
            changeNum = sc.next();
        }

        sql = "UPDATE user SET u_password = ?, u_name = ?, u_phone = ?, updatedAt = ? WHERE u_userId = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, changePwd);
            psmt.setString(2, changeName);
            psmt.setString(3, changeNum);
            psmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            psmt.setString(5, findId);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("유저 정보 수정을 완료했습니다.");
            } else {
                System.out.println("유저 정보 수정 실패 했습니다.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteUser(String findId) {
        sql = "DELETE FROM user WHERE u_userId = ?";
        try {
            psmt = dbConn.prepareStatement(sql);
            psmt.setString(1, findId);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("유저 삭제를 완료했습니다.");
            } else {
                System.out.println("유저 삭제 실패 했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int productSales() {
        sql = "SELECT p.p_name AS 제품명, COUNT(p.p_id) AS `팔린 개수`, SUM(p.p_price) AS `총 금액` " +
                "FROM sales s " +
                "JOIN product p ON s.p_id = p.p_id " +
                "GROUP BY 제품명";
        try (PreparedStatement psmt = dbConn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                String pName = rs.getString("제품명");
                int count = rs.getInt("팔린 개수");
                int sum = rs.getInt("총 금액");
                System.out.println("제품명 : " + pName + ", 팔린 개수 : " + count + ", 총 금액 : " + sum);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    public int userSales() {
        sql = "SELECT u.u_userId AS 아이디, u.u_name AS 이름, SUM(p.p_price) AS 구매금액, u.u_money AS 충전잔액 " +
                "FROM sales s JOIN user u ON s.u_userid = u.uId " +
                "JOIN product p on p.p_id = s.p_id " +
                "GROUP BY u.u_userId, u.u_name, u.u_money";
        try (PreparedStatement psmt = dbConn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                String userId = rs.getString("아이디");
                String uName = rs.getString("이름");
                int sum = rs.getInt("구매금액");
                int userMoney = rs.getInt("충전잔액");

                System.out.println("아이디: " + userId + " 이름: " + uName + " 구매금액: " + sum + " 충전잔액: " + userMoney);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
