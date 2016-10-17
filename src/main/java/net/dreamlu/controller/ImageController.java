package net.dreamlu.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;

import net.dreamlu.ext.UpFileRenamePolicy;
import net.dreamlu.utils.KindEditorUtils;

/**
 * 图片控制器，用于
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月18日下午2:52:46
 */
public class ImageController extends Controller {

	/**
	 * 首页
	 */
	public void index() {}

	/**
	 * 
	 * jquery 图片截取插件
	 * 
	 * http://code.ciaoca.com/jquery/jcrop/demo/crop.php
	 * @throws IOException 
	 */
	public void crop() throws IOException {
		// 获取 jcrop 组件拿到的参数
		Integer x = getParaToInt("x");
		Integer y = getParaToInt("y");
		Integer w = getParaToInt("w");
		Integer h = getParaToInt("h");

//		注意：此处不支持 gif 的动态图 

//		拼接测试图片文件的目录
		String root = PathKit.getWebRootPath();

		String filePath = root + "/static/jcrop/demos/demo_files/pool.jpg";
		filePath = filePath.replace('/', File.separatorChar);

		// 调取java 相关api 截图
		BufferedImage bufferedImage = ImageIO.read(new File(filePath));
		// 核心api，java截图
		BufferedImage out = bufferedImage.getSubimage(x, y, w, h);

		ImageIO.write(out, "JPEG", getResponse().getOutputStream());
		renderNull();
	}

	/**
	 * ajax 图片上传
	 * 
	 * 图片上传时注意： 在校验器、拦截器中都得先getFile()
	 * 
	 */
	public void upload() {}

	/**
	 * 图片上传逻辑
	 */
	public void do_upload() {
		List<UploadFile> files = getFiles();
		String html = "upload images";
		for (UploadFile uploadFile : files) {
			if (null != uploadFile) {
				String fileName = uploadFile.getFileName();
				String imgUrl = UpFileRenamePolicy.getFileDir() + fileName;

				html += "<img src=\"" + imgUrl + "\"><br/>";
			}
		}
		renderHtml(html);
	}

	public void ajax_upload() {
		UploadFile up = getFile("file1");
		String url = "";
		if (null != up) {
			String fileName = up.getFileName();
			url = UpFileRenamePolicy.getFileDir() + fileName;
		}
		render(new JsonRender(url).forIE());
	}

	/**
	 * kindeditor编辑器
	 */
	public void kindeditor() {}

	/**
	 * 文档：http://kindeditor.net/docs/upload.html
	 * 
	 * 参数：
	 * dir=image|file|media|flash
	 * 
	 *  //成功时
		{
		        "error" : 0,
		        "url" : "http://www.example.com/path/to/file.ext"
		}
		//失败时
		{
		        "error" : 1,
		        "message" : "错误信息"
		}
	 */
	public void upload_json() {
		UploadFile uploadFile = getFile();
		if (null == uploadFile) {
			setAttr("error", 1);
			setAttr("message", "请选择要上传的图片");
			render(new JsonRender().forIE());
			return;
		}
		String fileName = uploadFile.getFileName();

		// 保存时应该根据dir参数生成目录，这块得写工具类去移动图片
		String dir = getPara("dir", "image");
		// 测试后缀
		boolean ok = KindEditorUtils.testExt(dir, fileName);
		if (!ok) {
			setAttr("error", 1);
			setAttr("message", "上传文件的类型不允许");
			render(new JsonRender().forIE());
			return;
		}
		String url = UpFileRenamePolicy.getFileDir() + fileName;
		setAttr("error", 0);

		// 对URL添加ctxPath，避免（formatUploadUrl：格式化上传后的URL，默认true，会添加上ctxPath，有问题）
		String ctxPath  = JFinal.me().getContextPath();
		setAttr("url", ctxPath + url);
		render(new JsonRender().forIE());
	}

	/**
	 * 文件管理
	 * 
	 * 参数：
	 * path=
	 * order=NAME|SIZE|TYPE
	 * dir=image|file|media|flash
	 * 
	 * 理论上应该根据dir来生成目录，获取资源时只获取对应目录下的资源
	 */
	public void file_manager_json() {
		String dir   = getPara("dir", "image");
		// 考虑安全问题
		String path  = getPara("path", "");
		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			renderText("Access is not allowed.");
			return;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			renderText("Parameter is not valid.");
			return;
		}
		String order = getPara("order", "name");

		Map<String, Object> result = KindEditorUtils.listFiles(dir, path, order);
		render(new JsonRender(result).forIE());
	}

}
