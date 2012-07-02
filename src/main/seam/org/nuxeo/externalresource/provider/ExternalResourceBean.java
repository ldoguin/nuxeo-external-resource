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

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;
import org.nuxeo.runtime.api.Framework;

/**
 * 
 * This is a simple service wrapper that allow to inject a Nuxeo Runtime service
 * as a Seam component
 * 
 */
@Name("externalResourceService")
@Scope(ScopeType.EVENT)
@AutoLog
public class ExternalResourceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    protected ExternalResourceProviderService externalResourceService;

    @Unwrap
    public ExternalResourceProviderService getService() throws Exception {
        if (externalResourceService == null) {
            externalResourceService = Framework.getService(ExternalResourceProviderService.class);
        }
        return externalResourceService;
    }

}
