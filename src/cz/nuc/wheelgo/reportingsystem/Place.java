package cz.nuc.wheelgo.reportingsystem;



public class Place extends Spot {
	protected int accessibility;
	public int getAccessibility() {
		return accessibility;
	}
	public void setAccesibility(int pristupnost) {
		this.accessibility= pristupnost;
	}

	public Place() {
		super();
        this.type = SpotType.PLACE;
		this.accessibility=0;  // default accessibility
	}
	
	public Place(int id) {
		super(id);
        this.type = SpotType.PLACE;
		this.accessibility=0;  // default accessibility
	}
}
