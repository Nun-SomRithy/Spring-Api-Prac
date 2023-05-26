package co.istad.Banking.api.user;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {

    @InsertProvider(type = UserProvider.class,method ="buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("u") User user);

    @SelectProvider(type = UserProvider.class ,method = "buildSelectByIdSql")
    @Results(id = "userResultMap",value = {
            @Result(column ="student_card_id",property="studentCardId"),
            @Result(column ="is_student",property="isStudent"),
            @Result(column = "id",property = "roles",many = @Many(select = "selectUserRole"))
    })
    Optional<User> selectById(@Param("id") Integer id);


    //Page
    @SelectProvider(type = UserProvider.class,method = "buildSelectSql")
    @ResultMap("userResultMap")
    List<User> select(String name);

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

    @Select("SELECT EXISTS(SELECT * FROM users WHERE email= #{email})") //maybe error
    boolean existByEmail(String email);


    @Select("SELECT EXISTS(SELECT * FROM roles WHERE id= #{roleId})")
    boolean checkRoleId(Integer roleId);



    @SelectProvider(type = UserProvider.class,method = "buildSelectUserRoleId")
    List<Role> selectUserRole(Integer id);




}
