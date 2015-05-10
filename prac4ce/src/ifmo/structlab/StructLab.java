package ifmo.structlab;

import ifmo.structlab.stuff.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class StructLab extends HttpServlet {

	ArrayList<Department> depList = new ArrayList<Department>();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		int action;
		try{
			action=Integer.parseInt(req.getParameter("action")); 
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}
		if ((action < 0)||(action > 3)){
			resp.getWriter().println("Error: incorrect mode!");
		}
				
		
		switch (action) {
		case 0:
			String depName = req.getParameter("dname");
			if (depName.length() <= 0){
				resp.getWriter().println("Error: no department name");
				return;
			}			
			Department tempDep = new Department(depName);
			resp.getWriter().println(tempDep.getStats());
			depList.add(tempDep);
			break;
		case 1:
			resp.getWriter().println(depList.size());
			break;
		default:
			resp.getWriter().println("Error: incorrect mode!");
		}
	}
}