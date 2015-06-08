package ifmo.opendata.items;

import java.util.ArrayList;

public class RestrType {
	private String name;
	private ArrayList<Restriction> restList;
	
	
	public ArrayList<Restriction> getRestList(){
		return new ArrayList<Restriction>(restList);
	}
	
	public String getName(){
		return name;
	}
	
	
	public RestrType attachRestriction(Restriction trgtRestriction){
		restList.add(trgtRestriction);
		return this;
	}
	
	
	public RestrType(String name) {
		restList = new ArrayList<Restriction>();
		this.name = name;
	}
}
