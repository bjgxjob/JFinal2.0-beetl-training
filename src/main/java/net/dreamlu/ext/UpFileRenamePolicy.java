package net.dreamlu.ext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * JFinal2.0文件上传重命名策略
 * 
 * @author L.cm 
 * email: 596392912@qq.com 
 * site:http://www.dreamlu.net
 * date 2015年7月10日下午11:23:25
 */
public class UpFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File f) {
		// 获取webRoot目录
		String webRoot = PathKit.getWebRootPath();

		// /xxx/JFinal2.0-beetl-training/upload/20150710/1436542837568.txt
		StringBuilder newFileName = new StringBuilder(webRoot)
			.append(getFileDir())
			.append(System.currentTimeMillis())
			.append(getFileExt(f.getName()));

		File dest = new File(newFileName.toString());
		// 创建目录
		File dir = dest.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}

		f.renameTo(dest);
		return dest;
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param @param fileName
	 * @param @return 设定文件
	 * @return String 返回类型
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	public static String getFileDir() {
		StringBuilder newFileDir = new StringBuilder();
		// 用户设置的默认上传目录
		String saveDir = JFinal.me().getConstants().getBaseUploadPath();
		// 添加时间作为目录
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		newFileDir.append(File.separator)
		.append(StrKit.isBlank(saveDir) ? "upload" : saveDir)
		.append(File.separator)
		.append(dateFormat.format(new Date()))
		.append(File.separator);
		return newFileDir.toString();
	}
}
