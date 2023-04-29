package co.istad.Banking.api.accounttype;


import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring") //org.mapstruct
public interface AccountTypeMapStruct {

    List<AccountTypeDto> toDto (List<AccountType> model);

    AccountTypeDto toDto(AccountType model);


}
