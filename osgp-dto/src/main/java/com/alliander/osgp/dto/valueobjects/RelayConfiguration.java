/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.dto.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelayConfiguration implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -108654955320491314L;

    private List<RelayMap> relayMap;

    public RelayConfiguration(final List<RelayMap> relayMap) {
        this.relayMap = new ArrayList<>(relayMap);
    }

    public List<RelayMap> getRelayMap() {
        return this.relayMap;
    }
}
