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
package org.nuxeo.externalresource.provider.instance.oembed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.nuxeo.externalresource.ExternalResourceConstants;
import org.nuxeo.externalresource.provider.ExternalResourceProvider;

import ac.simons.oembed.Oembed;
import ac.simons.oembed.OembedBuilder;
import ac.simons.oembed.OembedException;
import ac.simons.oembed.OembedProviderBuilder;
import ac.simons.oembed.OembedResponse;

public abstract class AbstractOEmbedProvider implements
        ExternalResourceProvider {

    private static final Log log = LogFactory.getLog(AbstractOEmbedProvider.class);

    public Map<String, Serializable> getPropertiesWithAutoDiscovery(String url) {
        final Oembed oembed = new Oembed(new DefaultHttpClient());
        oembed.setAutodiscovery(true);
        return getPropertiesFromOEmbedResponse(oembed, url);
    }

    public Map<String, Serializable> getProperties(String url, String format,
            String endpoint, String urlSchemes, Integer maxWidth,
            Integer maxHeight) {
        final Oembed oembed = new OembedBuilder(new DefaultHttpClient()).withProviders(
                new OembedProviderBuilder().withName(getName()).withFormat(
                        format).withEndpoint(endpoint).withUrlSchemes(
                        urlSchemes).withMaxHeight(maxHeight).withMaxWidth(
                        maxWidth).build()).build();
        return getPropertiesFromOEmbedResponse(oembed, url);
    }

    public Map<String, Serializable> getPropertiesFromOEmbedResponse(
            Oembed oembed, String url) {
        try {
            OembedResponse response = oembed.transformUrl(url);
            if (response != null) {
                Map<String, Serializable> properties = new HashMap<String, Serializable>();
                properties.put(
                        ExternalResourceConstants.EXTERNAL_RESOURCE_HTML_KEY,
                        response.render());
                properties.put(
                        ExternalResourceConstants.EXTERNAL_RESOURCE_TITLE_KEY,
                        response.getTitle());
                return properties;
            }
        } catch (OembedException e) {
            log.warn("Could not extract oEmbed properties from url: " + url);
            log.debug(e);
        }
        return null;
    }
}
