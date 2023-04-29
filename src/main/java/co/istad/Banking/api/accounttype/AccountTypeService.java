package co.istad.Banking.api.accounttype;

import co.istad.Banking.api.accounttype.web.AccountTypeDto;
import co.istad.Banking.api.accounttype.web.CreateAccountTypeDto;

import java.util.List;

public interface AccountTypeService {


    List<AccountTypeDto> findAll();


    AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto);

    AccountTypeDto findAccountTypeById(Integer id);







}
