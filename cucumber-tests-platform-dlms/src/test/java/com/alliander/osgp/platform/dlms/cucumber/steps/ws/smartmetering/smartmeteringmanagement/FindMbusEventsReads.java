/**
 * Copyright 2016 Smart Society Services B.V.
 */
package com.alliander.osgp.platform.dlms.cucumber.steps.ws.smartmetering.smartmeteringmanagement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alliander.osgp.adapter.ws.schema.smartmetering.management.EventLogCategory;
import com.alliander.osgp.adapter.ws.schema.smartmetering.management.EventType;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FindMbusEventsReads extends AbstractFindEventsReads {
    private static final List<EventType> allowed = Collections.unmodifiableList(Arrays.asList(new EventType[] {
            EventType.EVENTLOG_CLEARED, EventType.COMMUNICATION_ERROR_M_BUS_CHANNEL_1,
            EventType.COMMUNICATION_ERROR_M_BUS_CHANNEL_2, EventType.COMMUNICATION_ERROR_M_BUS_CHANNEL_3,
            EventType.COMMUNICATION_ERROR_M_BUS_CHANNEL_4, EventType.COMMUNICATION_OK_M_BUS_CHANNEL_1,
            EventType.COMMUNICATION_OK_M_BUS_CHANNEL_2, EventType.COMMUNICATION_OK_M_BUS_CHANNEL_3,
            EventType.COMMUNICATION_OK_M_BUS_CHANNEL_4, EventType.REPLACE_BATTERY_M_BUS_CHANNEL_1,
            EventType.REPLACE_BATTERY_M_BUS_CHANNEL_2, EventType.REPLACE_BATTERY_M_BUS_CHANNEL_3,
            EventType.REPLACE_BATTERY_M_BUS_CHANNEL_4, EventType.FRAUD_ATTEMPT_M_BUS_CHANNEL_1,
            EventType.FRAUD_ATTEMPT_M_BUS_CHANNEL_2, EventType.FRAUD_ATTEMPT_M_BUS_CHANNEL_3,
            EventType.FRAUD_ATTEMPT_M_BUS_CHANNEL_4, EventType.CLOCK_ADJUSTED_M_BUS_CHANNEL_1,
            EventType.CLOCK_ADJUSTED_M_BUS_CHANNEL_2, EventType.CLOCK_ADJUSTED_M_BUS_CHANNEL_3,
            EventType.CLOCK_ADJUSTED_M_BUS_CHANNEL_4, EventType.NEW_M_BUS_DEVICE_DISCOVERED_CHANNEL_1,
            EventType.NEW_M_BUS_DEVICE_DISCOVERED_CHANNEL_2, EventType.NEW_M_BUS_DEVICE_DISCOVERED_CHANNEL_3,
            EventType.NEW_M_BUS_DEVICE_DISCOVERED_CHANNEL_4, EventType.PERMANENT_ERROR_FROM_M_BUS_DEVICE_CHANNEL_1,
            EventType.PERMANENT_ERROR_FROM_M_BUS_DEVICE_CHANNEL_2,
            EventType.PERMANENT_ERROR_FROM_M_BUS_DEVICE_CHANNEL_3,
            EventType.PERMANENT_ERROR_FROM_M_BUS_DEVICE_CHANNEL_4 }));

    @Override
    protected String getEventLogCategory() {
        final String category = EventLogCategory.M_BUS_EVENT_LOG.name();
        return category.substring(0, category.lastIndexOf('_'));
    }

    @When("^receiving a find mbus events request$")
    @Override
    public void receivingAFindStandardEventsRequest(final Map<String, String> requestData) throws Throwable {
        super.receivingAFindStandardEventsRequest(requestData);
    }

    @Then("^mbus events should be returned$")
    @Override
    public void eventsShouldBeReturned(final Map<String, String> settings) throws Throwable {
        super.eventsShouldBeReturned(settings);
    }

    @Override
    protected List<EventType> getAllowedEventTypes() {
        return allowed;
    }
}
