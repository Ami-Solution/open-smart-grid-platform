/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.domain.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.opensmartgridplatform.domain.core.valueobjects.TimePeriod;

public class TimePeriodValidator implements ConstraintValidator<TimePeriodConstraints, TimePeriod> {

    private static final String CHECK_STARTTIME_MESSAGE = "startTime may not be after endTime";

    @Override
    public void initialize(final TimePeriodConstraints constraintAnnotation) {
        // Empty Method
    }

    @Override
    public boolean isValid(final TimePeriod timePeriod, final ConstraintValidatorContext context) {
        if (timePeriod == null) {
            return true;
        }

        final ValidatorHelper helper = new ValidatorHelper();

        if (timePeriod.getStartTime().isAfter(timePeriod.getEndTime())) {
            helper.addMessage(CHECK_STARTTIME_MESSAGE);
        }

        return helper.isValid(context);
    }
}
