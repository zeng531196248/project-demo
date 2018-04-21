package cn.tbnb1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name="Download",urlPatterns="/download")
public class Download extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String fileName=request.getParameter("fileName");  
		   String root=request.getServletContext().getRealPath("/upload");
		   String filename=root+"\\"+fileName;
		   File f = new File(filename);  
	        if(f.exists()){  
	            FileInputStream  fis = new FileInputStream(f);  
	            byte[] b = new byte[fis.available()];  
	            fis.read(b);  
	            response.setCharacterEncoding("utf-8");  
	            response.setHeader("Content-Disposition","attachment; filename="+fileName+"");  
	            //获取响应报文输出流对象  
	            ServletOutputStream  out =response.getOutputStream();  
	            //输出  
	            out.write(b);  
	            out.flush();  
	            out.close();  
	        } 
	        
	        
	        
	        
	        }
	
	}
	
	
	
	
