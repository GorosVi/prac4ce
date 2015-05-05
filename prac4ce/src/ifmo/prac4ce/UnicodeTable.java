package ifmo.prac4ce;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class UnicodeTable extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		String extMode = req.getParameter("extmode");

		int minMargin =	-1;	
		int maxMargin =	-1;	
		try{
			minMargin = Integer.parseInt(req.getParameter("min"));
			maxMargin = Integer.parseInt(req.getParameter("max"));
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}
		
		
		// Bounds check
		if ((minMargin > 65535)||(minMargin < 0)){
			resp.getWriter().println("Error in low margin");
			return;
		}
		if ((maxMargin > 65535)||(maxMargin < 0)){
			resp.getWriter().println("Error in high margin");
			return;
		}
		
		
		if ((extMode != null) && (extMode.equals("true")))
			for (int i = minMargin; i <= maxMargin; i++ )
				resp.getWriter().println("<div class=\"block-item-big\"><b>"
				                          +(char)i+"</b><sub></br>"
				                          +i+"</br> #"
				                          +Integer.toHexString(i).toUpperCase()+"</sub></div>");
		else 
			for (int i = minMargin; i <= maxMargin; i++ )
				resp.getWriter().println("<div class=\"block-item\">"+(char)i+"</div>");

		resp.getWriter().println("<div class=\"block-clear\"></div>");
	}
}