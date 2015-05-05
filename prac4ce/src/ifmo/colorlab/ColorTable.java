package ifmo.colorlab;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ColorTable extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");	
		
		
		
		//String extMode = req.getParameter("extmode");

		int minMargin =	-1;	
		int maxMargin =	-1;	
		try{
			minMargin = Integer.parseInt(req.getParameter("min"),16);
			maxMargin = Integer.parseInt(req.getParameter("max"),16);
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}
		
		
		// Bounds check
		if ((minMargin > 0xFFFFFF)||(minMargin < 0)){
			resp.getWriter().println("Error in low margin");
			return;
		}
		if ((maxMargin > 0xFFFFFF)||(maxMargin < 0)){
			resp.getWriter().println("Error in high margin");
			return;
		}
		if ((maxMargin - minMargin < 32)){
			resp.getWriter().println("Error : low number space");
			return;
		}
		
		
		//if ((extMode != null) && (extMode.equals("true")))
			for (int i = minMargin; i < maxMargin; i+=(maxMargin-minMargin)/15){
				String colVal = Integer.toHexString(i).toUpperCase();
				while (colVal.length() < 6)
					colVal = "0"+colVal;
				resp.getWriter().println("<div class=\"block-item-big\" id=\"colorlab\"style=\"background-color:#"+
				                          colVal+";\"><sub>#"+colVal
				                          +"</sub></div>");
			}
		//else 
		//	for (int i = minMargin; i <= maxMargin; i++ )
		//		resp.getWriter().println("<div class=\"block-item\">"+(char)i+"</div>");

		resp.getWriter().println("<div class=\"block-clear\"></div>");/**/
	}
}