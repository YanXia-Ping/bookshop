package cn.itcast.test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.CategoryDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.Book;
import cn.itcast.domain.Category;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItem;
import cn.itcast.domain.User;

public class TestMybatis {
	@Test
	public void addBook() throws Exception {
		Book book =new Book();
		book.setId("1");
		book.setName("格林童话");
		book.setAuthor("安徒生");
		book.setPrice(30.1);
		book.setDescription("童话书");
		book.setCategory_id("1");
		book.setImage(null);
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		BookDao bookDao=session.getMapper(BookDao.class);
		bookDao.add(book);;
		session.commit();
		session.close();
		in.close();
		}
	@Test
	public void getPageDataById() throws Exception {
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		BookDao bookDao=session.getMapper(BookDao.class);
		List<Book> list=bookDao.getPageDataById(0,1,"1");
		for(Book book:list) {
			System.out.println(book);
		}
		session.close();
		in.close();
		}
	@Test
	public void addCategory() throws Exception {
		Category category=new Category();
		category.setId("2");
		category.setName("教科书");
		category.setDescription("教学用书");
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		CategoryDao categoryDao=session.getMapper(CategoryDao.class);
		categoryDao.add(category);
		session.commit();
		session.close();
		in.close();
		}
	@Test
	public void find() throws Exception {
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		CategoryDao categoryDao=session.getMapper(CategoryDao.class);
		Category list=categoryDao.find("2");
		System.out.println(list);
		session.close();
		in.close();
		}
	@Test
	public void addUser() throws Exception {
		User user=new User();
		user.setAddress("广州");
		user.setCellphone("15507489999");
		user.setEmail("15507489999@163.com");
		user.setId(UUID.randomUUID().toString());
		user.setPassword("111111");
		user.setPhone("15507489999");
		user.setUsername("123123");
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		UserDao userDao=session.getMapper(UserDao.class);
		userDao.add(user);;
		session.commit();
		session.close();
		in.close();
		}
	@Test
	public void findUser() throws Exception {
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		UserDao userDao=session.getMapper(UserDao.class);
		User list =userDao.find("111111", "111111");
		System.out.println(list);
		session.close();
		in.close();
		}
	@Test
	public void addOrder() throws Exception {
		Order order=new Order();
		order.setId("1");
		order.setPrice(33.1);
		order.setState(true);
		order.setOrdertime(new Date());
		User user=new User();
		user.setId("1");
		order.setUser(user);
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		OrderDao orderDao=session.getMapper(OrderDao.class);
		orderDao.addOrder(order);
		session.commit();
		session.close();
		in.close();
		}
	@Test
	public void addOrderItem() throws Exception {
		OrderItem orderItem=new OrderItem();
		Book book =new Book();
		book.setId("1");
		orderItem.setId("1");
		orderItem.setBook(book);
		orderItem.setPrice(33.3);
		orderItem.setQuantity(1);
		orderItem.setOrder_id("1");
		InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
		//创建SqlSessionFactory对象
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession对象
		SqlSession session=factory.openSession();
		OrderDao orderDao=session.getMapper(OrderDao.class);
		orderDao.addOrderItem(orderItem);
		session.commit();
		session.close();
		in.close();
		}
	@	Test
	public void randomTest() {
		System.out.println(UUID.randomUUID().toString());
	}
	}
