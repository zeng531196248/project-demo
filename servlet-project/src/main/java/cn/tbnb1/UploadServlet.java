package cn.tbnb1;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UploadServlet", urlPatterns = "/uploadServlet")
@MultipartConfig(   maxFileSize = 5*1024*1024  )  
public class UploadServlet extends HttpServlet {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/upload/upload.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	            //获取上传的文件
	            Part part=request.getPart("file");
	            //获取请求的信息
	            String name=part.getHeader("content-disposition");
	            //获取上传文件的目录
	            String root=request.getServletContext().getRealPath("/upload");
	            File file =new File(root);
	            if(!file.exists()) {
	            	file.mkdir();
	            }
	            //获取文件的后缀
	            String str=name.substring(name.lastIndexOf("."), name.length()-1);
	            
	            //生成一个新的文件名，不重复，数据库存储的就是这个文件名，不重复的
	            String filename=root+"\\"+UUID.randomUUID().toString()+str;
	            part.write(filename);
	            request.setAttribute("info", "上传文件成功");
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("info", "上传文件失败");
	        }
	        request.getRequestDispatcher("/WEB-INF/jsp/upload/upload.jsp").forward(request, response);
	    }
		
}
