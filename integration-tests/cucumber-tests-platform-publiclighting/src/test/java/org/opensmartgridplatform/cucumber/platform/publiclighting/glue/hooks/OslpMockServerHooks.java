/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.cucumber.platform.publiclighting.glue.hooks;

import org.opensmartgridplatform.cucumber.core.GlueBase;
import org.opensmartgridplatform.cucumber.platform.publiclighting.mocks.oslpdevice.MockOslpServer;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;

public class OslpMockServerHooks extends GlueBase {

    @Autowired
    private MockOslpServer mockServer;

    @Before("@OslpMockServer")
    public void resetServer() {
        if (this.mockServer != null) {
            this.mockServer.resetServer();
        }
    }
}
