package ifmo.prac3e;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RandomArrays extends HttpServlet implements Servlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		String extMode = req.getParameter("extmode");
		
		if ((extMode != null) && (extMode.equals("true"))){
			resp.getWriter().println("<div style=\"width:320px;\">");
			int[][] mas = new int[5][8];
			int max=0;
			for(int i=0;i<5;i++){
				for(int j=0;j<8;j++){
					mas[i][j] = Math.round(-99+(int)(Math.random()*199));
					max = ((i==0) && (j==0))?mas[i][j]:max;
					if(mas[i][j]>max)
						max = mas[i][j];
					resp.getWriter().println("<div class=\"block-item\">"+mas[i][j]+"</div>");
				}
				resp.getWriter().println("<div class=\"block-clear\"></div>");
			}
			resp.getWriter().println("<br> \n Max = " + max);
		} else {
			resp.getWriter().println("<div style=\"width:200px;\">");
			int[][] mas = new int[8][5];
			for(int i=0;i<8;i++){
				for(int j=0;j<5;j++){
					mas[i][j] = Math.round(10+(int)(Math.random()*89));
					resp.getWriter().println("<div class=\"block-item\">"+mas[i][j]+"</div>");
				}
				resp.getWriter().println("<div class=\"block-clear\"></div>");
			}
		}
		resp.getWriter().println("</div>");
	}

}
