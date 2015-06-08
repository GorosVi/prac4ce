package ifmo.opendata.items;

import java.util.ArrayList;

public class Company {
	private String name;
	private long credINN;
	private ArrayList<Restriction> asCustomer;
	private ArrayList<Restriction> asContractor;

	
	public ArrayList<Restriction> getAsCustomerList(){
		return new ArrayList<Restriction>(asCustomer);
	}
	
	public ArrayList<Restriction> getAsContractorList(){
		return new ArrayList<Restriction>(asContractor);
	}
	

	public boolean isCustomer(){
		return asCustomer.size() > 0 ? true : false;
	}
	
	public boolean isContractor(){
		return asContractor.size() > 0 ? true : false;
	}
	
	
	public String getName(){
		return name;
	}

	public long getINN(){
		return credINN;
	}

	
	public Company attachAsCustomer(Restriction trgtRestriction){
		asCustomer.add(trgtRestriction);
		return this;
	}
	
	public Company attachAsContractor(Restriction trgtRestriction){
		asContractor.add(trgtRestriction);
		return this;
	}	

	
	Company(String name, long credINN){
		asCustomer = new ArrayList<Restriction>();
		asContractor = new ArrayList<Restriction>();
		this.name = name;
		this.credINN = credINN;
	}
}
