package com.mysite.core.servlets;

import com.mysite.core.services.FactoryConfigurationService;
import com.mysite.core.services.FactoryReferenceService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component(
        immediate = true,
        service = {Servlet.class},
        enabled = true,
        property = {
                Constants.SERVICE_DESCRIPTION + "=" + "Fetch Keywords Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/factoryConfiguration"
        }
)
public class FactoryConfigurationServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private transient FactoryReferenceService factoryReferenceService;


    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Factory Configuration Implementation..");
        List<FactoryConfigurationService> factoryServiceList = factoryReferenceService.getFactoryServiceList();
        if(factoryServiceList != null) {
            factoryServiceList.forEach(config -> {
                out.println("Config Name - "+config.getItem());
                out.println("Config List - "+config.getListOfItems());
            });
        }
    }
}
