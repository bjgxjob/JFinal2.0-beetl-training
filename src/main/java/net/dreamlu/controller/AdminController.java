package net.dreamlu.controller;

import java.util.Properties;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.interceptor.AdminInterceptor;
import net.dreamlu.model.Blog;
import net.dreamlu.service.TestService;
import net.dreamlu.vo.DataTables;

/**
 * 管理后台控制器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午3:54:59
 */
@Before(AdminInterceptor.class)
public class AdminController extends Controller {

	// 服务层
	private TestService s = enhance(TestService.class);

	public void index() {
		// 获取系统信息
		Properties systemPro = System.getProperties();
		setAttr("systemPro", systemPro);
	}

	public void test() {
		// 仅作演示
		s.saveUser();
		renderText("test");
	}

	/**
	 * 博文管理
	 */
	public void blogs() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<Blog> page = Blog.me.paginate(pageNumber, pageSize);
		setAttr("page", page);
	}

	public void data_tables() {
		
	}

	public void ajax_data_tables() {
		int start = getParaToInt("start", 0);
		int pageSize = getParaToInt("length", 10);
		int pageNumber = start / pageSize + 1;

		String search = getPara("search[value]");
		
		Page<Blog> page = Blog.me.paginate(pageNumber, pageSize);
		renderDataTable(page);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void renderDataTable(Page page) {
		int draw = getParaToInt("draw", 0);
		renderJson(new DataTables(draw, page));
	}

	/**
	 * 博客编辑
	 */
	public void blog_edit() {
		renderJson(Blog.me.findById(getParaToInt()));
	}
}
