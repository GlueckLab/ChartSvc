/*
 * Chart Service for the GLIMMPSE Software System.  Creates
 * publishable quality scatter plots.
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.cudenver.bios.chartsvc.domain;

import java.util.ArrayList;

/**
 * Pojo for a chart object
 * @author Sarah Kreidler
 *
 */
public class Chart
{
	protected String title = "";
	protected boolean legend = false;
	protected Axis XAxis = null;
	protected Axis YAxis = null;
	protected Axis ZAxis = null;
	protected ArrayList<Series> seriesList = new ArrayList<Series>();
	
	/**
	 * Create an empty chart description
	 */
	public Chart()
	{
		
	}

	/**
	 * Get the horizontal axis
	 * @return horizontal axis object
	 */
	public Axis getXAxis()
	{
		return XAxis;
	}

	/**
	 * Set the X axis for this chart
	 * @param xAxis axis object
	 */
	public void setXAxis(Axis xAxis)
	{
		XAxis = xAxis;
	}

	/**
	 * Get the vertical axis
	 * @return vertical axis object
	 */
	public Axis getYAxis()
	{
		return YAxis;
	}

	/**
	 * Set the Y axis for this chart
	 * @param yAxis axis object
	 */
	public void setYAxis(Axis yAxis)
	{
		YAxis = yAxis;
	}

	/**
	 * Get the horizontal axis
	 * @return horizontal axis object
	 */
	public Axis getZAxis()
	{
		return ZAxis;
	}

	/**
	 * Set the X axis for this chart
	 * @param xAxis axis object
	 */
	public void setZAxis(Axis zAxis)
	{
		ZAxis = zAxis;
	}
	
	
	
	/**
	 * Get the list of data series for this chart
	 * @return list of data series
	 */
	public ArrayList<Series> getSeries()
	{
		return seriesList;
	}
	
	/**
	 * Add a data series to the chart
	 * @param series data series object
	 */
	public void addSeries(Series series)
	{
		seriesList.add(series);
	}

	/**
	 * Get the title of the chart
	 * @return chart title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Set the title of the chart
	 * @param title chart title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Indicates if the chart has a visible legend
	 * @return true if chart has a legend
	 */
    public boolean hasLegend()
    {
        return legend;
    }

    /**
     * Enable/disable the chart legend
     * @param legend if true, enables the legend
     */
    public void setLegend(boolean legend)
    {
        this.legend = legend;
    }
	
	
}
