package ifmo.opendata.items;

import java.util.Date;

public class Restriction {
	private int code;
	private String disposalNum;
	private District district;
	private String address;
	private RestrType restrType;
	private Company consumer;
	private Company contractor;
	private Date dateStart;
	private Date dateEnd;
		
	public boolean checkConsistency(){
		// not complete
		return true;
	}
			
	public void setDistrict(District trgtDistrict){
		district = trgtDistrict;
	}
	public void setRestrType(RestrType trgtRestrType){
		restrType = trgtRestrType;
	}
	public void setConsumer(Company trgtConsumer){
		consumer = trgtConsumer;
	}
	public void setContractor(Company trgtContractor){
		contractor = trgtContractor;
	}
	
	public int getCode() {
		return code;
	}

	public String getDisposalNum() {
		return disposalNum;
	}

	public District getDistrict() {
		return district;
	}

	public String getAddress() {
		return address;
	}

	public RestrType getRestrType() {
		return restrType;
	}

	public Company getConsumer() {
		return consumer;
	}

	public Company getContractor() {
		return contractor;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}
	
	public Restriction(int code, String disposalNum, String address, Date dateStart, Date dateEnd) {
		district = null;
		restrType = null;
		consumer = null;
		contractor = null;
		
		this.code = code;
		this.disposalNum = disposalNum;
		this.address = address;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;		
	}
}
