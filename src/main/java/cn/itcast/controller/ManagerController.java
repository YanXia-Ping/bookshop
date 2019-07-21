package cn.itcast.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.domain.Book;
import cn.itcast.domain.Category;
import cn.itcast.domain.Order;
import cn.itcast.domain.Page;
import cn.itcast.service.impl.BusinessServiceImpl;

//后端控制器
@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	BusinessServiceImpl service = new BusinessServiceImpl();
	
	@RequestMapping("/confirmOrderServlet")
	public void confirmOrderServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try{
			String orderid = request.getParameter("orderid");
			service.confirmOrder(orderid);
			request.setAttribute("message", "订单已置为发货状态，请及时配送");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "确认失败");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
		}
	}
	@RequestMapping("/orderDetailServlet")
	public void orderDetailServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		Order order = service.findOrder(orderid);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/WEB-INF/pages/manager/orderdetail.jsp").forward(request, response);
	}
	@RequestMapping("/listOrderServlet")
	public void listOrderServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String state = request.getParameter("state");
		List<Order> orders = service.listOrder(state);//这里需要获得该用户所有订单消息，不用只看未发货的(state==false)，在后台会区分未发货和已发货，在前台要罗列在一起
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/WEB-INF/pages/manager/listorder.jsp").forward(request, response);
	}
	@RequestMapping("/listBookServlet")
	public void listBookServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		Page page = service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/manager/listbook.jsp").forward(request, response);
	}
	@RequestMapping("/addBookServlet")
	public void addBookServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Category> category = service.getAllCategory();
		request.setAttribute("categories", category);
		request.getRequestDispatcher("/WEB-INF/pages/manager/addBook.jsp").forward(request,
				response);
	}
	@RequestMapping("/addBook")
	public void addBook(Book book,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			book.setId(UUID.randomUUID().toString());
			System.out.println(book);
			service.addBook(book);
			request.setAttribute("message", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "添加失败");
		}
		request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
	}
	
	@RequestMapping("/listAllCategoryServlet")
	public void listAllCategoryServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Category> CategoryList = service.getAllCategory();
		request.setAttribute("categories", CategoryList);
		request.getRequestDispatcher("/WEB-INF/pages/manager/listcategory.jsp").forward(request, response);
	}
	@RequestMapping("/addcategory")
	public void addcategory(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/manager/addcategory.jsp").forward(request, response);
	}
	@RequestMapping("/addCategoryServlet")
	public void addCategoryServlet(Category category,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			category.setId(UUID.randomUUID().toString());
			service.addCategory(category);
			request.setAttribute("message", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "添加失败");
		}
		request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
	}
	
	@RequestMapping("/managerhead")
	public void managerhead(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/manager/managerhead.jsp").forward(request, response);
	}
	@RequestMapping("/left")
	public void left(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/manager/left.jsp").forward(request, response);
	}
	@RequestMapping("/right")
	public void right(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/manager/right.jsp").forward(request, response);
	}
}
