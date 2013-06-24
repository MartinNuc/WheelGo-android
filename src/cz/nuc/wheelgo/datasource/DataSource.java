package cz.nuc.wheelgo.datasource;

import android.graphics.Bitmap;
import cz.nuc.wheelgo.reportingsystem.*;

import java.util.List;

public interface DataSource {
	public List<Spot> getReportsAround(Location location, int radius);
	public void addPlace(Place newOne);
	public void addProblem(Problem newOne);
	public void addTip(Tip newOne);
	public Bitmap getImage(int id);
	public void deleteSpot(int id);
	public void editProblem(Problem edittedOne);
	public void editTip(Tip edittedOne);
	public void editPlace(Place edittedOne);
    public Problem getProblemDetail(int id);
    public Place getPlaceDetail(int id);
    public Tip getTipDetail(int id);
    public List<Node> navigate(Location from, Location to);
}
