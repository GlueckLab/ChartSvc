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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.restlet.data.MediaType;
import org.restlet.resource.OutputRepresentation;
import org.jfree.chart.block.ColumnArrangement;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
/**
 * Class to create a JPEG image representation of the chart legend
 * @author Sarah Kreidler
 *
 */
public class LegendImageRepresentation extends OutputRepresentation
{
	//protected JFreeChart plot;
	protected XYPlot plot;
	protected int width, height;
	
	/**
	 * Create a JPEG image representation of the chart legend
	 * @param chart chart object
	 * @param width width of the output JPEG
	 * @param height height of the output JPEG
	 */
	public LegendImageRepresentation(XYPlot plot, int width, int height)
	{
		super(MediaType.IMAGE_JPEG);
		this.plot = plot;
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * Called internally by Restlet library to write the image as the HTTP
	 * response.
	 * @param out output stream
	 */
	@Override
	public void write(OutputStream out) throws IOException
	{
		// build the legend from the plot, and write it to a jpeg image
		if (plot != null)
		{
			LegendTitle legend = new LegendTitle(plot, new ColumnArrangement(), new ColumnArrangement());
			legend.setMargin(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
			legend.setBackgroundPaint(Color.white);
			legend.setPosition(RectangleEdge.BOTTOM);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = image.createGraphics();
			Rectangle2D.Double legendArea = new Rectangle2D.Double(0, 0, width, height);
			g2.clip(legendArea);
			legend.arrange(g2, new RectangleConstraint(width, height));
			legend.draw(g2, legendArea);
			g2.dispose();
			EncoderUtil.writeBufferedImage(image, ImageFormat.JPEG, out);
		}
	}

}
