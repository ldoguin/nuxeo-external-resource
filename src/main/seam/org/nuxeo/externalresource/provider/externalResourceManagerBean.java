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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.model.PropertyException;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;
import org.nuxeo.ecm.webapp.helpers.ResourcesAccessor;
import org.nuxeo.externalresource.ExternalResourceConstants;

@Name("externalResourceManager")
@Scope(ScopeType.EVENT)
public class externalResourceManagerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(externalResourceManagerBean.class);

    @In(create = true, required = false)
    protected transient CoreSession documentManager;

    @In(create = true)
    protected NavigationContext navigationContext;

    @In(create = true, required = false)
    protected transient FacesMessages facesMessages;

    @In(create = true)
    protected transient ResourcesAccessor resourcesAccessor;

    @In(create = true)
    protected ExternalResourceProviderService externalResourceService;

    protected List<ExternalResourceProvider> providerInstanceList;

    protected String link;

    private String title;

    protected String html;

    private String providerName;

    private String providerIcon;;

    public List<ExternalResourceProvider> getProviderOptions() {
        if (providerInstanceList == null) {
            providerInstanceList = externalResourceService.getProviders();
        }
        return providerInstanceList;
    }

    public void renderUrl() throws PropertyException, ClientException {
        if (link == null) {
            return;
        }
        for (ExternalResourceProvider provider : getProviderOptions()) {
            if (provider.match(link)) {
                Map<String, Serializable> properties = provider.getProperties(link);
                if (properties != null) {
                    setHtml((String) properties.get(ExternalResourceConstants.EXTERNAL_RESOURCE_HTML_KEY));
                    setTitle((String) properties.get(ExternalResourceConstants.EXTERNAL_RESOURCE_TITLE_KEY));
                    setProviderIcon(provider.getIcon());
                    setProviderName(provider.getName());
                }
                return;
            }
        }
    }

    public void inputChange(ActionEvent event) throws PropertyException,
            ClientException {
        UIComponent input = event.getComponent().getParent();
        if (input instanceof ValueHolder) {
            link = (String) ((ValueHolder) input).getValue();
            link = link.trim();
            renderUrl();
        } else {
            log.error("Bad component returned " + input);
            throw new AbortProcessingException("Bad component returned "
                    + input);
        }
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setProviderIcon(String providerIcon) {
        this.providerIcon = providerIcon;
    }

    public String getProviderIcon() {
        return providerIcon;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
