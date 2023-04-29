package co.istad.Banking.api.accounttype.web;

import co.istad.Banking.api.accounttype.AccountTypeService;
import co.istad.Banking.api.user.web.CreateUserDto;
import co.istad.Banking.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
@Slf4j
public class AccountTypeRestController {

    private final AccountTypeService accountTypeService;
    @GetMapping
//  List<AccountTypeDto> //all work from service
    public BaseRest<?> findAll(){
        var accountTypeDtoList=accountTypeService.findAll();
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("Account type have been found")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoList)
                .build();
    }

    @PostMapping
    public  BaseRest<?> createNewAccountType(@RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
        AccountTypeDto accountTypeDto= accountTypeService.createNewAccountType(createAccountTypeDto);
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("Account type have been Created Successfully")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }

    @GetMapping("/{id}")
    public BaseRest<?> findAccountTypeById(@PathVariable Integer id){
        AccountTypeDto accountTypeDto=accountTypeService.findAccountTypeById(id);return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User Has Been found SuccessFully")
                .data(accountTypeDto)
                .build();

    }




}
