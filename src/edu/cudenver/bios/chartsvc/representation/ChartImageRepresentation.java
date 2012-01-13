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
package edu.cudenver.bios.chartsvc.representation;

import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.resource.Get;

/**
 * Class to create a JPEG image representation of the chart
 * @author Sarah Kreidler
 *
 */
public class ChartImageRepresentation extends OutputRepresentation
{
	protected JFreeChart chart;
	protected int width, height;
	
	/**
	 * Create a JPEG image representation of the chart
	 * @param chart chart object
	 * @param width width of the output JPEG
	 * @param height height of the output JPEG
	 */
	public ChartImageRepresentation(JFreeChart chart, int width, int height)
	{
		super(MediaType.IMAGE_JPEG);
		this.chart = chart;
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * Called internally by Restlet library to write the image as the HTTP
	 * response.
	 * @param out output stream
	 */
	@Get
	public void write(OutputStream out) throws IOException
	{
		ChartUtilities.writeChartAsJPEG(out, chart, width, height);
	}

}
