package net.dreamlu.ui.beetl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dreamlu.common.WebUtils;
import net.dreamlu.model.TUser;

/**
 * beetl 自定义函数
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年7月4日下午6:05:07
 */
public class Functions {

	/**
	 * 继续encode URL (url,传参tomcat会自动解码)
	 * 要作为参数传递的话，需要再次encode
	 * @param encodeUrl
	 * @return String
	 */
	public String encodeUrl(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return url;
	}

	/**
	 * 获取登录的用户
	 * @return
	 */
	public TUser currentUser(HttpServletRequest request, HttpServletResponse response) {
		return WebUtils.currentUser(request, response);
	}
}
