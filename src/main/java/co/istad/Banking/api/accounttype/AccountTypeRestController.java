package co.istad.Banking.api.accounttype;

import co.istad.Banking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeRestController {

    private final  AccountTypeService accountTypeService;
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
}
