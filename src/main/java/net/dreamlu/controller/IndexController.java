package net.dreamlu.controller;

import com.jfinal.core.Controller;

/**
 * 首页控制器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * @date 2015年7月8日下午10:05:55
 */
public class IndexController extends Controller {

	/**
	 * 首页
	 */
	public void index() {
	    //设置变量
		setAttr("hello", "hello beetl JFinal2.0~");
	}

	/**
	 * 博客页
	 */
	public void blog() {
		
	}

	/**
	 * 登录页
	 */
	public void login() {
		// url 传递的参数会自动URLDecoder一次，默认跳转/admin
		String from = getPara("from", "/admin");

		// 将from再次传递到页面供js使用，如果需要再次传递到其他控制器需要再次urlEncode
		// 可采用 beetl函数去页面上urlEncode
		// 此处的from需要限定一样域名，防止攻击（用户登录之后直接跳转）
		// 例如：http://localhost:8080/login?from=http://www.oschina.net，不做限制的话登录完成之后将跳入开源中国

		setAttr("from", from);
	}

	/**
	 * 注册页
	 */
	public void register() {
		String from = getPara("from", "/admin");
		setAttr("from", from);
	}

	/**
	 * 验证码
	 */
	public void image_code() {
		renderCaptcha();
	}
}
