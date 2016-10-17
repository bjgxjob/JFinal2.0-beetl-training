package net.dreamlu.controller;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.BeetlKit;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.core.Controller;

import net.dreamlu.utils.EmailUtils;
import net.dreamlu.utils.EmailUtils.MailData;

/**
 * 右键控制器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年9月27日 下午3:46:29
 */
public class EmailController extends Controller {

	/**
	 * 普通邮件发送
	 */
	public void index() {
		boolean ok = EmailUtils.sendMail(MailData.New()
				.subject("hello")
				.content("hello")
				.to("596392912@qq.com"));

		renderText(ok + "");
	}

	/**
	 * 模板邮件发送
	 */
	public void template() {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("nickName", "Dreamlu");

		// BeetlKit 的gt默认是使用默认配置new的一个，这里指定为项目的模板
		BeetlKit.gt = BeetlRenderFactory.groupTemplate;
		String content = BeetlKit.render("/email/test.html", paras);

		EmailUtils.asynMail(MailData.New()
				.subject("hello")
				.content(content)
				.to("596392912@qq.com"));

		renderNull();
	}

}
