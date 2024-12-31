package repository;

import dto.UserDto;

import java.util.List;

public interface AdminRepositoryInterface {
    List<UserDto> getAllUserList();
//    int addMenu(ProductDto productDto);
    int deleteMenu(String findName);
    int updateMenu(String findName);
    List<UserDto> getAllUser();
    int addUser(UserDto userDto);
    int updateUser(String findId);
    int deleteUser(String findId);
}
