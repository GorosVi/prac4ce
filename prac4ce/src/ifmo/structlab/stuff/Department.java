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
		String res = "Current department: "+name+"/n";
		for (int i=groups.size()-1; i>=0; i--){
			res += "Group :"+groups.get(i).name+"/n";
				for (int j=groups.get(i).studentList.size()-1; j>=0; j--){
					res += "Stdent :"+groups.get(i).studentList.get(j).name;
					for (int k=groups.get(i).studentList.get(j).examResults.size()-1; k>=0; k--)
						res += "Ex:" +groups.get(i).studentList.get(j).examResults.get(k).type.name+
						       "has "+groups.get(i).studentList.get(j).examResults.get(k).qualification+
						       "exams score"+"/n";
			}
		}
		return res;
	}
	
	public void autoKill(){
		// Automatically kill students who not passed current exams
		
	}
	
}