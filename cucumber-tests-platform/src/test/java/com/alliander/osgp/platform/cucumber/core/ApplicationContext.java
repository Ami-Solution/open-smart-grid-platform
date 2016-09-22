/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.platform.cucumber.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * An application context Java configuration class. The usage of Java
 * configuration requires Spring Framework 4.0
 */
@Configuration
@ComponentScan(basePackages = { "com.alliander.osgp.adapter.ws.smartmetering.domain.entities",
        "com.alliander.osgp.domain.core.repositories", "com.alliander.osgp.domain.core.entities",
        "com.alliander.osgp.logging.domain.repositories",
        "com.alliander.osgp.adapter.protocol.oslp.domain.repositories",
        "com.alliander.osgp.adapter.protocol.oslp.domain.entities",
        "com.alliander.osgp.platform.cucumber.hooks" })
@EnableTransactionManagement()
@Import({ PersistenceConfigCore.class, PersistenceConfigResponseData.class, PersistenceConfigResponseDlms.class,
        PersistenceConfigLogging.class, PersistenceConfigResponseOslp.class })
@PropertySources({ 
		@PropertySource("classpath:cucumber-platform.properties"),
        @PropertySource(value = "file:${osgp/cucumber/platform}", ignoreResourceNotFound = true),
        @PropertySource(value = "file:/etc/osp/cucumber-platform.properties", ignoreResourceNotFound = true)}
)
public class ApplicationContext {

}
