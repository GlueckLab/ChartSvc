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

/**
 *  Pojo for chart axes
 * @author Sarah Kreidler
 *
 */
public class Axis
{
	protected String label = "";
	protected int numberTicks = 5;
	protected double rangeMin = Double.NaN;
	protected double rangeMax = Double.NaN;

	/**
	 * Create a chart axis with the specified label
	 * @param label axis label
	 */
	public Axis(String label)
	{
		this.label = label;
	}

	/**
	 * Get the axis label
	 * @return label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Get the number of axis tick marks
	 * @return number of tick marks
	 */
	public int getNumberTicks()
	{
		return numberTicks;
	}

	/**
	 * Set the number of axis tick marks
	 * @param numberTicks number of tick marks
	 */
	public void setNumberTicks(int numberTicks)
	{
		this.numberTicks = numberTicks;
	}

	/**
	 * Get the minimum value in the displayed range
	 * @return minimum value in range
	 */
    public double getRangeMin()
    {
        return rangeMin;
    }

    /**
     * Set the minimum value in the displayed range 
     * @param rangeMin minimum value in range
     */
    public void setRangeMin(double rangeMin)
    {
        this.rangeMin = rangeMin;
    }

    /**
     * Get maximum value in the displayed range
     * @return max value in range
     */
    public double getRangeMax()
    {
        return rangeMax;
    }

    /**
     * Set the maximum value in the displayed range
     * @param rangeMax max value in range
     */
    public void setRangeMax(double rangeMax)
    {
        this.rangeMax = rangeMax;
    }

}
