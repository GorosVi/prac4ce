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
			tempDep.addGroup(((Integer)(int)(Math.random()*3000+1000)).toString());
			// Generate exams
			for (int i=(int)(Math.random()*10+2); i>=1; i--)
				tempDep.groups.get(0).addExam("Exam"+i);
			// Generate students
			for (int i=(int)(Math.random()*10+1); i>=1; i--)
				tempDep.groups.get(0).addStudent("Stud"+i);
			resp.getWriter().println(tempDep.getStats());
			depList.add(tempDep);
			break;
		case 1: // Get Info
			for (int i=depList.size()-1; i>=0; i--)
				resp.getWriter().println(depList.get(i).getStats());
			break;
		case 2: // Call exams
			for (int i=depList.size()-1; i>=0; i--)
				for (int j=depList.get(i).groups.size()-1; j>=0; j--)
					for (int k=depList.get(i).groups.get(j).studentList.size()-1; k>=0; k--){
						for (int l=depList.get(i).groups.get(j).examList.size()-1; l>=0; l--){
							int qual=0;
							if (Math.random()>0.8){ // Ну не сдал, бывает
								qual=10+(int)(Math.random()*50);
								resp.getWriter().println(depList.get(i).groups.get(j).studentList.get(k).name+
								      " not pass the exam "+depList.get(i).groups.get(j).examList.get(l).name+"<br>");
							}
							else // Сдал.
								qual=60+(int)(Math.random()*40);	
							depList.get(i).groups.get(j).studentList.get(k).addExamPoint(depList.get(i).groups.get(j).examList.get(l), qual);
						}
					}
			
			break;
		case 3: // Kill unpassers
			for (int i=depList.size()-1; i>=0; i--)
				for (int j=depList.get(i).groups.size()-1; j>=0; j--){
					ArrayList<Student> blackList = depList.get(i).groups.get(j).getOutsiders();
					for (int k=blackList.size()-1; k>=0; k--){
						resp.getWriter().println("Student "+blackList.get(k).name+
								" from group "+ depList.get(i).groups.get(j).name+
								" of department "+ depList.get(i).getName()+
								" not pass exams and was banished from university <br>");
						depList.get(i).groups.get(j).killStudent(blackList.get(k));
					}
				}
			break;
		case 4:
			break;
		default:
			resp.getWriter().println("Error: incorrect mode!");
		}
	}
}