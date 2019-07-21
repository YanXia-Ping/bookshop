package cn.itcast.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.itcast.domain.Book;

public interface BookDao {
	@Insert("insert into book(id,name,author,price,image,description,category_id) "
			+ "values(#{id},#{name},#{author},#{price},#{image},#{description},#{category_id})")
	public void add(Book book);
	@Select("select * from book where id=#{id}")
	public Book find(@Param("id")String id);
	@Select("select * from book limit #{startindex},#{pagesize}")
	public List<Book> getPageData(@Param("startindex")int startindex, @Param("pagesize")int pagesize);
	@Select("select count(*) from book")
	public int getTotalRecord();

	@Select("select * from book where category_id=#{category_id} limit #{startindex},#{pagesize}")
	public List<Book> getPageDataById(@Param("startindex")int startindex, @Param("pagesize")int pagesize,
			@Param("category_id")String category_id);
	@Select("select count(*) from book where category_id=#{category_id}")
	public int getTotalRecordById(@Param("category_id")String category_id);
}
