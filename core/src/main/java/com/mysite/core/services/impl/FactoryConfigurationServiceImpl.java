package com.mysite.core.services.impl;

import com.mysite.core.services.FactoryConfigurationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component(
        service = {FactoryConfigurationService.class},
        immediate = true
)
@Designate(ocd = FactoryConfigurationServiceImpl.Config.class, factory=true)
public class FactoryConfigurationServiceImpl implements FactoryConfigurationService {

    @ObjectClassDefinition(name="Factory Configuration Service Impl", description = "Configure Factory Configuration Service")
    @interface Config {

        @AttributeDefinition(name = "Namw",
                description = "Add Name",
                type = AttributeType.STRING)
        String name();

        @AttributeDefinition(name = "List of Items",
                description = "Add List of Items",
                type = AttributeType.STRING)
        String[] listOfItems();
    }

    private List<String> listOfItems = new ArrayList<>();
    private String item;

    @Activate
    @Modified
    public void activate(final Config config) {
        item = config.name();
        listOfItems = Arrays.asList(config.listOfItems());
    }

    @Override
    public List<String> getListOfItems() {
        return listOfItems;
    }

    @Override
    public String getItem() {
        return item;
    }
}
