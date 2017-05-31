/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.dto.valueobjects.smartmetering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This Dto is used to transfer information from the the protocol adapter back
 * to the core. It contains the original request values, plus the values it
 * found on the e-meter in the smart_meter table.
 *
 *
 */
public class MbusChannelElementsResponseDto implements Serializable {

    private static final long serialVersionUID = 5377631203726277889L;

    /**
     * This contains the channel on which the mbus-device is connected. It is
     * null if NO channel is found that matches the original values from the
     * request dto see also isChannelFound()
     */
    private final Integer channel;

    /**
     * this contains the values read from the e-meter until a match is found. if
     * no channel is found, it will contain 4 entries
     */
    private final List<ChannelElementValues> channelElements;

    /**
     * the original values from the request dto, which are retrieved from
     * smartmeter database table.
     */
    private final MbusChannelElementsDto mbusChannelElementsDto;

    MbusChannelElementsResponseDto(final MbusChannelElementsDto mbusChannelElementsDto, final Integer channel,
            final List<ChannelElementValues> channelElements) {
        super();
        this.mbusChannelElementsDto = mbusChannelElementsDto;
        this.channel = channel;
        this.channelElements = channelElements;
    }

    public boolean isChannelFound() {
        return this.channel != null;
    }

    public Integer getChannel() {
        return this.channel;
    }

    public List<ChannelElementValues> getChannelElements() {
        return new ArrayList<>(this.channelElements);
    }

    public MbusChannelElementsDto getMbusChannelElementsDto() {
        return this.mbusChannelElementsDto;
    }

}