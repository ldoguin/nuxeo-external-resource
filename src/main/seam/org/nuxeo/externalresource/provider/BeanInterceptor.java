package org.nuxeo.externalresource.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.core.BijectionInterceptor;
import org.jboss.seam.intercept.AbstractInterceptor;
import org.jboss.seam.intercept.InvocationContext;

@Interceptor(stateless = true, within = BijectionInterceptor.class)
public class BeanInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(BeanInterceptor.class);

    @Override
    public Object aroundInvoke(InvocationContext ic) throws Exception {
        log.error("$$$$$$$$$$$$$$$$$$$ Invoking method from wrapper "
                + ic.getMethod().getName());
        return ic.proceed();
    }

    @Override
    public boolean isInterceptorEnabled() {
        return true;
    }
}
