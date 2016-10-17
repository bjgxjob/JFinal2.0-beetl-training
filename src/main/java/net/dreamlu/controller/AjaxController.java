package net.dreamlu.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.JsonKit;

import net.dreamlu.common.WebUtils;
import net.dreamlu.model.TUser;
import net.dreamlu.validate.LoginValidate;
import net.dreamlu.validate.RegisterValidate;
import net.dreamlu.vo.AjaxResult;

/**
 * ajax控制器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年7月25日 下午5:37:21
 */
public class AjaxController extends Controller {

	private AjaxResult result = new AjaxResult();

	/**
	 * jsonp test
	 * 
	 * $.getJSON("http://localhost:8080/ajax/test?callback=?", function(data) {alert(data)})
	 * 
	 */
	public void test() {
		String callback = getPara("callback");
		
		String json = JsonKit.toJson(true);
		
		String text = callback + "(" + json + ")";
		renderText(text);
	}

	/**
	 * 注册表单提交
	 */
	@Before(RegisterValidate.class)
	public void register() {
		String email = getPara("email");
		String password = getPara("password");

		String nickName = email.split("@")[0];
		// 判断邮箱是否存在
		TUser user = TUser.dao.findByEmail(email);
		if (null != user) {
			result.addError("该邮箱已经注册过，请返回登录或者使用新邮箱地址");
			renderJson(result);
			return;
		}
		user = new TUser();
		user.set("email", email);
		user.set("password", HashKit.md5(password));
		user.set("nickName", nickName);
		boolean temp = user.save();

		if (!temp) {
			result.addError("注册失败，请稍后再试");
		}

		renderJson(result);
	}

	/**
	 * 登录表单提交
	 */
	@Before(LoginValidate.class)
	public void session() {
		String email = getPara("email");
		String password = getPara("password");
		String remember = getPara("remember", "0");

		TUser user = TUser.dao.findByEmail(email);
		if (null == user) {
			renderJson(result.addError("该邮箱尚未注册"));
			return;
		}
		password = HashKit.md5(password);
		// 比较密码
		String oldPwd = user.get("password");
		if (!oldPwd.equals(password)) {
			renderJson(result.addError("您输入的密码不正确"));
			return;
		}
//		setSessionAttr("user", user);

		WebUtils.loginUser(this, user, "1".equals(remember));

		// 记住密码推后讲解
		renderJson(result);
	}

}
