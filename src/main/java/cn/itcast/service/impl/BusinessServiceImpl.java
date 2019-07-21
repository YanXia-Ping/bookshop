package cn.itcast.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.CategoryDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.Book;
import cn.itcast.domain.Cart;
import cn.itcast.domain.CartItem;
import cn.itcast.domain.Category;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItem;
import cn.itcast.domain.Page;
import cn.itcast.domain.User;
import cn.itcast.service.BusinessService;
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private BookDao bookDao ;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderDao orderDao ;
	/* (non-Javadoc)
	 * @see service.impl.BusinessService#addCategory(domain.Category)
	 */
	public void addCategory(Category category){
		categoryDao.add(category);
	}
	
	/* (non-Javadoc)
	 * @see service.impl.BusinessService#findCategory(java.lang.String)
	 */
	public Category findCategory(String id){
		return categoryDao.find(id);
	}
	
	/* (non-Javadoc)
	 * @see service.impl.BusinessService#getAllCategory()
	 */
	public List<Category> getAllCategory(){
		return categoryDao.getAll();
	}
	
	//添加书
	public void addBook(Book book){
		bookDao.add(book);
	}
	
	//获得书
	public Book findBook(String id){
		return bookDao.find(id);
	}
	
	//获得分页数据
	public Page getBookPageData(String pagenum){
		int totalrecord = bookDao.getTotalRecord();
		Page page = null;
		if(pagenum == null){
			page = new Page(1,totalrecord);
		}else{
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getPageData(page.getStartindex(), page.getPagesize());
		page.setList(list);
		return page;
	}
	
	public Page getBookPageData(String pagenum, String category_id){
		int totalrecord = bookDao.getTotalRecordById(category_id);
		Page page = null;
		if(pagenum == null){
			page = new Page(1,totalrecord);
		}else{
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getPageDataById(page.getStartindex(), page.getPagesize(), category_id);
		page.setList(list);
		return page;
	}

	public void buyBook(Cart cart, Book book) {
		cart.add(book);
	}
	
	//注册用户
	public void registerUser(User user) {
		userDao.add(user);
	}
	
	public User findUser(String id){
		return userDao.findById(id);
	}

	public User userLogin(String username, String password){
		return userDao.find(username, password);
	}
	
	//生成订单
	public void createOrder(Cart cart, User user){
		if(cart == null){
			throw new RuntimeException("对不起，您还没有购买任何商品");
		}
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setOrdertime(new Date());
		order.setPrice(cart.getPrice());
		order.setState(false);
		order.setUser(user);
		for(Map.Entry<String, CartItem> me : cart.getMap().entrySet()){
			//得到一个购物项就生成一个订单项
			CartItem citem = me.getValue();
			OrderItem oitem = new OrderItem();
			oitem.setBook(citem.getBook());
			oitem.setPrice(citem.getPrice());
			oitem.setId(UUID.randomUUID().toString());
			oitem.setQuantity(citem.getQuantity());
			order.getOrderitems().add(oitem);
		}	
		orderDao.addOrder(order);
		Set<OrderItem> set = order.getOrderitems();
		for(OrderItem item : set){
			orderDao.addOrderItem(item);
		}
	}

	//后台获取所有订单信息
	public List<Order> listOrder(String state) {
		List<Order> list=orderDao.getAll(Boolean.parseBoolean(state));	
		for(Order order : list){				
			//找出当前订单属于哪个用户
			User user = orderDao.findUserByOrder(order.getId());
			order.setUser(user);
		} 
		return list;	
	}

	//列出订单明细
	public Order findOrder(String orderid) {		
		Order order=orderDao.findOrder(orderid);
		List<OrderItem> list =orderDao.findOrderItem(orderid);
		for(OrderItem item : list){
			Book book = orderDao.findBook(item.getId());
			item.setBook(book);
		}
		order.getOrderitems().addAll(list);
		User user = orderDao.findUserByOrder(order.getId());
		order.setUser(user);
		return order;
	}

	//把订单置为发货状态
	public void confirmOrder(String orderid) {
		Order order=orderDao.findOrder(orderid);
		List<OrderItem> list =orderDao.findOrderItem(orderid);
		for(OrderItem item : list){
			Book book = orderDao.findBook(item.getId());
			item.setBook(book);
		}
		order.getOrderitems().addAll(list);
		User user = orderDao.findUserByOrder(order.getId());
		order.setUser(user);
		order.setState(true);
		orderDao.update(order);
	}

	//获得某个用户的订单信息
	public List<Order> listOrder(String state, String userid) {
		List<Order> list=orderDao.getAllById(Boolean.parseBoolean(state), userid);
		for(Order order : list){
			User user = userDao.findById(userid);
			order.setUser(user);
		}
		return list;
	}

	//获取某个用户的订单信息
	public List<Order> clientListOrder(String userid) {	
		List<Order> list =orderDao.getAllOrder(userid);
		for(Order order : list){
			User user = userDao.findById(userid);
			order.setUser(user);
		}
		return list;
	}
}