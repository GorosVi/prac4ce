package ifmo.structlab.stuff;

public class ExamRecord {
	public ExamType type;
	public int qualification;
	//public int receivingWeek;
	
	public ExamRecord (ExamType exT, int qual){
		type = exT;
		qualification = qual;
	}
	
}