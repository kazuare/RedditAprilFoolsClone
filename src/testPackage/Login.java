package testPackage; // Always use packages. Never use default package.

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


@WebServlet("/login")
public class Login extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
	  HttpSession session = request.getSession(true);
	  
	  long t = System.currentTimeMillis();
	  
	  if(session.getAttribute("lastModified") == null || t - (Long)session.getAttribute("lastModified") > 10000){
		  
		  session.setAttribute("lastModified", t);
		  
		  URL url = new URL("http://80.87.202.12:8080/epam-app/world");
		  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		 
		  byte[] out = 
		  (
			  "{\"x\":\""+ (String)request.getParameter("x")+"\",\"y\":\""+ 
			  (String)request.getParameter("y")+"\",\"color\":\""+ 
			  (String)request.getParameter("color")+"\"}"
		  )
		  .getBytes(StandardCharsets.UTF_8);
		  int length = out.length;
		  conn.setDoOutput(true);
		  conn.setFixedLengthStreamingMode(length);
		  conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		  conn.connect();
		  try(OutputStream os = conn.getOutputStream()) {
		      os.write(out);
		  }
		  
		  PrintWriter o = response.getWriter();
			 
		  o.print("OK");
		  
		  o.close();
	  }else{
		  PrintWriter o = response.getWriter();
			 
		  o.print("Only " + (t - (Long)session.getAttribute("lastModified"))/1000 + " seconds elapsed, so please wait!");
		  
		  o.close();
	  }
  }
}
