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

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.chartsvc.application.ChartConstants;


/**
 * Default request resource.  Called from the URI /power
 * Simply returns a self-identifying message for the server
 */
public class DefaultResource extends ServerResource
{
	

    /**
     * Returns a full representation for a given variant.
     */
    @Get
    public Representation represent(Variant variant) {
        String version = 
                getApplication().getContext().getParameters().getFirstValue(
                        "edu.ucdenver.bios.chartsvc.application.version");
        Representation representation = 
            new StringRepresentation("Chart REST Service, version " + version, 
            		MediaType.TEXT_PLAIN);

        return representation;
    }
}
