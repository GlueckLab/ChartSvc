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
package edu.cudenver.bios.chartsvc.application;

/**
 * Convenience class for chart service constants
 * @author Sarah Kreidler
 *
 */
public class ChartConstants
{
	public static final String VERSION = "2.0.0";
	// form entry containing data
	public static final String QUERY_PARAM_DATA = "data";
	
	// tag names
	public static final String TAG_ERROR = "error";
	public static final String TAG_CHART = "chart";
	public static final String TAG_XAXIS = "xaxis";
	public static final String TAG_YAXIS = "yaxis";
	public static final String TAG_SERIES = "series";
	public static final String TAG_POINT = "p";
	// attribute names
	public static final String ATTR_LABEL = "label";
	public static final String ATTR_TITLE = "title";
	public static final String ATTR_TICKS = "ticks";
    public static final String ATTR_MIN = "min";
    public static final String ATTR_MAX = "max";
    public static final String ATTR_LEGEND = "legend";
    public static final String ATTR_X = "x";
    public static final String ATTR_Y = "y";

    // query parameters - based on the Google Chart API syntax
	public static final String QPARAM_TITLE = "chtt";
	public static final String QPARAM_SIZE = "chs";
	public static final String QPARAM_DATA = "chd";
	public static final String QPARAM_SERIES_LABEL = "chdl";
	public static final String QPARAM_DATA_PREFIX = "t:";
	public static final String QPARAM_AXIS_LABEL = "chxl";
	public static final String QPARAM_TOKEN_SEPARATOR = "|";
	public static final String QPARAM_VALUE_SEPARATOR = ",";
}
