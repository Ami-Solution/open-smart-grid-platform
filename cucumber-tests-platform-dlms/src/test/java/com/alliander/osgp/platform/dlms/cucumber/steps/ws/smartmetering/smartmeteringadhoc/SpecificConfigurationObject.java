/**
 * Copyright 2016 Smart Society Services B.V.
 */
package com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.smartmeteringadhoc;

import static com.alliander.osgp.platform.cucumber.core.Helpers.getString;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.alliander.osgp.platform.cucumber.core.ScenarioContext;
import com.alliander.osgp.platform.cucumber.steps.Defaults;
import com.alliander.osgp.platform.cucumber.steps.Keys;
import com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.SmartMeteringStepsBase;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SpecificConfigurationObject extends SmartMeteringStepsBase {
    private static final String PATH_RESULT_CONFIGURATION_DATA = "/Envelope/Body/SpecificAttributeValueResponse/ConfigurationData/text()";

    private static final String XPATH_MATCHER_RESULT_CONFIGURATION_DATA = "DataObject: Choice=\\w[A-Z]+, ResultData \\w+, value=";

    private static final String TEST_SUITE_XML = "SmartmeterAdhoc";
    private static final String TEST_CASE_XML = "534 Retrieve specific attribute value";
    private static final String TEST_CASE_NAME_REQUEST = "GetSpecificAttributeValue - Request 1";
    private static final String TEST_CASE_NAME_GETRESPONSE_REQUEST = "GetSpecificAttributeValueResponse - Request 1";

    @When("^receiving a retrieve specific configuration request$")
    public void receivingARetrieveSpecificConfigurationRequestIsReceived(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);
    }

    @Then("^the specific configuration item should be returned$")
    public void theSpecificConfigurationItemShouldBeReturned(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
        PROPERTIES_MAP.put(CORRELATION_UID_LABEL, ScenarioContext.Current().get("CorrelationUid").toString());

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_GETRESPONSE_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);

        assertTrue(this.runXpathResult.assertXpath(this.response, PATH_RESULT_CONFIGURATION_DATA,
                XPATH_MATCHER_RESULT_CONFIGURATION_DATA));
    }
}
