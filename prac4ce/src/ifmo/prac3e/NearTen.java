package ifmo.prac3e;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class NearTen extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		double N,M;
		try{
			N=Double.parseDouble(req.getParameter("N")); 
			M=Double.parseDouble(req.getParameter("M"));
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}
		
		if(Math.abs(N-10) > Math.abs(M-10)){
			resp.getWriter().println(M);
			}
		else{
			resp.getWriter().println(N);
		}
	}
}
