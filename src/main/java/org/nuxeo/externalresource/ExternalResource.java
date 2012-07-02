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
package org.nuxeo.externalresource;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.model.PropertyException;

public class ExternalResource {

    protected DocumentModel doc;

    public ExternalResource(DocumentModel doc) {
        this.doc = doc;
    }

    public String getLink() {
        return getStringPropertyValue(ExternalResourceConstants.EXTERNAL_RESOURCE_LINK_PROPERTY_NAME);
    }

    public String getProviderName() {
        return getStringPropertyValue(ExternalResourceConstants.EXTERNAL_RESOURCE_PROVIDER_PROPERTY_NAME);
    }

    public String getProviderIcon() {
        return getStringPropertyValue(ExternalResourceConstants.EXTERNAL_RESOURCE_PROVIDER_ICON_PROPERTY_NAME);
    }

    public String getHTML() {
        return getStringPropertyValue(ExternalResourceConstants.EXTERNAL_RESOURCE_HTML_PROPERTY_NAME);
    }

    protected String getStringPropertyValue(String xPath) {
        try {
            return doc.getProperty(xPath).getValue(String.class);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
