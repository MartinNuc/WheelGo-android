package cz.nuc.wheelgo.reportingsystem;

import java.util.Date;

public class Problem extends Spot {

	protected Date duration;
	public Date getDuration() {
		return duration;
	}
	public void setDuration(Date lasts) {
		this.duration = lasts;
	}


	public Problem() {
		super();
        this.type = SpotType.PROBLEM;
		this.duration=new Date(0,0,5); // default duration
	}
	public Problem(int id) {
		super(id);
        this.type = SpotType.PROBLEM;
		this.duration=new Date(0,0,5); // default duration
	}

    public void setDuration(long expiration) {
        this.duration = new Date(expiration);
    }
}
