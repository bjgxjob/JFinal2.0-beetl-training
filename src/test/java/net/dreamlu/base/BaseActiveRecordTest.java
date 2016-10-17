package net.dreamlu.base;

import net.dreamlu.model.TUser;

import org.junit.Before;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

public class BaseActiveRecordTest {

	@Before
	public void setUp() {
		PropKit.use("config.txt");

		// 数据库信息
		String jdbcUrl  = PropKit.get("db.jdbcUrl");
		String user     = PropKit.get("db.user");
		String password = PropKit.get("db.password");

		// 配置Druid数据库连接池插件
		DruidPlugin dp = new DruidPlugin(jdbcUrl, user, password);
		dp.addFilter(new StatFilter()).addFilter(new Slf4jLogFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");
		dp.addFilter(wall);
		// 测试中手动启动DruidPlugin
		dp.start();

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.addMapping("t_user", "id", TUser.class);
		arp.setShowSql(true);
		// 测试中手动启动ActiveRecordPlugin
		arp.start();
	}
}
