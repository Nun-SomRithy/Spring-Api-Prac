package co.istad.Banking.api.accounttype;


import co.istad.Banking.api.accounttype.web.AccountType;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository //REPO=DAO=MAPPER
public interface AccountTypeMapper {


    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelect") //the one who handle
    List<AccountType> select();

    @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertSql")
    void insert (@Param("a") AccountType accountType);

}
