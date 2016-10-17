package net.dreamlu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;

/**
 * 测试控制器
 * @author L.cm
 *
 */
public class TestController extends Controller {

	public void select2() {
		
	}

	public void select2_ajax() {
//		autNumber:    // 搜索的文字
//		pageSize:15   // 分页大小
//		pageNo:1      // 分页页码
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 50; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i + 1);
			map.put("text", i + 1);
			map.put("name", "test" + (i + 1));
			list.add(map);
		}
		renderJson("result", list);
	}

}
