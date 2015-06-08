package ifmo.opendata.items;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.opencsv.CSVReader;

public class Container {
	private ArrayList<Restriction> restrList;
	private ArrayList<District> distrList;
	private ArrayList<RestrType> restrTypeList;
	private ArrayList<Company> companyList;

	final boolean debug = false;
	private String log = "";
	
	@SuppressWarnings("unused")
	private void parseCSV(String filename) throws IOException{
		if (debug) log += "Log started =================================> \n";
		if (debug) log += "Reading file "+filename+"... \n";
		
		CSVReader cReader = null;
		try{
			String[] nextLine;
			cReader = new CSVReader(new FileReader(filename));
			// Bypass first line
			cReader.readNext();
			while ((nextLine = cReader.readNext()) != null) {
				if (nextLine.length != 11){
					if (debug) log += "Error: bad row with id "+nextLine[0]+" \n";
					continue;
				}
				try{
					int restCode = Integer.parseInt(nextLine[0]);
					SimpleDateFormat restDF = new SimpleDateFormat("dd.MM.yyyy");
					// Bug: no exception for bad format
					Date restDateStart = restDF.parse(nextLine[9]);
					Date restDateEnd   = restDF.parse(nextLine[10]);
					//                                              disposalNum  address
					Restriction cuRestr = new Restriction(restCode, nextLine[1], nextLine[3], restDateStart, restDateEnd);
					
					// Район
					// Create double-sided link between objects
					int index = findDistrict(nextLine[2]);
					if (index >= 0)
						cuRestr.setDistrict(distrList.get(index).attachRestriction(cuRestr));
					else{
						//if (debug) log += "District <"+ nextLine[2] +"> added. \n";
						District curDistr = new District(nextLine[2]);
						distrList.add(curDistr);
						cuRestr.setDistrict(curDistr.attachRestriction(cuRestr));
					};
					
					// Вид ограничения
					// Create double-sided link between objects
					index = findRestrType(nextLine[4]);
					if (index >= 0)
						cuRestr.setRestrType(restrTypeList.get(index).attachRestriction(cuRestr));
					else{
						//if (debug) log += "Restriction type <"+ nextLine[4] +"> added. \n";
						RestrType cuRestrType = new RestrType(nextLine[4]);
						restrTypeList.add(cuRestrType);
						cuRestr.setRestrType(cuRestrType.attachRestriction(cuRestr));
					};

					// Подрядчик
					// Create double-sided link between objects
					index = findCompany(Long.parseLong(nextLine[6]));
					if (index >= 0){
						if (debug && !nextLine[5].equals(companyList.get(index).getName())) log += "Warning: different names for INN at ID "+ nextLine[0] +" \n";
						cuRestr.setContractor((companyList.get(index).attachAsContractor(cuRestr)));
					}
					else{
						//if (debug) log += "Company <"+ nextLine[5] +"> added. \n";
						Company curCompany = new Company(nextLine[5],Long.parseLong(nextLine[6]));
						companyList.add(curCompany);
						cuRestr.setContractor(curCompany.attachAsContractor(cuRestr));
					};
					
					// Заказчик
					// Create double-sided link between objects
					index = findCompany(Long.parseLong(nextLine[8]));
					if (index >= 0){
						if (debug && !nextLine[7].equals(companyList.get(index).getName())) log += "Warning: different names for INN at ID "+ nextLine[0] +" \n";
						cuRestr.setContractor((companyList.get(index).attachAsContractor(cuRestr)));
					}
					else{
						//if (debug) log += "Company <"+ nextLine[7] +"> added. \n";
						Company curCompany = new Company(nextLine[7],Long.parseLong(nextLine[8]));
						companyList.add(curCompany);
						cuRestr.setContractor(curCompany.attachAsContractor(cuRestr));
					};
					
					restrList.add(cuRestr);
				}
				catch (Exception e){
					if (debug) log += "Error: exception <"+ e.toString() +"> when decoding row with id "+nextLine[0]+" \n";
					continue;
				}
			}
			if (debug) log += "Scan CSV done. \n";
		}
		finally{
			if (debug) log += "Closing reader. \n";
			if (cReader != null)
				cReader.close();
			else 
				if (debug)	log += "Error: reader class is null. \n";
		}
	}
	
	
	public String getLog(){
		if (debug) return log;
		return "Logging disabled :c";
	}
	
	public ArrayList<Restriction> getRestrictionList(){
		return new ArrayList<Restriction>(restrList);
	}
	
	public ArrayList<RestrType> getRestrTypeList(){
		return new ArrayList<RestrType>(restrTypeList);
	}

	public ArrayList<District> getDistrictList(){
		return new ArrayList<District>(distrList);
	}
	
	public ArrayList<Company> getCompanyList(){
		return new ArrayList<Company>(companyList);
	}
		
	
	public int findRestrType(String restrTypeDesc){
		for (int i = restrTypeList.size()-1; i >= 0; i--)
			if (restrTypeList.get(i).getName().equals(restrTypeDesc))
				return i;
		return -1;
	}
	
	public int findDistrict(String DiscrName){
		for (int i = distrList.size()-1; i >= 0; i--)
			if (distrList.get(i).getName().equals(DiscrName))
				return i;
		return -1;
	}
	
	public int findCompany(long INN){
		for (int i = companyList.size()-1; i >= 0; i--)
			if (companyList.get(i).getINN() == INN)
				return i;
		return -1;
	}
		
	
	public Container(String filename) throws IOException {
		restrList = new ArrayList<Restriction>();
		distrList = new ArrayList<District>();
		companyList = new ArrayList<Company>();
		restrTypeList = new ArrayList<RestrType>();
		parseCSV(filename);
	}
}
