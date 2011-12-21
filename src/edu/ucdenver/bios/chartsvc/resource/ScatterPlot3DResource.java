package edu.cudenver.bios.chartsvc.resource;

import java.awt.Color;
import java.util.List;

import org.math.plot.canvas.Plot3DCanvas;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import edu.cudenver.bios.chartsvc.domain.Chart;
import edu.cudenver.bios.chartsvc.domain.Series;
import edu.cudenver.bios.chartsvc.representation.ChartImage3DRepresentation;
import edu.cudenver.bios.chartsvc.representation.ErrorXMLRepresentation;

public class ScatterPlot3DResource extends Resource
{
    private Form queryParams= null;
	/**
	 * Create a 3d scatter plot 
	 * @param context restlet context
	 * @param request HTTP request object
	 * @param response HTTP response object
	 */
    public ScatterPlot3DResource(Context context, Request request, Response response) 
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
        return  false;
    }
    
    /**
     * Returns a full representation for a given variant.
     */
    @Override
    public Representation represent(Variant variant) 
    {
    	Representation rep = null;
    	try
    	{
    		Chart chart = ChartResourceHelper.chartFromQueryString(queryParams, true);
    		Plot3DCanvas plot = createPlot(chart);
    		rep = new ChartImage3DRepresentation(plot);
    	}
    	catch (ResourceException re)
    	{
    		getResponse().setStatus(re.getStatus());
    		try { rep = new ErrorXMLRepresentation(re.getMessage()); } catch (Exception e) {}
    	}
    	return rep;
    }

    /**
     * 
     * @param chart
     * @return
     */
	private Plot3DCanvas createPlot(Chart chart)
	throws ResourceException
	{
        Plot3DCanvas plot = new Plot3DCanvas();
        
        for(Series series: chart.getSeries())
        {
        	List<Double> xList = series.getXCoordinates();
        	List<Double> yList = series.getXCoordinates();
        	List<Double> zList = series.getXCoordinates();
        	if (xList != null && yList != null && zList != null &&
        			xList.size() == yList.size() && zList.size() == xList.size() * yList.size())
        	{
        		// dump the lists into arrays
        		double[] x = new double[xList.size()];
        		for(int i = 0; i < xList.size(); i++) x[i] = xList.get(i).doubleValue();
        		
        		double[] y = new double[yList.size()];
        		for(int i = 0; i < yList.size(); i++) y[i] = yList.get(i).doubleValue();
        		
        		double[][] zGrid = buildZGrid(x,y,zList);
        		
        		// add grid plot to the PlotPanel
        		plot.addGridPlot(chart.getTitle(), Color.black, x, y, zGrid);
        	}
        }
        
        return plot;
	}	

	private double[][] buildZGrid(double[] x, double[] y, List<Double> zList)
	{
		double[][] zGrid = new double[x.length][y.length];
		int zIdx = 0;
		for (int i = 0; i < x.length; i++)
		{
			for (int j = 0; j < y.length; j++)
			{
				zGrid[i][j] = zList.get(zIdx);
				zIdx++;
			}
		}
		return zGrid;
	}
}
