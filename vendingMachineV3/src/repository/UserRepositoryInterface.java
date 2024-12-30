package repository;


import vendingMachineV3.dto.LoginDto;
import vendingMachineV3.dto.UserDto;

public interface UserRepositoryInterface {
    int register(UserDto userDto);
    int login(LoginDto loginDto);
    int insertCoin(LoginDto loginDto, int cMoney);
    UserDto findById(int id);
}
