package net.dreamlu.utils;

import com.jfinal.kit.StrKit;

/**
 * 云环境的探针
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年10月15日 下午10:52:07
 */
public class CloudUtils {

	private static final String IN_MOPAAS = "in_mopaas";
	private static final String IN_DOCKER = "in_docker";

	/**
	 * 获取env信息，既系统环境信息
	 * @param name
	 * @return
	 */
	public static String getEnv(String name) {
		return System.getenv(name);
	}

	/**
	 * 判断是否在Mopaas环境中
	 * @return {boolean}
	 */
	public static boolean isInMopaas() {
		String in = getEnv(IN_MOPAAS);
		return StrKit.notBlank(in);
	}

	/**
	 * 判断是否在Docker环境中
	 * @return {boolean}
	 */
	public static boolean isInDocker() {
		String in = getEnv(IN_DOCKER);
		return StrKit.notBlank(in);
	}

}
