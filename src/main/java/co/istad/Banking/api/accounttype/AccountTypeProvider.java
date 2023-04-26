package co.istad.Banking.api.accounttype;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public class AccountTypeProvider {

    public String buildSelect(){
        return new SQL() {{
            //ToDO
            SELECT("*");
            FROM("account_types");
        }}.toString();
    }










}
