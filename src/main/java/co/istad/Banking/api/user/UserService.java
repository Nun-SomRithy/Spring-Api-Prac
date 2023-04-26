package co.istad.Banking.api.user;

import co.istad.Banking.api.user.web.CreateUserDto;
import co.istad.Banking.api.user.web.UserDto;

public interface UserService {

    UserDto createNewUser(CreateUserDto createUserDto);

}
