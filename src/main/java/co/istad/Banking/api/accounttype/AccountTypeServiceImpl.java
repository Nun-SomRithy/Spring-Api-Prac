package co.istad.Banking.api.accounttype;

import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.api.accounttype.web.AccountTypeDto;
import co.istad.Banking.api.accounttype.web.CreateAccountTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements  AccountTypeService{

    private final AccountTypeMapStruct accountTypeMapStruct;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes=accountTypeMapper.select();
        return accountTypeMapStruct.toDto(accountTypes);

//        List<AccountType> accountTypes=accountTypeMapper.select();
//        List<AccountTypeDto> accountTypeDtoList =accountTypes.stream()
//                .map(accountType -> new AccountTypeDto(accountType.getName())).toList();
//        return accountTypeDtoList;
    }

    @Override
    public AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto) {
        AccountType accountType=accountTypeMapStruct.createAccountTypeDtoToAccountType(createAccountTypeDto);
        accountTypeMapper.insert(accountType);
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }


}
