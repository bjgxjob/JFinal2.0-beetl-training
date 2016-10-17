package net.dreamlu.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

/**
 * 用户model
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年7月17日 下午10:54:41
 */
@SuppressWarnings("serial")
public class TUser extends Model<TUser> {

	public static final TUser dao = new TUser();

	// id: 主键id，自增
	// nickName: 昵称
	// email: 邮箱
	// password: 密码
	// role: 角色0:普通用户，1:vip,2:admin

	// 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId

	// java中的数据类型和mysql中的数据类型
	// http://dev.mysql.com/doc/connector-j/en/connector-j-reference-type-conversions.html

	/**
	 * 根据邮箱查找用户
	 * @param email
	 * @return TUser
	 */
	public TUser findByEmail(String email) {
		String sql = "SELECT * FROM t_user WHERE email = ? LIMIT 1";
		return this.findFirst(sql, email);
	}

	/**
	 * 从缓存中加载用户信息
	 * @param userId
	 * @return
	 */
	public TUser loadInSession(String userId) {
		// 传入的userId为空直接返回null
		if (StrKit.isBlank(userId)) {
			return null;
		}
		return loadInSession(Long.parseLong(userId));
	}

	private static final String USER_CACHE_NAME = "session";

	/**
	 * 从缓存中加载用户信息
	 * @param userId
	 * @return
	 */
	public TUser loadInSession(final long userId) {
		return CacheKit.get(USER_CACHE_NAME, userId, new IDataLoader() {

			@Override
			public Object load() {
				return findById(userId);
			}
		});
	}

}
