package cn.itcast.dao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.itcast.domain.User;

public interface UserDao {

	@Insert("insert into user(id,username,password,phone,cellphone,address,email) "
			+ "values(#{id},#{username},#{password},#{phone},#{cellphone},#{address},#{email})")
	public void add(User user);

	@Select("select * from user where id=#{id}")
	public User findById(@Param("id")String id);

	@Select("select * from user where username=#{username} and password=#{password}")
	public User find(@Param("username")String username, @Param("password")String password);

}