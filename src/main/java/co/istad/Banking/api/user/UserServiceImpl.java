package co.istad.Banking.api.user;

import co.istad.Banking.api.user.web.CreateUserDto;
import co.istad.Banking.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserMapStruct userMapStruct;

    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {
        User user = userMapStruct.createUserDtoToUser(createUserDto);
        userMapper.insert(user);
        log.info("User id =" + user.getId());
//        return userMapStruct.userToUserDto(user);
        return this.findUserById(user.getId());
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userMapper.selectById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with %d is not found", id)));
        return userMapStruct.userToUserDto(user);

    }
}