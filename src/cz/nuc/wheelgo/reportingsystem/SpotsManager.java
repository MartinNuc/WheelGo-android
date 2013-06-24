package cz.nuc.wheelgo.reportingsystem;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.datasource.DataFromService;
import cz.nuc.wheelgo.datasource.DataSource;

import java.util.List;

public class SpotsManager {

	// singleton
	private static SpotsManager instance;

	private DataSource ds;

	/************** CONSTRUCTORS ***************/
	public SpotsManager(Context context)
	{
        this();
	}
	
	public SpotsManager()
	{
		ds = new DataFromService();
	}
	
	/************** getrs for specific spots ***************/
	public Problem getDetailProblem(int id)
	{
		return ds.getProblemDetail(id);
	}
	
	public Place getDetailPlace(int id)
	{
		return ds.getPlaceDetail(id);
	}
	
	public Tip getDetailTip(int id)
	{
        return ds.getTipDetail(id);
	}
	
	/************** SINGLETON ***************/
	/**
	 * Singleton
	 * @return
	 */
	public static SpotsManager getInstance(){
		if (instance == null)
		{
			instance = new SpotsManager();
		}
		
		return instance;
	}
	
	/**
	 * Singleton
	 * @return
	 */	
	public static SpotsManager getInstance(Context context){
		if (instance == null)
		{
			instance = new SpotsManager(context);
		}
		
		return instance;
	}
	
	/************** REPORTING ***************/
	public void reportProblem(Problem newOne)
	{
		ds.addProblem(newOne);
	}

	public void reportPlace(Place newOne) {
		ds.addPlace(newOne);		
	}

	public void reportTip(Tip newOne) {
		ds.addTip(newOne);		
	}

	/************** Getrs for all spots ***************/
	
	/**
	 * Downloads image from datasource
	 * @param id ID of the spot we want the image
	 * @return
	 */
	public Bitmap getImage(int id)
	{
		return ds.getImage(id);
	}

	/**
	 * Reloads data
	 */
	public List<Spot> load(LatLng location, int radius)
	{
        Location loc = new Location();
        loc.setLatitude(location.latitude);
        loc.setLongitude(location.longitude);
		return ds.getReportsAround(loc, radius);
	}

	/**
	 * Deletes specific spot from datasource
	 */
	public void deleteSpot(int id_external)
	{
		ds.deleteSpot(id_external);
	}

	public void editPlace(Place edittedOne) {
		ds.editPlace(edittedOne);
	}
	public void editProblem(Problem edittedOne) {
		ds.editProblem(edittedOne);
	}
	public void editTip(Tip edittedOne) {
		ds.editTip(edittedOne);
	}
}
