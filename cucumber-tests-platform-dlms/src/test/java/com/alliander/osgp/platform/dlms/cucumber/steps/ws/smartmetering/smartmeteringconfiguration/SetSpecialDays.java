/**
 * Copyright 2016 Smart Society Services B.V.
 */
package com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.smartmeteringconfiguration;

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

public class SetSpecialDays extends SmartMeteringStepsBase {
    private static final String PATH_RESULT = "/Envelope/Body/SetSpecialDaysResponse/Result/text()";

    private static final String TEST_SUITE_XML = "SmartmeterConfiguration";
    private static final String TEST_CASE_XML = "215 Retrieve SetSpecialDays result";
    private static final String TEST_CASE_NAME_REQUEST = "SetSpecialDays - Request 1";
    private static final String TEST_CASE_NAME_GETRESPONSE_REQUEST = "GetSetSpecialDaysResponse - Request 1";

    @When("^the set special days request is received$")
    public void theSetSpecialDaysRequestIsReceived(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
        PROPERTIES_MAP.put(ORGANISATION_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_ORGANIZATION_IDENTIFICATION, Defaults.DEFAULT_ORGANIZATION_IDENTIFICATION));

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);
    }

    @Then("^the special days should be set on the device$")
    public void theSpecialDaysShouldBeSetOnTheDevice(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
        PROPERTIES_MAP.put(CORRELATION_UID_LABEL, ScenarioContext.Current().get("CorrelationUid").toString());

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_GETRESPONSE_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);

        assertTrue(this.runXpathResult.assertXpath(this.response, PATH_RESULT, Defaults.EXPECTED_RESULT_OK));
    }
}
