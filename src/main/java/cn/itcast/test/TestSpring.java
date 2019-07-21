package cn.itcast.test;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.domain.Category;
import cn.itcast.service.BusinessService;
public class TestSpring {
	@Test
	public void run1() {
		//加载Spring配置文件
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		//获取对象
		BusinessService accountService  = (BusinessService)ac.getBean("businessService");
		//调用方法
		List<Category> cg=accountService.getAllCategory();
		for(Category category:cg) {System.out.println(category);
	}
}
}
