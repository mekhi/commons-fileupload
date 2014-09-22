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

		response.setCharacterEncoding("UTF-8"); // ���ô�������ı��뷽ʽ,��ֹ��������
		response.setContentType("text/html;charset=UTF-8"); // ����Content-Type�ֶ�ֵ
		PrintWriter out = response.getWriter();

		// ʵ����һ��Ӳ���ļ����������������ļ��ϴ����ServletFileUpload
		FileItemFactory factory = new DiskFileItemFactory();
		// ����Ӳ���ļ����������ļ��ϴ����
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null; // ���FileItem����
		try { // ��ȡ�ļ���FileItem���󣬼�����,��Ϊ��ͨ������ļ���
			items = upload.parseRequest(request);

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		String path = null; // ����ϴ��ļ����������ƣ�����·����
		String filename = " "; // ����ļ���
		InputStream is = null;
		// ѭ�������ϴ��ļ�
		for (FileItem item : items) {
			if (item.isFormField()) {
				if (item.getFieldName().equals("filename")) {
					if (!item.getString().equals(""))
						filename = item.getString("UTF-8");
				}
			} else if (item.getName().trim() != null
					&& !item.getName().trim().equals(" ")) {
				path = item.getName();// �õ��ļ�����·��
				filename = path.substring(path.lastIndexOf("\\") + 1);
				is = item.getInputStream(); // ����ϴ��ļ���InputStream����
			}
		}
		filename = request.getRealPath("/") + "Uploaded/" + filename;
		if (new File(filename).exists()) {
			out.println("���ļ��Ѿ����ڣ���Ϊ�ļ�ָ��һ���µ��ļ�����");
		} else if (!filename.equals("")) {
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[8192];
			int count = 0;

			// ��ʼ��ȡ�ϴ��ļ����ֽڣ�������������������˵��ϴ��ļ��� ������
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count); // ����������ļ�д���ֽ���
			}
			fos.close();
			is.close();
			out.println("�ϴ��ɹ���");
		}
	}

}