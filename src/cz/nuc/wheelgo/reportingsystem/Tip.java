package cz.nuc.wheelgo.reportingsystem;


public class Tip extends Spot {

	public Tip() {
        super();
        this.type = SpotType.TIP;
	}
	
	public Tip(int id)
	{
		super(id);
        this.type = SpotType.TIP;
	}

}
