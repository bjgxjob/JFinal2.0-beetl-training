package net.dreamlu;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.BeetlKit;

import net.dreamlu.utils.EmailUtils;
import net.dreamlu.utils.EmailUtils.MailData;

public class EmailTest {

//	@Test
	public void test1() {
		EmailUtils.sendMail(MailData.New()
				.subject("hello")
				.content("中秋快乐！")
				.to("596392912@qq.com"));
	}

//	@Test
	public void test2() {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("nickName", "Dreamlu！");

		String html = BeetlKit.render("/email/test.html", paras);
		System.out.println(html);
	}
}
