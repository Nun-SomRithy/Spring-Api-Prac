package co.istad.Banking.api.user.web;


import co.istad.Banking.api.user.User;
import co.istad.Banking.api.user.UserService;
import co.istad.Banking.base.BaseError;
import co.istad.Banking.base.BaseRest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserResController {

    private final UserService userService;

    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable Integer id){
        UserDto userDto =userService.findUserById(id)   ;
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User Has Been Created SuccessFully")
                .data(userDto)
                .build();
    }

    @PostMapping
    public  BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto) {
        UserDto userDto= userService.createNewUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User Has Been Created SuccessFully")
                .data(userDto)
                .build();
    }


}
