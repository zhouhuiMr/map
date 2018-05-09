package per.map.serviceimpl;

import java.util.ArrayList;

public class buildingObject {
	private int ID = 0;
	private String BUILDINGNAME = "";
	private String BUILDINGTYPE = "";
	private ArrayList<coordinateObject> list = new ArrayList<>();
	private String TELEPHONE = "";
	private String ADDRESS = "";
	private String PIC = "";
	private String TYPENAME = "";

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getBUILDINGNAME() {
		return BUILDINGNAME;
	}

	public void setBUILDINGNAME(String bUILDINGNAME) {
		BUILDINGNAME = bUILDINGNAME;
	}

	public String getBUILDINGTYPE() {
		return BUILDINGTYPE;
	}

	public void setBUILDINGTYPE(String bUILDINGTYPE) {
		BUILDINGTYPE = bUILDINGTYPE;
	}

	public ArrayList<coordinateObject> getList() {
		return list;
	}

	public void setList(ArrayList<coordinateObject> list) {
		this.list = list;
	}

	public String getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getPIC() {
		return PIC;
	}

	public void setPIC(String pIC) {
		PIC = pIC;
	}

	public String getTYPENAME() {
		return TYPENAME;
	}

	public void setTYPENAME(String tYPENAME) {
		TYPENAME = tYPENAME;
	}
}
