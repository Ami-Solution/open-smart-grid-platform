/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.domain.core.valueobjects;

public enum DeviceFunction {
    START_SELF_TEST,
    STOP_SELF_TEST,
    SET_LIGHT,
    GET_DEVICE_AUTHORIZATION,
    SET_EVENT_NOTIFICATIONS,
    SET_DEVICE_AUTHORIZATION,
    GET_EVENT_NOTIFICATIONS,
    UPDATE_FIRMWARE,
    GET_FIRMWARE_VERSION,
    SET_TARIFF_SCHEDULE,
    SET_LIGHT_SCHEDULE,
    SET_CONFIGURATION,
    GET_CONFIGURATION,
    GET_STATUS,
    GET_LIGHT_STATUS,
    GET_TARIFF_STATUS,
    REMOVE_DEVICE,
    GET_POWER_USAGE_HISTORY,
    RESUME_SCHEDULE,
    SET_REBOOT,
    SET_TRANSITION,
    UPDATE_KEY,
    REVOKE_KEY,
    FIND_SCHEDULED_TASKS,
    REGISTER_DEVICE,
    ADD_EVENT_NOTIFICATION,
    ADD_METER,
    FIND_EVENTS,
    REQUEST_PERIODIC_METER_DATA,
    SYNCHRONIZE_TIME,
    SET_SPECIAL_DAYS,
    SET_ALARM_NOTIFICATIONS,
    SET_CONFIGURATION_OBJECT,
    SET_ADMINISTRATIVE_STATUS,
    GET_ADMINISTRATIVE_STATUS,
    SET_ACTIVITY_CALENDAR,
    REQUEST_ACTUAL_METER_DATA,
    READ_ALARM_REGISTER,
    PUSH_NOTIFICATION_ALARM,
    PUSH_NOTIFICATION_SMS,
    SET_ENCRYPTION_KEY_EXCHANGE_ON_G_METER,
    REPLACE_KEYS,
    SET_PUSH_SETUP_ALARM,
    SET_PUSH_SETUP_SMS,
    GET_ALL_ATTRIBUTE_VALUES,
    GET_SPECIFIC_ATTRIBUTE_VALUE,
    SWITCH_CONFIGURATION_BANK,
    SWITCH_FIRMWARE,
    UPDATE_DEVICE_SSL_CERTIFICATION,
    SET_DEVICE_VERIFICATION_KEY,
    HANDLE_BUNDLED_ACTIONS,
    GET_ASSOCIATION_LN_OBJECTS,
    COUPLE_MBUS_DEVICE,
    DE_COUPLE_MBUS_DEVICE,
    GET_DATA,
    SET_DATA,
    ENABLE_DEBUGGING,
    DISABLE_DEBUGGING,
    GET_MESSAGES,
    SET_DEVICE_ALIASES,
    GET_PROFILE_GENERIC_DATA,
    SET_CLOCK_CONFIGURATION,
    GET_CONFIGURATION_OBJECT,
    GET_POWER_QUALITY_VALUES,
    GET_POWER_QUALITY_VALUES_PERIODIC,
    GET_DEVICE_MODEL,
    GET_HEALTH_STATUS,
    GENERATE_AND_REPLACE_KEYS,
    GET_FIRMWARE_FILE,
    SET_LIGHT_MEASUREMENT_DEVICE,
    GET_LIGHT_SENSOR_STATUS,
    SET_DEVICE_LIFECYCLE_STATUS,
    SET_DEVICE_LIFECYCLE_STATUS_BY_CHANNEL,
    CONFIGURE_DEFINABLE_LOAD_PROFILE,
    SET_MBUS_USER_KEY_BY_CHANNEL,
    COUPLE_MBUS_DEVICE_BY_CHANNEL,
    GET_MBUS_ENCRYPTION_KEY_STATUS,
    SET_DEVICE_COMMUNICATION_SETTINGS,
    CLEAR_ALARM_REGISTER,
    GET_MBUS_ENCRYPTION_KEY_STATUS_BY_CHANNEL,
    SCAN_MBUS_CHANNELS,
    UPDATE_DEVICE_CDMA_SETTINGS;
}
