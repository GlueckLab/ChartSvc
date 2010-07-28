package edu.cudenver.bios.chartsvc.representation;

import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.restlet.data.MediaType;
import org.restlet.resource.OutputRepresentation;

public class ChartImageRepresentation extends OutputRepresentation
{
	protected JFreeChart chart;
	protected int width, height;
	
	public ChartImageRepresentation(JFreeChart chart, int width, int height)
	{
		super(MediaType.IMAGE_JPEG);
		this.chart = chart;
		this.width = width;
		this.height = height;
		
	}
	
	@Override
	public void write(OutputStream out) throws IOException
	{
		ChartUtilities.writeChartAsJPEG(out, chart, width, height);
	}

}
