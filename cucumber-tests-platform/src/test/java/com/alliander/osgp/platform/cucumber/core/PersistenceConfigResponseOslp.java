/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.platform.cucumber.core;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alliander.osgp.adapter.protocol.oslp.domain.repositories.OslpDeviceRepository;

@EnableJpaRepositories(entityManagerFactoryRef = "entityMgrFactOslp", transactionManagerRef = "txMgrOslp", basePackageClasses = {
        OslpDeviceRepository.class })
@Configuration
@EnableTransactionManagement()
@Primary
@PropertySources({ @PropertySource("classpath:cucumber-platform.properties"),
        @PropertySource(value = "file:/etc/osp/cucumber-platform.properties", ignoreResourceNotFound = true)}
)
public class PersistenceConfigResponseOslp extends AbstractPersistenceConfig {

    public PersistenceConfigResponseOslp() {
    }

    @Value("${cucumber.osgpadapterprotocoloslpdbs.url}")
    private String databaseUrl;

    @Value("${entitymanager.packages.to.scan.oslp}")
    private String entitymanagerPackagesToScan;

    @Override
    protected String getDatabaseUrl() {
        return this.databaseUrl;
    }

    @Override
    protected String getEntitymanagerPackagesToScan() {
        return this.entitymanagerPackagesToScan;
    }

    /**
     * Method for creating the Data Source.
     *
     * @return DataSource
     */
    @Bean(name = "dsOslp")
    public DataSource dataSource() {
        return this.makeDataSource();
    }

    /**
     * Method for creating the Entity Manager Factory Bean.
     *
     * @return LocalContainerEntityManagerFactoryBean
     * @throws ClassNotFoundException
     *             when class not found
     */
    @Bean(name = "entityMgrFactOslp")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dsOslp") final DataSource dataSource)
            throws ClassNotFoundException {

        return this.makeEntityManager("OSGP_CUCUMBER_OSLP", dataSource);
    }

    /**
     * Method for creating the Transaction Manager.
     *
     * @return JpaTransactionManager
     * @throws ClassNotFoundException
     *             when class not found
     */
    @Bean(name = "txMgrOslp")
    public JpaTransactionManager transactionManager(
            @Qualifier("entityMgrFactOslp") final EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }

}
