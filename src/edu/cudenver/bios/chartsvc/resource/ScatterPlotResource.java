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
package edu.cudenver.bios.chartsvc.resource;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.cudenver.bios.chartsvc.application.ChartLogger;
import edu.cudenver.bios.chartsvc.domain.Axis;
import edu.cudenver.bios.chartsvc.domain.Chart;
import edu.cudenver.bios.chartsvc.domain.Series;
import edu.cudenver.bios.chartsvc.representation.ChartImage3DRepresentation;
import edu.cudenver.bios.chartsvc.representation.ChartImageRepresentation;
import edu.cudenver.bios.chartsvc.representation.ErrorXMLRepresentation;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.ColumnArrangement;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.math.plot.canvas.Plot3DCanvas;

/**
 * Resource which responds to requests for scatter plots
 * @author Sarah Kreidler
 *
 */
public class ScatterPlotResource extends Resource
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;

	private static final String FORM_TAG_CHART = "chart";
	private static final String FORM_TAG_SAVE = "save";

	private Form queryParams= null;

	/**
	 * Create a scatter plot 
	 * @param context restlet context
	 * @param request HTTP request object
	 * @param response HTTP response object
	 */
	public ScatterPlotResource(Context context, Request request, Response response) 
	{
		super(context, request, response);
		queryParams = request.getResourceRef().getQueryAsForm();
		// This representation has only one type of representation.
		getVariants().add(new Variant(MediaType.IMAGE_JPEG));
	}

	/**
	 * Disallow GET requests
	 */
	@Override
	public boolean allowGet()
	{
		return true;
	}

	/**
	 * Disallow PUT requests
	 */
	@Override
	public boolean allowPut()
	{
		return false;
	}

	/**
	 * Allow POST requests to create a power list
	 */
	@Override
	public boolean allowPost() 
	{
		return  true;
	}


	/**
	 * Returns a full representation for a given variant.
	 */
	@Override
	public Representation represent(Variant variant) 
	{
		ChartImageRepresentation rep = null;
		try
		{
			// parse the chart parameters from the entity body
			Chart chartSpecification = ChartResourceHelper.chartFromQueryString(queryParams, false);

			// build a JFreeChart from the specs
			JFreeChart renderedChart = buildScatterPlot(chartSpecification);
			// write to an image representation
			rep = new ChartImageRepresentation(renderedChart, chartSpecification.getWidth(), 
					chartSpecification.getHeight());

			// Add file save headers if requested
			String saveStr = queryParams.getFirstValue(FORM_TAG_SAVE);
			boolean save = Boolean.parseBoolean(saveStr);
			if (save)
			{
				Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");  
				if (responseHeaders == null)  
				{  
					responseHeaders = new Form();  
					getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);  
				}  
				responseHeaders.add("Content-type", "application/force-download");
				responseHeaders.add("Content-disposition", "attachment; filename=chart.jpg");
			}

			getResponse().setEntity(rep); 
			getResponse().setStatus(Status.SUCCESS_CREATED);
		}
		catch (IllegalArgumentException iae)
		{
			ChartLogger.getInstance().error(iae.getMessage());
			try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
			catch (IOException e) {}
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
		catch (ResourceException re)
		{
			ChartLogger.getInstance().error(re.getMessage());
			try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
			catch (IOException e) {}
			getResponse().setStatus(re.getStatus());
		}

		return rep;
	}

	/**
	 * Create a JFreeChart object from the chart specification.  JFreechart provides 
	 * functionality to render 2D scatter plots as jpeg images
	 * 
	 * @param chart chart specification object
	 * @return JFreeChart object
	 * @throws ResourceException
	 */
	private JFreeChart buildScatterPlot(Chart chart)
	throws ResourceException
	{
		// the first series is treated as the x values
		if (chart.getSeries() == null || chart.getSeries().size() <= 0)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No data series specified");

		// create the jfree chart series
		XYSeriesCollection chartData = new XYSeriesCollection();
		// use a spline renderer to make the connecting lines smooth
		XYSplineRenderer rend = new XYSplineRenderer();

		int seriesIdx = 0;
		for(Series series: chart.getSeries())
		{    		
			XYSeries xySeries = new XYSeries(series.getLabel());
			
			List<Double> xList = series.getXCoordinates();
			List<Double> yList = series.getYCoordinates();
			if (xList != null && yList != null && xList.size() == yList.size())
			{
				for(int i = 0; i < xList.size(); i++)
				{
					xySeries.add(xList.get(i), yList.get(i));
				}
			}

			// set the line style
			rend.setSeriesPaint(seriesIdx, Color.BLACK);
			if (seriesIdx > 0)
			{
				rend.setSeriesStroke(seriesIdx, 
						new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
								1.0f, new float[] {(float) seriesIdx, (float) 2*seriesIdx}, 0.0f));
			}
			// add the series to the data set
			chartData.addSeries(xySeries);
			seriesIdx++;
		}

		// turn off shapes displayed at each data point to make a smooth curve
		rend.setBaseShapesVisible(false);

		// Create the line chart
		NumberAxis xAxis = new NumberAxis();
		xAxis.setAutoRangeIncludesZero(false);
		if (chart.getXAxis() != null)
		{
			Axis xAxisSpec = chart.getXAxis();
			xAxis.setLabel(xAxisSpec.getLabel());
			if (!Double.isNaN(xAxisSpec.getRangeMin()) &&
					!Double.isNaN(xAxisSpec.getRangeMax()))
			{
				xAxis.setRange(xAxisSpec.getRangeMin(), xAxisSpec.getRangeMax());
			}
		}
		NumberAxis yAxis = new NumberAxis();
		if (chart.getYAxis() != null)
		{
			Axis yAxisSpec = chart.getYAxis();
			yAxis.setLabel(chart.getYAxis().getLabel());
			if (!Double.isNaN(yAxisSpec.getRangeMin()) &&
					!Double.isNaN(yAxisSpec.getRangeMax()))
			{
				xAxis.setRange(yAxisSpec.getRangeMin(), yAxisSpec.getRangeMax());
			}
		}
		XYPlot plot = new XYPlot((XYDataset) chartData, xAxis, 
				yAxis, rend);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);		
		
		JFreeChart renderedChart = new JFreeChart(chart.getTitle(), 
				JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		renderedChart.setBackgroundPaint(Color.WHITE);
		if (chart.hasLegend())
		{
			LegendTitle legend = new LegendTitle(plot, new ColumnArrangement(), new ColumnArrangement());
			legend.setFrame(BlockBorder.NONE);
			legend.setPosition(RectangleEdge.BOTTOM);
			renderedChart.addLegend(legend);
		}
		
		return renderedChart;
	}
}
