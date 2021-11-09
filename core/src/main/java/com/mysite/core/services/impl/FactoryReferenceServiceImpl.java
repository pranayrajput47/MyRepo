package com.mysite.core.services.impl;

import com.mysite.core.services.FactoryConfigurationService;
import com.mysite.core.services.FactoryReferenceService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.ArrayList;
import java.util.List;

@Component(service = FactoryReferenceService.class, immediate = true)
public class FactoryReferenceServiceImpl implements FactoryReferenceService {

    @Reference
    private transient List<FactoryConfigurationService> factoryServiceList;

    @Reference(name = "configurationfactory", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    protected synchronized void bindConfigurationFactory(final FactoryConfigurationService config) {
        if(factoryServiceList == null){
            factoryServiceList = new ArrayList<>();
        }
        factoryServiceList.add(config);
    }

    protected synchronized void unbindConfigurationFactory(final FactoryConfigurationService config) {
        if(factoryServiceList == null){
            factoryServiceList = new ArrayList<>();
        }
        factoryServiceList.remove(config);
    }

    @Override
    public List<FactoryConfigurationService> getFactoryServiceList() {
        return factoryServiceList;
    }
}
