package co.istad.Banking.api.account.web;

import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.api.accounttype.web.AccountTypeDto;
import lombok.Builder;

@Builder
public record AccountDto(
        String accountNo,
        String accountName,
        String profile,
        String phoneNumber,
        Integer transferLimit,
        AccountTypeDto accountType

) {


}
