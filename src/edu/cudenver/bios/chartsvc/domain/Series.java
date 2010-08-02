package edu.cudenver.bios.chartsvc.domain;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Series
{
	protected String label = null;
	protected ArrayList<Point2D.Double> data = new ArrayList<Point2D.Double>();
	
	public Series(String label)
	{
		this.label = label;
	}
	
	public void addData(double x, double y) 
	{
		data.add(new Point2D.Double(x,y));
	}

	public String getLabel()
	{
		return label;
	}

	public ArrayList<Point2D.Double> getData()
	{
		return data;
	}
	
	
	
}
