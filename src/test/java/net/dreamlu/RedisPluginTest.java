package net.dreamlu;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;

/**
 * redis插件测试
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午3:00:56
 */
public class RedisPluginTest {

	/**
	 * JFinal2.0中RedisPlugin源码导读: http://blog.dreamlu.net/blog/72
	 */
	public static void setUp() {
		RedisPlugin redisPlugin = new RedisPlugin("main", "localhost");

//		redisPlugin.setSerializer(JdkSerializer.me);

		redisPlugin.start();
	}

	public static void main(String[] args) {
		setUp();

		Cache mainCache = Redis.use("main");
		String xx  = mainCache.set("key", "value");

		System.out.println(xx);

		String xxx = mainCache.get("key");
		System.out.println(xxx);

		Long xxxx  = mainCache.del("key");
		System.out.println(xxxx);
	}

}
