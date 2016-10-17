package net.dreamlu;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.HttpKit;

public class HttpTest {

	public static void main(String[] args) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Cookie", "oscid=xxx;");
		String html = HttpKit.get("http://my.oschina.net/qq596392912/admin/profile", null, headers);

		System.out.println(html);
	}
}
