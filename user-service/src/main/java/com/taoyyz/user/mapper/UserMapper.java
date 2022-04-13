package com.taoyyz.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyyz.user.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set active_status = 'Y' where active_code = #{code}")
    void updateByActiveCode(String code);

    @Select("select * from user where (username = #{username} " +
            "or email = #{email}) and password = #{password}")
    User getUserByUsernameOrEmail(User user);

    @Update("UPDATE `user` SET points = points + #{point} WHERE id = #{uid}")
    void addPoint(Long uid, BigDecimal point);
}
