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

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Router;

import edu.cudenver.bios.chartsvc.resource.DefaultResource;
import edu.cudenver.bios.chartsvc.resource.ScatterPlotResource;


/**
 * Main Restlet application class for the Power Service.
 * Defines URI mappings to the appropriate power,
 * sample size, or detectable difference resource
 */
public class ChartApplication extends Application
{   
    /**
     * Class which dispatches http requests to the appropriate
     * handler class for the power service.
     * 
     * @param parentContext
     */
    public ChartApplication(Context parentContext) throws Exception
    {
        super(parentContext);

        ChartLogger.getInstance().info("Chart service starting.");
    }

    /**
     * Define URI mappings for incoming power, sample size,
     * and detectable difference requests
     */
    @Override
    public Restlet createRoot() 
    {
        // Create a router Restlet that routes each call to a new instance of Resource.
        Router router = new Router(getContext());
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);

        // Scatter chart resource
        router.attach("/scatter", ScatterPlotResource.class);
        
        return router;
    }
}

