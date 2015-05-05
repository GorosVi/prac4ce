package ifmo.prac3e;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SquareRoots extends HttpServlet implements Servlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		double a,b,c;
		try{
			a=Double.parseDouble(req.getParameter("A"));
			b=Double.parseDouble(req.getParameter("B"));
			c=Double.parseDouble(req.getParameter("C"));
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}
		
		double d=b*b-4*a*c;
		
		if((d>=0) && (a!=0)){
			resp.getWriter().print("X<sub>1</sub>="+(-b+Math.sqrt(d))/2/a+"; &nbsp&nbsp; X<sub>2</sub>="+(-b-Math.sqrt(d))/2/a);
		} else {
			resp.getWriter().print("None");
		}
	}

}
