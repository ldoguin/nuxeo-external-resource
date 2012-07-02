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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeProvider extends AbstractOEmbedProvider {

    protected Pattern youtubePattern = Pattern.compile("(.*youtube.*)",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public boolean match(String url) {
        Matcher m = youtubePattern.matcher(url);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "youtube";
    }

    @Override
    public String getIcon() {
        return "/img/youtube.gif";
    }

    @Override
    public Map<String, Serializable> getProperties(String url) {
        return getPropertiesWithAutoDiscovery(url);
    }

}
