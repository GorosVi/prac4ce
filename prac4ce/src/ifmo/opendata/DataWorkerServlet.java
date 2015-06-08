package ifmo.opendata;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DataWorkerServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		ifmo.opendata.items.Container c = new ifmo.opendata.items.Container("data/data.csv");
		resp.getWriter().println(c.getLog().replaceAll("\n","<br>"));
	}

}