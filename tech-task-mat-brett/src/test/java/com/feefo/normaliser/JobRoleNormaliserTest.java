package com.feefo.normaliser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class JobRoleNormaliserTest {

    private JobRoleNormaliser unitUnderTest;


    @BeforeEach
    public void setUp() {
        unitUnderTest = new JobRoleNormaliser();
    }

    /**
     * Cases:
     *  No match (score is zero)
     *  Strong match (score is one)
     *  Partial but preferred match (score is between zero and one and is not 0.5)
     *  Score is split down the middle (say someone is called a Software Accountant) - this is ambigious and therefore not normalisable, this should score zero
     */

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "\t\n\r\f"})
    public void testEmptyOptionalReturnedWhenInputIsBlank(final String unnormalisedJobRole)
    {
        // GIVEN a blank unnormalised input
        // WHEN the normaliser is executed
        // THEN no suggestion is returned
        Assertions.assertThat(unitUnderTest.normalise(unnormalisedJobRole)).isEmpty();
    }
}
