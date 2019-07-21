package cn.itcast.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.itcast.domain.Book;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItem;
import cn.itcast.domain.User;

public interface OrderDao {
	//填加order时，同时将orderItem进行填加
	@Insert("insert into orders(id,ordertime,price,state,user_id) "
			+ "values(#{id},#{ordertime},#{price},#{state},#{user.id})")
	public void addOrder(Order order);
	@Insert("insert into orderitem(id,quantity,price,order_id,book_id) "
			+ "values(#{id},#{quantity},#{price},#{order_id},#{book.id})")
	public void addOrderItem(OrderItem orderItem);
	//下面是查询，查询order，orderItem,book和user
	@Select("select * from orders where id=#{id}")
	public Order findOrder(@Param("id")String id);
	@Select("select * from orderitem where order_id=#{order_id}")
	public List<OrderItem> findOrderItem(@Param("order_id")String order_id);
	@Select("select book.* from orderitem,book where orderitem.id=#{orderitem_id} and orderitem.book_id=book.id")
	public Book findBook(@Param("orderitem_id")String orderitem_id);
	//找出当前订单属于哪个用户
	@Select("select user.* from orders,user where orders.id=#{order_id} and orders.user_id=user.id")
	public User findUserByOrder(@Param("order_id")String order_id);
	//后端所有订单
	@Select("select * from orders where state=#{state}")
	public List<Order> getAll(@Param("state")boolean state);
		
	//前端订单
	@Select("select * from orders where state=#{state} and orders.user_id=#{userid}")
	public List<Order> getAllById(@Param("state")boolean state,@Param("userid")String userid);
	
	@Select("select * from orders where user_id=#{userid}")
	public List<Order> getAllOrder(@Param("userid")String userid);
	
	
	@Update("update orders set state=#{state} where id=#{id}")
	public void update(Order order);

}