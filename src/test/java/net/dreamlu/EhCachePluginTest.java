package net.dreamlu;

import org.junit.Before;
import org.junit.Test;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * EhCache插件测试
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午3:00:56
 */
public class EhCachePluginTest {

	@Before
	public void setUp() {
		EhCachePlugin ehCachePlugin = new EhCachePlugin();

		ehCachePlugin.start();
	}

	String cacheName = "session";

	@Test
	public void testPut() {
		Record value = new Record();
		value.set("id", "123123");

		CacheKit.put(cacheName, "key", value);
	}

	@Test
	public void testGet() {
		Record value = CacheKit.get(cacheName, "key");

		System.out.println(JsonKit.toJson(value));
	}

}
