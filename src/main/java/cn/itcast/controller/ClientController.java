package cn.itcast.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.domain.Book;
import cn.itcast.domain.Cart;
import cn.itcast.domain.Category;
import cn.itcast.domain.Order;
import cn.itcast.domain.Page;
import cn.itcast.domain.User;
import cn.itcast.service.impl.BusinessServiceImpl;

//前端控制器
@Controller
@RequestMapping("/client")
public class ClientController {
	@Autowired
	BusinessServiceImpl service = new BusinessServiceImpl();
	//下面是功能
	@RequestMapping("/orderServlet")
	public void orderServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		try{
			User user = (User) request.getSession().getAttribute("user");
			if(user == null){
				request.setAttribute("message", "对不起，请先登录");
				request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
				return;
			}
			
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			service.createOrder(cart, user);
			request.setAttribute("message", "订单已生成");
			request.getSession().removeAttribute("cart");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "订单生成失败");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		}
	}
	@RequestMapping("/clientOrderDetailServlet")
	public void clientOrderDetailServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String orderid = request.getParameter("orderid");
		Order order = service.findOrder(orderid);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/WEB-INF/pages/client/clientorderdetail.jsp").forward(request, response);
	}
	@RequestMapping("/clientListOrderServlet")
	public void clientListOrderServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String userid = request.getParameter("userid");
		List<Order> orders = service.clientListOrder(userid);
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/WEB-INF/pages/client/clientlistorder.jsp").forward(request, response);
	}
	@RequestMapping("/buyServlet")
	public void buyServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String bookid = request.getParameter("bookid");
		Book book = service.findBook(bookid);
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		service.buyBook(cart, book);
		request.getRequestDispatcher("/WEB-INF/pages/client/listcart.jsp").forward(request, response);
	}
	@RequestMapping("/indexServlet")
	public void indexServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("getAll")){
			getAll(request, response);
		}else if(method.equalsIgnoreCase("listBookWithCategory")){
			listBookWithCategory(request, response);
		}
	}
	@RequestMapping("/getAll")
	public void getAll(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum = request.getParameter("pagenum");
		Page page = service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/client/body.jsp").forward(request, response);
	}
	@RequestMapping("/listBookWithCategory")
	public void listBookWithCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String category_id = request.getParameter("category_id");
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum = request.getParameter("pagenum");
		Page page = service.getBookPageData(pagenum, category_id);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/client/body.jsp").forward(request, response);
		return ;
	}
	@RequestMapping("/loginOutServlet")
	public void loginOutServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("/WEB-INF/pages/client/head.jsp").forward(request, response);
	}

	@RequestMapping("/login")
	public void login(User user,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		User u = service.userLogin(user.getUsername(), user.getPassword());
		if(u == null){
			request.setAttribute("message", "账号或密码错误");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
			return;
		}
		request.getSession().setAttribute("user", u);
		request.getRequestDispatcher("/WEB-INF/pages/client/head.jsp").forward(request, response);
	}
	@RequestMapping("/registerServlet")
	public void registerServlet(User user,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {	
		user.setId(UUID.randomUUID().toString());
		try {
			service.registerUser(user);
			request.setAttribute("message", "注册成功");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		}catch (DuplicateKeyException e) {
			// TODO: handle exception
			request.setAttribute("message", "注册失败，用户名已存在");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		}
	}
	//下面是页面跳转
	@RequestMapping("/head")
	public void head(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/client/head.jsp").forward(request, response);
	}
	@RequestMapping("/listcart")
	public void listcart(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/client/listcart.jsp").forward(request, response);
	}
	@RequestMapping("/register")
	public void register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/client/register.jsp").forward(request, response);
	}

}
