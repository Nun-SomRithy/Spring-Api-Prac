package co.istad.Banking.api.accounttype;


import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository //REPO=DAO=MAPPER
public interface AccountTypeMapper {


    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelect") //the one who handle
    List<AccountType> select();

    @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert (@Param("a") AccountType accountType);


    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectByIdSql")
    Optional<AccountType> selectById(@Param("id") Integer id);

}
