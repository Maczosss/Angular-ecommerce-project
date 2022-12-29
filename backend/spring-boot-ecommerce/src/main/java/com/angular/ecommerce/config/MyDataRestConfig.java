package com.angular.ecommerce.config;

import com.angular.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions =
                {
                        HttpMethod.PUT,
                        HttpMethod.POST,
                        HttpMethod.DELETE,
                        HttpMethod.PATCH
                };

        //disable those methods for product

        disableHttpMethods(config, Product.class, theUnsupportedActions);

        //for product category

        disableHttpMethods(config, ProductCategory.class, theUnsupportedActions);

        // for Country entity
        disableHttpMethods(config, Country.class, theUnsupportedActions);

        //for state entity
        disableHttpMethods(config, State.class, theUnsupportedActions);

        //for orders entity
        disableHttpMethods(config, Order.class, theUnsupportedActions);


        //call an internal helper method
        exposeIds(config);

        //configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(RepositoryRestConfiguration config, Class theClass, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration configuration) {
        //expose entity ids

        //get a list of all entity classes from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        //get the entity types for entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        //expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        configuration.exposeIdsFor(domainTypes);
    }
}
