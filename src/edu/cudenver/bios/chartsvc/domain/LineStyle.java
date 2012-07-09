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
 * Line Style Object
 * @author VIJAY AKULA
 *
 */

public class LineStyle 
{

    protected String lineStyleLabel = null;
    protected double width = 1.0f;
    protected double dashLength = 1.0f;
    protected double spaceLength = 10.0f;
    
    /*protected ArrayList<Double> width = new ArrayList<Double>();
    protected ArrayList<Double> dashLength = new ArrayList<Double>();
    protected ArrayList<Double> spaceLength = new ArrayList<Double>();*/
    
        
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    public void setDashLength(double dashLength)
    {
        this.dashLength = dashLength;
    }
    
    public void setSpaceLength(double spaceLength)
    {
        this.spaceLength = spaceLength;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getDashLength()
    {
        return dashLength;
    }
    
    public double getSpaceLength()
    {
        return spaceLength;
    }
    
    /*public void addLineWidth(double lineWidth)
    {
        width.add(lineWidth);
    }
    
    public void addDashLength(double dashLengthSize)
    {
        dashLength.add(dashLengthSize);
    }
    
    public void addSpaceLength(double spaceLengthSize)
    {
        spaceLength.add(spaceLengthSize);
    }
    
    public List<Double> getLineWidth()
    {
        return width;
    }
    
    public List<Double> getLineDashLength()
    {
        return dashLength;
    }
    
    public List<Double> getLineSpaceLength()
    {
        return spaceLength;
    }*/
}
