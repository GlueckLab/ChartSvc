package edu.cudenver.bios.chartsvc.resource;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.DomRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

import edu.cudenver.bios.chartsvc.application.ChartConstants;
import edu.cudenver.bios.chartsvc.application.ChartLogger;
import edu.cudenver.bios.chartsvc.domain.Chart;
import edu.cudenver.bios.chartsvc.domain.Series;
import edu.cudenver.bios.chartsvc.representation.ChartImageRepresentation;
import edu.cudenver.bios.chartsvc.representation.ErrorXMLRepresentation;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlotResource extends Resource
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;

	/**
	 * Create a scatter plot 
	 * @param context restlet context
	 * @param request HTTP request object
	 * @param response HTTP response object
	 */
    public ScatterPlotResource(Context context, Request request, Response response) 
    {
        super(context, request, response);

        // This representation has only one type of representation.
        getVariants().add(new Variant(MediaType.IMAGE_JPEG));
    }

    /**
     * Disallow GET requests
     */
    @Override
    public boolean allowGet()
    {
        return false;
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
     * Process a POST request to perform a set of power
     * calculations.  Please see REST API documentation for details on
     * the entity body format.
     * 
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    {
        DomRepresentation rep = new DomRepresentation(entity);

        try
        {
            // parse the chart parameters from the entity body
            Chart chartSpecification = ChartResourceHelper.chartFromDomNode(rep.getDocument().getDocumentElement());

            // build a JFreeChart from the specs
            JFreeChart renderedChart = buildScatterPlot(chartSpecification);
            ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Owner\\Desktop\\foo.jpeg"), renderedChart, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            // write to an image representation
            ChartImageRepresentation response = 
            	new ChartImageRepresentation(renderedChart, DEFAULT_WIDTH, DEFAULT_HEIGHT);


            // TODO: remove
            Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");  
            if (responseHeaders == null)  
            {  
            	responseHeaders = new Form();  
            	getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);  
            }  
            responseHeaders.add("Content-type", "application/force-download");
            responseHeaders.add("Content-disposition", "attachment; filename=chart.jpg");
            
            
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);
        }
        catch (IOException ioe)
        {
            ChartLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
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
    }
    
    private JFreeChart buildScatterPlot(Chart chart)
    throws ResourceException
    {
    	// the first series is treated as the x values
    	Series xSeries = chart.getSeries().get(0);
    	if (xSeries == null)
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No data series specified");
    	ArrayList<Double> xData = xSeries.getData();
    	
    	// create the jfree chart series
    	XYSeriesCollection chartData = new XYSeriesCollection();
        // use a spline renderer to make the connecting lines smooth
        XYSplineRenderer rend = new XYSplineRenderer();
        
        int seriesIdx = 0;
    	for(Series series: chart.getSeries())
    	{
    		if (series == xSeries) continue;
    		
    		XYSeries xySeries = new XYSeries(series.getLabel());
    		int dataIdx = 0;
    		for(Double data: series.getData())
    		{
    			xySeries.add(xData.get(dataIdx), data);
    			dataIdx++;
    		}
    		
    		// set the line style
            rend.setSeriesPaint(seriesIdx, Color.BLACK);
            rend.setSeriesStroke(seriesIdx, 
            		new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
            				1.0f, new float[] {(float) seriesIdx, 6.0f}, 0.0f));
    		
            // add the series to the data set
    		chartData.addSeries(xySeries);
    		seriesIdx++;
    	}

        // turn off shapes displayed at each data point to make a smooth curve
        rend.setBaseShapesVisible(false);

        // Create the line chart
        String xLabel = "";
        String yLabel = "";
        if (chart.getXAxis() != null) xLabel = chart.getXAxis().getLabel();
        if (chart.getYAxis() != null) yLabel = chart.getYAxis().getLabel();

        XYPlot plot = new XYPlot((XYDataset) chartData, new NumberAxis(xLabel), 
                new NumberAxis(yLabel), rend);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
       
        JFreeChart renderedChart = new JFreeChart(chart.getTitle(), 
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        renderedChart.setBackgroundPaint(Color.WHITE);
        
        return renderedChart;
    }
}
