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

/**
 * Helper class to parse incoming chart requests
 * @author Sarah Kreidler
 *
 */
public class ChartResourceHelper
{
	/**
	 * Create a chart object from an XML chart description
	 * @param node node associated with the chart tag
	 * @return chart object
	 * @throws ResourceException
	 */
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
	
	/**
	 * Create an axis object from an XML axis description
	 * @param node node associated with the axis tag
	 * @return axis object
	 * @throws ResourceException
	 */
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
	
	/**
	 * Create an data series object from an XML data series description
	 * @param node node associated with the data series tag
	 * @return data series object
	 * @throws ResourceException
	 */
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
        			if (ChartConstants.TAG_POINT.equals(child.getNodeName()))
        			{
        				NamedNodeMap pointAttrs = child.getAttributes();
        				try
        				{
        					Node xNode = pointAttrs.getNamedItem(ChartConstants.ATTR_X);
        					Node yNode = pointAttrs.getNamedItem(ChartConstants.ATTR_Y);
        					if (xNode != null && yNode != null)
        					{
        						series.addData(Double.parseDouble(xNode.getNodeValue()), 
        								Double.parseDouble(yNode.getNodeValue()));
        					}
        				}
        				catch (NumberFormatException nfe)
        				{
        					ChartLogger.getInstance().warn("Ignoring invalid data point", nfe);
        				}
        			}
        		}
        	}
        }
        
		return series;
	}
}
