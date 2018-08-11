package com.airline.dto.validation;

import com.airline.model.dto.FlightDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.List;


public class CrossFiledValidator implements ConstraintValidator<CrossFieldValidation, FlightDTO>{

    private static final Logger LOGGER = LoggerFactory.getLogger(CrossFiledValidator.class);

    @Override
    public void initialize(final CrossFieldValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(final FlightDTO flightDTO, final ConstraintValidatorContext context)
    {
        try {
            List<Date> dates = flightDTO.getDates();
            List<String> periods = flightDTO.getSchedule().getPeriods();

            if (dates.isEmpty() && !periods.isEmpty() ||
                    !dates.isEmpty() && periods.isEmpty()) return true;

        }
        catch (final Exception ignore) {
            // ignore
        }
        return false;
    }
}
