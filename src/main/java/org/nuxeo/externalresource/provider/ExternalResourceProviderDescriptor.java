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

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("provider")
public class ExternalResourceProviderDescriptor {

    @XNode("@name")
    private String name;

    @XNode("@enabled")
    private boolean enabled = true;

    @XNode("@class")
    private Class<ExternalResourceProvider> className;

    public Class<ExternalResourceProvider> getClassName() {
        return className;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

}
