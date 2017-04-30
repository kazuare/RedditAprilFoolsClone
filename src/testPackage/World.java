package testPackage; // Always use packages. Never use default package.

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


@WebServlet("/world")
public class World extends HttpServlet {
  String g;
  String[][] world;
  @Override
  public void init(){
	  world = new String[1000/50][600/50];
	  for(int i = 0; i < world.length; i++)
		  for(int j = 0; j < world[i].length; j++)
			  world[i][j] = "white";
  }
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  request.setCharacterEncoding("UTF-8");
	  response.setCharacterEncoding("UTF-8");
		
	  PrintWriter out = response.getWriter();
	 
	  for(int i = 0; i < world.length; i++){
		  for(int j = 0; j < world[i].length; j++)
			  out.print(world[i][j] + " ");
		  out.println();
	  }
	  
	  out.close();
	 
	
  }
  
  
  @Override
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
	 String x = null;
	 String y = null;
	 String color = null;

	 g = (String)request.getReader().readLine();
	 
	 for(int i = 0; i < 3; i++){
		 g = g.substring(g.indexOf('"')+1);
		 String property = g.substring(0,g.indexOf('"'));
		 g = g.substring(g.indexOf('"')+1);
		 g = g.substring(g.indexOf('"')+1);
		 String value = g.substring(0,g.indexOf('"'));
		 if(property.equals("x"))
			 x = value;
		 if(property.equals("y"))
			 y = value;
		 if(property.equals("color"))
			 color = value;

		 g = g.substring(g.indexOf('"')+1);
	 }
			 
	 world[Integer.parseInt(x)][Integer.parseInt(y)] = color;
	 
  }
}
