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

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Pojo for data series objects
 * @author Sarah Kreidler
 *
 */
public class Series
{
	protected String label = null;
	protected ArrayList<Point2D.Double> data = new ArrayList<Point2D.Double>();
	
	/**
	 * Create a new data series with the specified label
	 * @param label series label
	 */
	public Series(String label)
	{
		this.label = label;
	}
	
	/**
	 * Add an x,y coordinate to the data series
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void addData(double x, double y) 
	{
		data.add(new Point2D.Double(x,y));
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
	 * Get the list of x,y coordinates for this data series
	 * @return list of points
	 */
	public ArrayList<Point2D.Double> getData()
	{
		return data;
	}
	
	
	
}
