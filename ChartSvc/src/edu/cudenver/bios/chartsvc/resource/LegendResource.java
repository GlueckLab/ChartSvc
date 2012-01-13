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
import java.io.IOException;
import java.util.List;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.chartsvc.application.ChartLogger;
import edu.cudenver.bios.chartsvc.domain.Axis;
import edu.cudenver.bios.chartsvc.domain.Chart;
import edu.cudenver.bios.chartsvc.domain.Series;
import edu.cudenver.bios.chartsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.chartsvc.representation.LegendImageRepresentation;

/**
 * Creates a legend for a scatter plot as a separate image.
 * Used for plots with several data series resulting in a large
 * legend.
 * @author Sarah Kreidler
 *
 */
public class LegendResource extends ServerResource 
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;

	private static final String FORM_TAG_CHART = "chart";
    private static final String FORM_TAG_SAVE = "save";

	private Form queryParams= null;
	
	

    /**
     * Disallow GET requests
     */
    
	@Get
	public Representation represent(Variant variant) 
	{
		queryParams = getRequest().getResourceRef().getQueryAsForm();
		LegendImageRepresentation rep = null;
		try
		{
			// parse the chart parameters from the entity body
			Chart chartSpecification = ChartResourceHelper.chartFromQueryString(queryParams, false);

			// build a JFreeChart from the specs
			XYPlot renderedChart = buildScatterPlot(chartSpecification);
			// write to an image representation
			rep = new LegendImageRepresentation(renderedChart, chartSpecification.getWidth(), 
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
    
	private XYPlot buildScatterPlot(Chart chart)
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

        return plot;
	}
	
}
