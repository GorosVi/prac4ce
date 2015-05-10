package ifmo.structlab.stuff;

import java.util.ArrayList;

public class Student {
	
	public String name;
	//public Group group;
	public ArrayList<ExamRecord> examResults;
	
	public Student(String name){
		examResults = new ArrayList<ExamRecord>();
		this.name = name;
	}

	public ArrayList<ExamRecord> getPassedExams(){
		// Check with 60 points, out list
		ArrayList<ExamRecord> passedExamList = new ArrayList<ExamRecord>();
		for (int i = examResults.size()-1; i >= 0; i--){
			if (examResults.get(i).qualification >= 60 )
				passedExamList.add(examResults.get(i));
		}
		return passedExamList;
	}
	
	public void AddExamPoint(ExamType exT, int qual){
		// if not in group = error - not implemented
		int isModified = 0;
		// if existing = modify
		for (int i = examResults.size()-1; i >= 0; i--){
			if (examResults.get(i).type == exT){
				isModified = 1;
				examResults.get(i).qualification = qual;
			}
		}
		// if nothing found = add
		if (isModified == 0){
			ExamRecord exRec = new ExamRecord(exT, qual);
			examResults.add(exRec);
		}
	}

	public String getStats(){
		String res = ("Student: "+name+"/n");
			for (int k=examResults.size()-1; k>=0; k--)
				res += "Ex:" +examResults.get(k).type.name+
				       "has "+examResults.get(k).qualification+
				       "exams score"+"/n";
		return res;
	}	
}