package com.mysite.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.mysite.core.services.SearchResultService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;
@Component
public class SearchResultServiceImpl implements SearchResultService {

    private static final Logger log = LoggerFactory.getLogger(SearchResultServiceImpl.class);

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resolverFactory;

    Session session;

    @Activate
    protected final void activate() throws LoginException {
        try{
            ResourceResolver adminResolver = resolverFactory.getAdministrativeResourceResolver(null);
            session = adminResolver.adaptTo(Session.class);
            log.info("Got session for Search Result Service :: {}", session.getUserID());
        }catch(Exception e) {
            log.error("Error in getting session for Search Result Service :: {}", e.getMessage());
        }

    }

    @Deactivate
    protected final void deactivate(){
        session.logout();
        log.info("Logged out session for Search Result Service");
    }

    @Override
    public SearchResult createQuery(String keyword) {
        if(session != null) {
            SearchResult result = null;
            Map<String, String> map = new HashMap<String, String>();
            map.put("path", "/content/royal-enfield");
            map.put("type", "cq:Page");
            map.put("1_property", "jcr:content/cq:template");
            map.put("1_property.value", "/apps/re-platform/templates/motorcycleDetailPage");
            map.put("2_property", "jcr:content/jcr:title");
            map.put("2_property.value", keyword);
            //map.put("group.p.and", "true"); // combine this group with OR
            //map.put("group.1_fulltext", fulltextSearchTerm);
            //map.put("p.offset", "0"); // same as query.setStart(0) below
            //map.put("p.limit", "20"); // same as query.setHitsPerPage(20)
            try {
                result = executeQuery(map);
//            int hitsPerPage = result.getHits().size();
//            long totalMatches = result.getTotalMatches();
//            long offset = result.getStartIndex();
//            long numberOfPages = totalMatches / 20;
            } catch (Exception e) {
                log.error("Error in createQuery method :: {}", e);
            }

            return result;
        }

        return null;

    }

    private SearchResult executeQuery(Map<String, String> executionMap) {
        try{
            if(executionMap != null){
                log.info("Execution Map :: {}", executionMap);
                Query query = queryBuilder.createQuery(PredicateGroup.create(executionMap), session);
                log.info("Query :: {}", query.getPredicates());
//                query.setStart(0);
//                query.setHitsPerPage(20);
                SearchResult result = query.getResult();
                log.info("result matches :: {}", result.getTotalMatches());
                return result;
            }
        } catch (Exception e) {
            log.error("Error in executeQuery method :: {}",e);
        }
        return null;
    }
}
