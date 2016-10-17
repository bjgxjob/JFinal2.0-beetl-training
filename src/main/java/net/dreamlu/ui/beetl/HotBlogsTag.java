package net.dreamlu.ui.beetl;

import java.util.List;
import java.util.Map;

import org.beetl.core.GeneralVarTagBinding;

import net.dreamlu.model.Blog;

/**
 * 设计一个用来获取热门博客的组件
 * 
 * <#hotBlog limit="5" order="id desc|asc"; x>
 * ${x.id}, ${x.title} <br/>
 * </#hotBlog>
 * 
 */
public class HotBlogsTag extends GeneralVarTagBinding {

	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		Object[] tags = this.args;
		Map<String, Object> paras = (Map<String, Object>) this.args[1];

		List<Blog> blogs = Blog.me.findByMap(paras);
		if (null != blogs) {
			for (Blog blog : blogs) {
				this.binds(blog);
				this.doBodyRender();
			}
		}
	}

}
