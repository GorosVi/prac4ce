package ifmo.structlab.stuff;

import java.util.*;

public class Group {
	
	public String name;
	public ArrayList<Student> studentList;
	public ArrayList<ExamType> examList;
	
	public Group(String name){	
		this.name = name;
		studentList = new ArrayList<Student>();
		examList = new ArrayList<ExamType>();
	}

	public int getStudentsNumber(){
		return studentList.size();
	}
	
	public void addStudent(String name){
		Student cStudent = new Student(name);
		studentList.add(cStudent);
	}
	
	public void addExam(String name){
		ExamType exT = new ExamType(name);
		examList.add(exT);
	}
	
	public ArrayList<Student> getOutsiders(){
		ArrayList<Student> outList = new ArrayList<Student>();
		for (int i=studentList.size()-1; i>=0; i--){
			if (studentList.get(i).getPassedExams().size() < examList.size()-1)
				outList.add(studentList.get(i));
		}
		return outList;
	}
	
	public void killStudent(Student trgtStud){
		//System.out.println("We was kill "+name);
		studentList.remove(trgtStud);
	}

	public String getStats(){
		String res = ("<br>Current group: "+name+"<br>");
			for (int j=studentList.size()-1; j>=0; j--){
				res += "Stdent: "+studentList.get(j).name+"<br>";
				for (int k=studentList.get(j).examResults.size()-1; k>=0; k--)
					res += "&nbsp;Ex: " +studentList.get(j).examResults.get(k).type.name+
					       " has "+studentList.get(j).examResults.get(k).qualification+
					       " exams score"+"<br>";
		}
		return res;
	}
}