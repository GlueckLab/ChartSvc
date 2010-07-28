package edu.cudenver.bios.chartsvc.domain;

import java.util.ArrayList;

public class Series
{
	protected String label = null;
	protected ArrayList<Double> data = new ArrayList<Double>();
	
	public Series(String label)
	{
		this.label = label;
	}
	
	public void addData(double value) 
	{
		data.add(value);
	}

	public String getLabel()
	{
		return label;
	}

	public ArrayList<Double> getData()
	{
		return data;
	}
	
	
	
}
