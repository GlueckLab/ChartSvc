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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.math.plot.canvas.Plot3DCanvas;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.resource.Get;

/**
 * Class to create a JPEG image representation of the chart
 * @author Sarah Kreidler
 *
 */
public class ChartImage3DRepresentation extends OutputRepresentation
{
	protected Plot3DCanvas chart;
	
	/**
	 * Create a JPEG image representation of the chart
	 * @param chart chart object
	 * @param width width of the output JPEG
	 * @param height height of the output JPEG
	 */
	public ChartImage3DRepresentation(Plot3DCanvas chart)
	{
		super(MediaType.IMAGE_JPEG);
		this.chart = chart;
	}
	
	/**
	 * Called internally by Restlet library to write the image as the HTTP
	 * response.
	 * @param out output stream
	 */
	@Get
	public void write(OutputStream out) throws IOException
	{
		chart.setSize(300,300);

        BufferedImage image = new BufferedImage(chart.getWidth(),chart.getHeight(),	BufferedImage.TYPE_INT_RGB );
        chart.paint(image.createGraphics());
		
		ImageIO.write(image, "jpg", out);
	}
}
