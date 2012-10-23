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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;
import org.osgi.framework.Bundle;

/**
 * @author ldoguin
 */
public class ExternalResourceProviderServiceImpl extends DefaultComponent
        implements ExternalResourceProviderService {

    private static final String PROVIDER_DECLARATION_EP = "provider";

    public static final String NAME = "org.nuxeo.externalresource.provider.ExternalResourceService";;

    private static final Log log = LogFactory.getLog(ExternalResourceProviderServiceImpl.class);

    protected Map<String, ExternalResourceProvider> providerInstances;

    protected Bundle bundle;

    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Component activated notification. Called when the component is activated.
     * All component dependencies are resolved at that moment. Use this method
     * to initialize the component.
     * <p>
     * The default implementation of this method is storing the Bundle owning
     * that component in a class field. You can use the bundle object to lookup
     * for bundle resources:
     * <code>URL url = bundle.getEntry("META-INF/some.resource");</code>, load
     * classes or to interact with OSGi framework.
     * <p>
     * Note that you must always use the Bundle to lookup for resources in the
     * bundle. Do not use the classloader for this.
     * 
     * @param context the component context. Use it to get the current bundle
     *            context
     */
    @Override
    public void activate(ComponentContext context) {
        this.bundle = context.getRuntimeContext().getBundle();
        this.providerInstances = new HashMap<String, ExternalResourceProvider>();
    }

    /**
     * Component deactivated notification. Called before a component is
     * unregistered. Use this method to do cleanup if any and free any resources
     * held by the component.
     * 
     * @param context the component context. Use it to get the current bundle
     *            context
     */
    @Override
    public void deactivate(ComponentContext context) {
        this.bundle = null;
        this.providerInstances = null;
    }

    @Override
    public void registerContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor) {
        if (extensionPoint.equals(PROVIDER_DECLARATION_EP)) {
            if (contribution instanceof ExternalResourceProviderDescriptor) {
                ExternalResourceProviderDescriptor provider = (ExternalResourceProviderDescriptor) contribution;
                try {
                    String providerName = provider.getName();
                    if (provider.isEnabled()) {
                        ExternalResourceProvider providerInstance = provider.getClassName().newInstance();
                        providerInstances.put(providerName, providerInstance);
                    } else {
                        if (providerInstances.containsKey(providerName)) {
                            providerInstances.remove(providerName);
                        }
                    }
                } catch (InstantiationException e) {
                    log.error("Error while creating instance of provider "
                            + provider.getName() + " :" + e.getMessage());
                } catch (IllegalAccessException e) {
                    log.error("Error while creating instance of provider "
                            + provider.getName() + " :" + e.getMessage());
                }

            }
        }
    }

    @Override
    public void unregisterContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor)
            throws Exception {
        if (extensionPoint.equals(PROVIDER_DECLARATION_EP)) {
            if (contribution instanceof ExternalResourceProviderDescriptor) {
                ExternalResourceProviderDescriptor provider = (ExternalResourceProviderDescriptor) contribution;
                String providerName = provider.getName();
                if (providerInstances.containsKey(providerName)) {
                    providerInstances.remove(providerName);
                }
            }
        }
    }

    @Override
    public List<ExternalResourceProvider> getProviders() {
        return new ArrayList<ExternalResourceProvider>(
                providerInstances.values());
    }

    @Override
    public ExternalResourceProvider getProvider(String providerName) {
        return providerInstances.get(providerName);
    }
}
