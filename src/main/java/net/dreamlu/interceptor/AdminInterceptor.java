package net.dreamlu.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

import net.dreamlu.common.WebUtils;
import net.dreamlu.model.TUser;

/**
 * 管理后台拦截器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午5:02:14
 */
public class AdminInterceptor implements Interceptor {

	private final Log logger = Log.getLog(getClass());

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
//		TUser user = controller.getSessionAttr("user");
		TUser user = WebUtils.currentUser(controller);

		if (null != user) {
			inv.invoke();
			return;
		}
		// 登陆页URL
		String loginPath = "/login?from=%s";

		HttpServletRequest request = controller.getRequest();
		String queryString = request.getQueryString();
		// 被拦截前的请求URL
		String redirectUrl = request.getRequestURI();
		if (StrKit.notBlank(queryString)) {
			redirectUrl = redirectUrl.concat("?").concat(queryString);
		}
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		String tagPath = String.format(loginPath, redirectUrl);
		logger.info("login[redirectUrl]:\t" + tagPath);

		controller.redirect(tagPath);
	}

}
