package com.mekhi.uploadfile;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @Author: mekhi   --> https://github.com/mekhi
 * @Create Date: 2014-09-01
 */ 
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8"); // 设置处理请求的编码方式,防止中文乱码
		response.setContentType("text/html;charset=UTF-8"); // 设置Content-Type字段值
		PrintWriter out = response.getWriter();

		// 实例化一个硬盘文件工厂，用来配置文件上传组件ServletFileUpload
		FileItemFactory factory = new DiskFileItemFactory();
		// 利用硬盘文件工厂配置文件上传组件
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null; // 存放FileItem对象
		try { // 获取文件的FileItem对象，即表单域,分为普通表单域和文件域
			items = upload.parseRequest(request);

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		String path = null; // 存放上传文件的完整名称，包括路径。
		String filename = " "; // 存放文件名
		InputStream is = null;
		// 循环处理上传文件
		for (FileItem item : items) {
			if (item.isFormField()) {
				if (item.getFieldName().equals("filename")) {
					if (!item.getString().equals(""))
						filename = item.getString("UTF-8");
				}
			} else if (item.getName().trim() != null
					&& !item.getName().trim().equals(" ")) {
				path = item.getName();// 得到文件完整路径
				filename = path.substring(path.lastIndexOf("\\") + 1);
				is = item.getInputStream(); // 获得上传文件的InputStream对象
			}
		}
		filename = request.getRealPath("/") + "Uploaded/" + filename;
		if (new File(filename).exists()) {
			out.println("该文件已经存在，请为文件指定一个新的文件名！");
		} else if (!filename.equals("")) {
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[8192];
			int count = 0;

			// 开始读取上传文件的字节，并将其输出到服务器端的上传文件输 出流中
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count); // 向服务器端文件写入字节流
			}
			fos.close();
			is.close();
			out.println("上传成功！");
		}
	}

}