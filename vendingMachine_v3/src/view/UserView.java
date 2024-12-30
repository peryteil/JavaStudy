package view;

import service.UserService;

public class UserView implements UserViewInterface{
    UserService userService = new UserService();


    public int register() {
        return 0;
    }

    public void login() {
    }
}
