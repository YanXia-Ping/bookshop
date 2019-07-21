package cn.itcast.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.itcast.domain.Category;
@Repository
public interface CategoryDao {

	@Insert("insert into category(id,name,description) "
			+ "values(#{id},#{name},#{description})")
	public  void add(Category category);
	@Select("select * from category where id=#{id}")
	public  Category find(@Param("id")String id);
	@Select("select * from category")
	public  List<Category> getAll();

}