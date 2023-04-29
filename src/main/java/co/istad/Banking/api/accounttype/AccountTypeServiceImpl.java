package co.istad.Banking.api.accounttype;

import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.api.accounttype.web.AccountTypeDto;
import co.istad.Banking.api.accounttype.web.CreateAccountTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
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
    public AccountTypeDto createNewAccountType(CreateAccountTypeDto accountTypeDto) {
        AccountType accountType=accountTypeMapStruct.createAccountTypeDtoToAccountType(accountTypeDto);
        accountTypeMapper.insert(accountType);
//        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
        return this.findAccountTypeById(accountType.getId());

    }


    @Override
    public AccountTypeDto findAccountTypeById(Integer id) {
        AccountType accountType=accountTypeMapper.selectById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Not %d found",id)));
            return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }









}
