<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<!DOCTYPE form PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<form action="${pageContext.request.contextPath}/uploadServlet" method="post"enctype="multipart/form-data">
		
			<input id="myfile" name="file" type="file" /> <input type="submit" value="提交" />${result}
	</form>
	下载：
 <a href="${pageContext.request.contextPath}/download?fileName=86b262cd-2e56-4142-a457-5e0b78f66e04.java">文件</a>
	   ${info}