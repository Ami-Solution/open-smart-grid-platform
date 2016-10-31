/**
 * Copyright 2016 Smart Society Services B.V.
 */
package com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.smartmeteringbundle;

import static com.alliander.osgp.platform.cucumber.core.Helpers.getString;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.alliander.osgp.platform.cucumber.core.ScenarioContext;
import com.alliander.osgp.platform.cucumber.steps.Defaults;
import com.alliander.osgp.platform.cucumber.steps.Keys;
import com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.SmartMeteringStepsBase;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Bundle extends SmartMeteringStepsBase {
    private static final String TEST_SUITE_XML = "SmartmeterConfiguration";
    private static final String TEST_CASE_XML = "484 Handle a bundle of requests";
    private static final String TEST_CASE_NAME_REQUEST = "Bundle - Request 1";
    private static final String TEST_CASE_NAME_GETRESPONSE_REQUEST = "GetBundleResponse - Request 1";

    private static final Logger LOGGER = LoggerFactory.getLogger(Bundle.class);
    private static final List<String> REQUEST_ACTIONS = new ArrayList<>();

    private static final Map<String, String> REQUEST_RESPONSE_PAIRS = new HashMap<>();
    static {
        REQUEST_RESPONSE_PAIRS.put("FindEventsRequest", "FindEventsResponse");
        REQUEST_RESPONSE_PAIRS.put("SetSpecialDaysRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("GetSpecificAttributeValueRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("ReadAlarmRegisterRequest", "ReadAlarmRegisterResponse");
        REQUEST_RESPONSE_PAIRS.put("SetAdministrativeStatusRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("GetActualMeterReadsRequest", "ActualMeterReadsResponse");
        REQUEST_RESPONSE_PAIRS.put("GetAdministrativeStatusRequest", "AdministrativeStatusResponse");
        REQUEST_RESPONSE_PAIRS.put("GetPeriodicMeterReadsRequest", "PeriodicMeterReadsResponse");
        REQUEST_RESPONSE_PAIRS.put("SetActivityCalendarRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("SetAlarmNotificationsRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("SetConfigurationObjectRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("SetPushSetupAlarmRequest", "ActionResponse");
        REQUEST_RESPONSE_PAIRS.put("SynchronizeTimeRequest", "ActionResponse");
    }

    @When("^a bundled request message is received$")
    public void aBundledRequestMessageIsReceived(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
        PROPERTIES_MAP.put(ORGANISATION_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_ORGANIZATION_IDENTIFICATION, Defaults.DEFAULT_ORGANIZATION_IDENTIFICATION));

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);

//        final NodeList nodeList = this.runXpathResult.getNodeList(this.request, "//Actions/*");
//        for (int nodeId = 0; nodeId < nodeList.getLength(); nodeId++) {
//            REQUEST_ACTIONS.add(this.formatAction(nodeList.item(nodeId).getNodeName(), "Request"));
//        }

        final NodeList nodeList = this.runXpathResult.getNodeList(this.request, "//Actions/*");
        for (int nodeId = 0; nodeId < nodeList.getLength(); nodeId++) {
            REQUEST_ACTIONS.add(this.removeNamespace(nodeList.item(nodeId).getNodeName()));
        }
    }

    /**
     * An action is in the node list in the form of <ns1:FindEventsRequest> or
     * <ns2:FindEventsResponseData> We want to loose the namespace and postfix
     */
    private String formatAction(final String input, final String postfix) {
        final String request = input.split(":")[1];
        if (request.indexOf(postfix) > 0) {
            return request.substring(0, request.indexOf(postfix));
        } else {
            return request;
        }

    }

    @And("^the operations in the bundled request message will be executed from top to bottom$")
    public void theRequestsInTheBundledRequestMessageWillBeExecutedFromTopToBottom(final Map<String, String> settings) throws Throwable {
        PROPERTIES_MAP.put(DEVICE_IDENTIFICATION_LABEL, getString(settings, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
        PROPERTIES_MAP.put(CORRELATION_UID_LABEL, ScenarioContext.Current().get("CorrelationUid").toString());
        PROPERTIES_MAP.put(MAX_TIME, "180000");

        this.requestRunner(TestStepStatus.OK, PROPERTIES_MAP, TEST_CASE_NAME_GETRESPONSE_REQUEST, TEST_CASE_XML, TEST_SUITE_XML);

        LOGGER.debug("check if responses are in the correct order:");
        final NodeList nodeList = this.runXpathResult.getNodeList(this.response, "//AllResponses/*");
        for (int nodeId = 0; nodeId < nodeList.getLength(); nodeId++) {
            final String responseAction = this.removeNamespace(nodeList.item(nodeId).getNodeName());
            final String matchingResponse = REQUEST_RESPONSE_PAIRS.get(REQUEST_ACTIONS.get(nodeId));
            assertEquals(matchingResponse, responseAction);
        }
    }

    @Then("^a bundled response message will contain the response from all the operations$")
    public void aBundledResponseMessageWillContainTheResponseFromAllTheRequests() throws Throwable {
        LOGGER.debug("check if we get responses for all the requests");
        final NodeList nodeList = this.runXpathResult.getNodeList(this.response, "//AllResponses/*");

        assertEquals(REQUEST_ACTIONS.size(), nodeList.getLength());
    }

    private String removeNamespace(final String input) {
        return input.split(":")[1];
    }
}
