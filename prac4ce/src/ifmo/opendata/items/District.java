package ifmo.opendata.items;

import java.util.ArrayList;

public class District {
	private String name;
	private ArrayList<Restriction> restList;
	
	
	public ArrayList<Restriction> getRestList(){
		return new ArrayList<Restriction>(restList);
	}
	
	public String getName(){
		return name;
	}
	
	
	public District attachRestriction(Restriction trgtRestriction){
		restList.add(trgtRestriction);
		return this;
	}
	
	
	public District(String name) {
		restList = new ArrayList<Restriction>();
		this.name = name;
	}
}
