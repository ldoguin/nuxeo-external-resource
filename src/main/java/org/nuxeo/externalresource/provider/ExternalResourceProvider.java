/*
 * (C) Copyright 2006-2012 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Laurent Doguin <ldoguin@nuxeo.com>
 *
 */
package org.nuxeo.externalresource.provider;

import java.io.Serializable;
import java.util.Map;

public interface ExternalResourceProvider {

    /**
     * @return the name of the provider, also stored in the externalResource
     *         schema.
     */
    String getName();

    /**
     * 
     * @return the path to the provider icon defined in it's contribution.
     */
    String getIcon();

    /**
     * 
     * @param url
     * @return must return true if the provider is able to handle the given URL.
     */
    boolean match(String url);

    /**
     * 
     * @param url
     * @return the different properties extracted from the given url by the
     *         provider. It must return at least a title and an HTML preview of
     *         the content.
     */
    Map<String, Serializable> getProperties(String url);
}
