/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.domain.core.valueobjects.smartmetering;

import java.io.Serializable;

public class SmsDetails implements Serializable {

    private static final long serialVersionUID = -95100923844792133L;

    private final String deviceIdentification;
    private final Long smsMsgId;
    private String status;
    private String smsMsgAttemptStatus;
    private String msgType;

    public SmsDetails(final String deviceIdentification, final Long smsMsgId, final String status,
            final String smsMsgAttemptStatus, final String msgType) {
        this.deviceIdentification = deviceIdentification;
        this.smsMsgId = smsMsgId;
        this.status = status;
        this.smsMsgAttemptStatus = smsMsgAttemptStatus;
        this.msgType = msgType;
    }

    public String getDeviceIdentification() {
        return this.deviceIdentification;
    }

    public Long getSmsMsgId() {
        return this.smsMsgId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getSmsMsgAttemptStatus() {
        return this.smsMsgAttemptStatus;
    }

    public String getMsgType() {
        return this.msgType;
    }

}
