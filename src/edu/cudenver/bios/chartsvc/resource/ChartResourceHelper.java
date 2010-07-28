package edu.cudenver.bios.chartsvc.resource;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.cudenver.bios.chartsvc.application.ChartConstants;
import edu.cudenver.bios.chartsvc.application.ChartLogger;
import edu.cudenver.bios.chartsvc.domain.Axis;
import edu.cudenver.bios.chartsvc.domain.Chart;
import edu.cudenver.bios.chartsvc.domain.Series;

public class ChartResourceHelper
{
	public static Chart chartFromDomNode(Node node)
	throws ResourceException
	{
		Chart chart = new Chart();
		
        // make sure the root node is a chart
        if (!node.getNodeName().equals(ChartConstants.TAG_CHART))
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid root node '" + node.getNodeName() + "' when parsing chart object");
        
        // parse the title from the attribute list
        NamedNodeMap attrs = node.getAttributes();
        Node chartTitleNode = attrs.getNamedItem(ChartConstants.ATTR_TITLE);
        if (chartTitleNode != null) chart.setTitle(chartTitleNode.getNodeValue());
        // parse whether the legend should be displayed
        Node legendNode = attrs.getNamedItem(ChartConstants.ATTR_LEGEND);
        if (legendNode != null) 
            chart.setLegend(Boolean.parseBoolean(legendNode.getNodeValue()));
        
        /* process the child elements.  Includes matrix and list inputs */
        NodeList children = node.getChildNodes();
        if (children != null && children.getLength() > 0)
        {
            for (int i = 0; i < children.getLength(); i++)
            {
                Node child = children.item(i);

                if (ChartConstants.TAG_XAXIS.equals(child.getNodeName()))
                {
                	chart.setXAxis(axisFromDomNode(child));
                }
                else if (ChartConstants.TAG_YAXIS.equals(child.getNodeName()))
                {
                	chart.setYAxis(axisFromDomNode(child));
                }
                else if (ChartConstants.TAG_SERIES.equals(child.getNodeName()))
                {
                	chart.addSeries(seriesFromDomNode(child));
                }
                else 
                {
                    ChartLogger.getInstance().warn("Ignoring unknown tag while parsing chart: " + child.getNodeName());
                }
            }
        }
        
        
		return chart;
	}
	
	public static Axis axisFromDomNode(Node node)
	throws ResourceException
	{
		Axis axis = null;
        NamedNodeMap attrs = node.getAttributes();
        Node labelNode = attrs.getNamedItem(ChartConstants.ATTR_LABEL);
        if (labelNode != null) axis = new Axis(labelNode.getNodeValue());
        
        if (axis != null)
        {
        	try
        	{
        		Node numTicksNode = attrs.getNamedItem(ChartConstants.ATTR_TICKS);
        		if (numTicksNode != null) 
        			axis.setNumberTicks(Integer.parseInt(numTicksNode.getNodeValue()));
        		
                Node rangeMinNode = attrs.getNamedItem(ChartConstants.ATTR_MIN);
                if (rangeMinNode != null) 
                    axis.setRangeMin(Double.parseDouble(rangeMinNode.getNodeValue()));
                
                Node rangeMaxNode = attrs.getNamedItem(ChartConstants.ATTR_MAX);
                if (rangeMaxNode != null) 
                    axis.setRangeMax(Double.parseDouble(rangeMaxNode.getNodeValue()));
        	}
        	catch (NumberFormatException e)
        	{
        		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        	}
        }
        return axis;
	}
	
	public static Series seriesFromDomNode(Node node)
	throws ResourceException
	{
		Series series = null;
        NamedNodeMap attrs = node.getAttributes();
        Node labelNode = attrs.getNamedItem(ChartConstants.ATTR_LABEL);
        if (labelNode != null) series = new Series(labelNode.getNodeValue());
		
        if (series != null)
        {
            NodeList children = node.getChildNodes();
            if (children != null && children.getLength() > 0)
            {
                for (int i = 0; i < children.getLength(); i++)
                {
                    Node child = children.item(i);
                    if (ChartConstants.TAG_DATA.equals(child.getNodeName()))
                    {
                    	Node dataNode = child.getFirstChild();
                    	if (dataNode != null)
                    	{
                    		series.addData(Double.parseDouble(dataNode.getNodeValue()));
                    	}
                    }
                }
            }
        }
        
		return series;
	}
}
