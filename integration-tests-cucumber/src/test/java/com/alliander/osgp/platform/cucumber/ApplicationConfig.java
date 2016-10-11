/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.platform.cucumber;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Configuration
@PropertySources({
    @PropertySource("classpath:cucumber-platform-dlms.properties"),
    @PropertySource(value = "file:/etc/osp/cucumber-platform-dlms.properties", ignoreResourceNotFound = true)}
)

@Component
public class ApplicationConfig {
}
