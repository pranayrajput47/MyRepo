package com.mysite.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(
        immediate = true,
        service = {Servlet.class},
        enabled = true,
        property = {
                Constants.SERVICE_DESCRIPTION + "=" + "Fetch Keywords Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/fetchKeyword"
        }
)
public class FetchSearchKeywordsServlet extends SlingSafeMethodsServlet {

    //private static final Logger log = LoggerFactory.getLogger(FetchSearchKeywordsServlet.class);

    private static final long serialVersionUID = 1L;

    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
          req.getParameter("keyWord").toString();
         resp.setContentType("application/json");

        PrintWriter out = null;
        out = resp.getWriter();
        out.println("Requested Keywoard: " + req.getParameter("keyWord").toString());
    }
}
