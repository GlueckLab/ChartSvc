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
import java.util.List;

/**
 * Pojo for data series objects
 * @author Sarah Kreidler
 *
 */
public class Series
{
	protected String label = null;
	protected ArrayList<Double> xList = new ArrayList<Double>();
	protected ArrayList<Double> yList = new ArrayList<Double>();
	protected ArrayList<Double> zList = new ArrayList<Double>();
	
	/**
	 * Create a new data series with the specified label
	 * @param label series label
	 */
	public Series(String label)
	{
		this.label = label;
	}
	
	/**
	 * Add an x value to the series
	 */
	public void addX(double x)
	{
		xList.add(x);
	}
	
	/**
	 * Add a y value to the series
	 */
	public void addY(double y)
	{
		yList.add(y);
	}
	
	/**
	 * Add a z value to the series
	 */
	public void addZ(double z)
	{
		zList.add(z);
	}

	/**
	 * Get the label of the data series
	 * @return data series label
	 */
	public String getLabel()
	{
		return label;
	}
	
	
	/**
	 * Set the label for this series
	 * @param label series label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Get the list of X coordinates
	 * @return X coordinates
	 */
	public List<Double> getXCoordinates()
	{
		return xList;
	}
	
	/**
	 * Get the list of Y coordinates
	 * @return Y coordinates
	 */
	public List<Double> getYCoordinates()
	{
		return yList;
	}
	
	/**
	 * Get the list of Z coordinates
	 * @return Z coordinates
	 */
	public List<Double> getZCoordinates()
	{
		return zList;
	}
	
}
