package ifmo.structlab.stuff;

import java.util.*;

public class Department {
	private String name;
	public ArrayList<Group> groups;
	
	public String getName(){
		return name;
	}
	
	public int getStudentsNumber(){
		int studCount = 0;
		for (int i = groups.size()-1; i >= 0; i--){
			studCount += groups.get(i).getStudentsNumber();
		}
		return studCount;
	}

	public Department(String name){
		this.name = name;
		groups = new ArrayList<Group>();
	};

	public void addGroup(String name){
		Group cGr = new Group(name);
		groups.add(cGr);		
	}
	
	public String getStats(){
		String res = "<br>Current department: "+name+"; "+getStudentsNumber()+" students <br>";
		for (int i = groups.size()-1; i>=0; i--){
			res += "Group: "+groups.get(i).name+"<br>";
				for (int j = groups.get(i).studentList.size()-1; j>=0; j--){
					res += "&nbsp;Stdent: "+groups.get(i).studentList.get(j).name+"<br>";
					for (int k = groups.get(i).studentList.get(j).examResults.size()-1; k>=0; k--)
						res += "&nbsp;&nbsp;Ex: " +groups.get(i).studentList.get(j).examResults.get(k).type.name+
						       " has "+groups.get(i).studentList.get(j).examResults.get(k).qualification+
						       " exams score"+"<br>";
			}
		}
		return res;
	}
	
	public void autoKill(){
		// Automatically kill students who not passed current exams
		
	}
	
}