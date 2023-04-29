package co.istad.Banking.api.accounttype;


import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.api.accounttype.web.AccountTypeDto;
import co.istad.Banking.api.accounttype.web.CreateAccountTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") //org.mapstruct
public interface AccountTypeMapStruct {

    List<AccountTypeDto> toDto (List<AccountType> model);


    AccountType createAccountTypeDtoToAccountType(CreateAccountTypeDto createAccountTypeDto);

    AccountTypeDto accountTypeToAccountTypeDto(AccountType accountType);

    AccountType accountTypeToAccountType(CreateAccountTypeDto createAccountTypeDto);
}
