package repository;

import db.DbConnect;
import dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserRepository {
    Connection dbConn = DbConnect.getConnection();
    String sql;
    PreparedStatement psmt;

    public int register(UserDto dto) {
//        System.out.println("[PhoneBookRepository]-insertData");
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
}

