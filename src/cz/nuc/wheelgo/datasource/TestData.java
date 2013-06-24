package cz.nuc.wheelgo.datasource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.reportingsystem.*;
import cz.nuc.wheelgo.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TestData implements DataSource {
	private List<CommonPlace> all;
	
	public TestData()
	{
	
	}
	
	public TestData(Context context)
	{
		all = new ArrayList<CommonPlace>();

		CommonPlace cp;

		cp = new CommonPlace();
		cp.describtion="Manes - tahle restaurace ma skvele schody. Da se tu i dobre a levne najist. Doporucuji vsem labuznikum. Ackoliv je tohle dlouhy popis, doufam, ze si ho uzijete!!";
		cp.kind=Type.PLACE;
		cp.placeKind=2;
		cp.id_out=20;
		cp.id=all.size();
		cp.coordinates=new LatLng(50.0774, 14.4142);
		all.add(cp);
		
		cp = new CommonPlace();
		cp.describtion="Zbraslavsky problem :-)";
		cp.kind=Type.TIP;
		cp.id_out=2;
		cp.id=all.size();
		cp.coordinates=new LatLng(49.9681, 14.3859);
		all.add(cp);
		
		cp = new CommonPlace();
		cp.describtion="Zde se da sjet z chodniku";
		cp.kind=Type.TIP;
		cp.id_out=2;
		cp.id=all.size();
		cp.coordinates=new LatLng(50.076189, 14.416803);
		all.add(cp);

		cp = new CommonPlace();
		cp.describtion="Zde se neda nikde sjet. Tudy ne!";
		cp.kind=Type.PROBLEM;
		cp.id_out=3;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.076606, 14.415969);
		all.add(cp);

		cp = new CommonPlace();
		cp.describtion="Trubky na silnici";
		cp.kind=Type.PROBLEM;
		cp.id_out=7;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.076611, 14.415361);
		all.add(cp);

		cp = new CommonPlace();
		cp.describtion="Trubky na silnici";
		cp.kind=Type.PROBLEM;
		cp.id_out=8;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.076567 * 1E6, 14.415392);
		all.add(cp);

		cp = new CommonPlace();
		cp.describtion="Trubky na silnici";
		cp.kind=Type.PROBLEM;
		cp.id_out=9;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.076606, 14.415228);
		all.add(cp);

		cp = new CommonPlace();
		cp.describtion="Trubky na silnici";
		cp.kind=Type.PROBLEM;
		cp.id_out=10;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.077133, 14.415075);
		all.add(cp);
		
		cp = new CommonPlace();
		cp.describtion="Pohodlne prechody";
		cp.kind=Type.TIP;
		cp.id_out=11;
		cp.id=all.size();
		cp.coordinates=new LatLng(50.077283, 14.415169);
		all.add(cp);
			
		cp = new CommonPlace();
		cp.describtion="Schody";
		cp.kind=Type.PROBLEM;
		cp.id_out=13;
		cp.id=all.size();
		cp.duration=new Date(0,0,0);
		cp.coordinates=new LatLng(50.0771, 14.4170);
		all.add(cp);
		
		cp = new CommonPlace();
		cp.describtion="Bez problemu projeto";
		cp.kind=Type.TIP;
		cp.id_out=14;
		cp.id=all.size();
		cp.coordinates=new LatLng(50.077550,14.419042);
		all.add(cp);
	
		cp = new CommonPlace();
		cp.describtion="Bez pomoci to zde nejde. Najezd rozkopany.";
		cp.kind=Type.PROBLEM;
		cp.id_out=18;
		cp.id=all.size();
		cp.duration=new Date();
		cp.duration.setTime(cp.duration.getTime() + 12*1000*3600);
		cp.coordinates=new LatLng(50.077656, 14.419469);
		all.add(cp);		
	}
	
	/**
	 * Supposed to fill up database if clean
	 * 
	 * *Not implemented yet*
	 */
	public void initData()
	{
		
	}
	
	public List<Place> getPlaces()
	{
		List<Place> ret = new ArrayList<Place>();
		for (CommonPlace one : all)
			if (one.kind == Type.PLACE)
			{
				Place newOne = new Place(one.id);
				newOne.setCoordinates(one.coordinates);
				newOne.setName(one.name);
				newOne.setDescribtion(one.describtion);
				ret.add(newOne);	
			}
		
		return ret;
	}
	
	public List<Problem> getProblems()
	{
		List<Problem> ret = new ArrayList<Problem>();
		for (CommonPlace one : all)
			if (one.kind == Type.PROBLEM)
			{
				Problem newOne = new Problem(one.id);
				newOne.setCoordinates(one.coordinates);
				newOne.setName(one.name);
				newOne.setDescribtion(one.describtion);
				newOne.setDuration(one.duration);
				ret.add(newOne);
			}
		
		return ret;
	}
	
	public List<Tip> getTips()
	{
		List<Tip> ret = new ArrayList<Tip>();
		for (CommonPlace one : all)
			if (one.kind == Type.TIP)
			{
				Tip newOne = new Tip(one.id);
				newOne.setCoordinates(one.coordinates);
				newOne.setName(one.name);
				newOne.setDescribtion(one.describtion);
				ret.add(newOne);
			}
		
		return ret;
	}


    @Override
    public List<Spot> getReportsAround(Location location, int radius) {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    public void addPlace(Place newOne)
	{
		CommonPlace cp = new CommonPlace();
		cp.coordinates=newOne.getCoordinates();
		cp.name=newOne.getName();
		cp.describtion=newOne.getDescription();
		cp.kind=Type.PLACE;
		cp.id=all.size();
		Random rand = new  Random();
		cp.id_out=35+Math.abs(rand.nextInt()%255);
		saveImageToSdCard(newOne.getPhoto(), cp.id_out);

		all.add(cp);
	}
	public void addProblem(Problem newOne)
	{
		CommonPlace cp = new CommonPlace();
		cp.coordinates=newOne.getCoordinates();
		cp.name=newOne.getName();
		cp.describtion=newOne.getDescription();
		cp.kind=Type.PROBLEM;
		cp.duration=newOne.getDuration();
		cp.id=all.size();
		Random rand = new  Random();
		cp.id_out=35+Math.abs(35+rand.nextInt()%255);
		saveImageToSdCard(newOne.getPhoto(), cp.id_out);

		all.add(cp);
	}
	
	public void addTip(Tip newOne)
	{
		CommonPlace cp = new CommonPlace();
		cp.coordinates=newOne.getCoordinates();
		cp.name=newOne.getName();
		cp.describtion=newOne.getDescription();
		cp.kind=Type.TIP;
		cp.id=all.size();
		Random rand = new  Random();
		cp.id_out=35+Math.abs(35+rand.nextInt()%255);
		saveImageToSdCard(newOne.getPhoto(), cp.id_out);
		all.add(cp);
	}
	
	private void saveImageToSdCard(Bitmap image, int extid)
	{
		if (image==null)
			return;
		File filename = new File("/sdcard/data/data/cz.nuc.vozik/" + "photo" + extid + ".jpg");
		FileOutputStream out;
		try {
		out = new FileOutputStream(filename);
		image.compress(Bitmap.CompressFormat.JPEG, 80, out);
		out.flush();
		out.close();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		out=null;
		}
	}
	
	class CommonPlace
	{
		public LatLng coordinates;
		public String describtion;
		public Type kind;
		public int placeKind;
		public String name;
		public int id;
		public Bitmap image;
		public Date duration;
		public int id_out;
		
		CommonPlace()
		{
		}
	}

	@Override
	public Bitmap getImage(int id) {
		return BitmapFactory.decodeFile("/sdcard/data/data/cz.nuc.vozik/photo" + id +".jpg");
	}

	@Override
	public void deleteSpot(int id) {
		// delete item itself
		for (CommonPlace one : all)
			if (one.id_out == id)
			{
				all.remove(one);
				return;
			}
		
		// delete photo
		
	}

	@Override
	public void editPlace(Place edittedOne) {
		for (CommonPlace one : all)
			if (one.id == edittedOne.getId())
			{
				one.describtion=edittedOne.getDescription();
				saveImageToSdCard(edittedOne.getPhoto(), one.id_out);
				return;
			}
	}

    @Override
    public Problem getProblemDetail(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlaceDetail(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tip getTipDetail(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Node> navigate(Location from, Location to) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void editProblem(Problem edittedOne) {
		for (CommonPlace one : all)
			if (one.id == edittedOne.getId())
			{
				one.describtion=edittedOne.getDescription();
				one.kind=Type.PROBLEM;
				one.duration=edittedOne.getDuration();
				saveImageToSdCard(edittedOne.getPhoto(), one.id_out);
				return;
			}
	}
	
	@Override
	public void editTip(Tip edittedOne) {
		for (CommonPlace one : all)
			if (one.id == edittedOne.getId())
			{
				one.describtion=edittedOne.getDescription();
				one.kind=Type.TIP;
				saveImageToSdCard(edittedOne.getPhoto(), one.id_out);
				return;
			}
	}


}
