package edu.cudenver.bios.chartsvc.domain;

import java.util.ArrayList;

public class Chart
{
	protected String title = "";
	protected boolean legend = false;
	protected Axis XAxis = null;
	protected Axis YAxis = null;
	protected ArrayList<Series> seriesList = new ArrayList<Series>();
	
	public Chart()
	{
		
	}

	public Axis getXAxis()
	{
		return XAxis;
	}

	public void setXAxis(Axis xAxis)
	{
		XAxis = xAxis;
	}

	public Axis getYAxis()
	{
		return YAxis;
	}

	public void setYAxis(Axis yAxis)
	{
		YAxis = yAxis;
	}

	public ArrayList<Series> getSeries()
	{
		return seriesList;
	}
	
	public void addSeries(Series series)
	{
		seriesList.add(series);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

    public boolean hasLegend()
    {
        return legend;
    }

    public void setLegend(boolean legend)
    {
        this.legend = legend;
    }
	
	
}
