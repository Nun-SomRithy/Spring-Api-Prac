package co.istad.Banking.api.user;


import org.apache.ibatis.annotations.*;
import org.hibernate.validator.internal.util.classhierarchy.Filters;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

@Repository
@Mapper
public interface UserMapper {

    @InsertProvider(type = UserProvider.class,method ="buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("u") User user);

    @SelectProvider(type = UserProvider.class ,method = "buildSelectByIdSql")
    @Results(id = "userResultMap",value = {
            @Result(column ="student_card_id",property="studentCardId"),
            @Result(column ="is_student",property="isStudent")
    })
    Optional<User> selectById(@Param("id") Integer id);


//Page
    @SelectProvider(type = UserProvider.class,method = "buildSelectSql")
    @ResultMap("userResultMap")
    List<User> select();

    @Select("SELECT EXISTS(SELECT * FROM  users WHERE id=#{id})")
    boolean existById(@Param("id") Integer id);


    @SelectProvider(type = UserProvider.class,method = "buildSelectByStudentId")
    Optional<User> selectUserByStudentId(String studentId);


    @DeleteProvider(type = UserProvider.class,method ="buildDeleteByIdSql" )
    void deleteById(@Param("id") Integer id);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateIsDeleteByIdSql")
    void updateIsDeletedById(@Param("id") Integer id,@Param("status") boolean isStatus);


    @UpdateProvider(type = UserProvider.class,method = "buildUpdateByIdSql")
    void updateById(@Param("u") User user);


    @SelectProvider(type = UserProvider.class, method = "buildSelectByNameSql")
    List<User> selectByName(@Param("name") String name);


}
