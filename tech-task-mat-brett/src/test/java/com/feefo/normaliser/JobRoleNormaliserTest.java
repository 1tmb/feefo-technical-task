package com.feefo.normaliser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JobRoleNormaliserTest {

    private JobRoleNormaliser unitUnderTest;
    private final String SOFTWARE_ENGINEER = "Software engineer";
    private final String QUANTITY_SURVEYOR = "Quantity surveyor";
    private final String ACCOUNTANT = "Accountant";

    @BeforeEach
    public void setUp() {
        final Set<String> normalisedJobRoles = Set.of(SOFTWARE_ENGINEER, QUANTITY_SURVEYOR, ACCOUNTANT);
        unitUnderTest = new JobRoleNormaliser(normalisedJobRoles);
    }


    @Test
    public void testConstructorValidationWhenNormalisedValueSetIsNull() {
        // GIVEN a poorly initialised normaliser with a null set of normalised roles
        // WHEN the normaliser is executed
        // THEN an IllegalArgumentException is thrown
        Assertions.assertThatThrownBy(() -> new JobRoleNormaliser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The normalised text data set may not be empty");
    }

    @Test
    public void testConstructorValidationWhenNormalisedValueSetIsEmpty() {
        // GIVEN a poorly initialised normaliser with an empty set of normalised roles
        // WHEN the normaliser is executed
        // THEN an IllegalArgumentException is thrown
        Assertions.assertThatThrownBy(() -> new JobRoleNormaliser(Collections.EMPTY_SET))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The normalised text data set may not be empty");
    }

    @Test
    public void testConstructorValidationWhenNormalisedValueSetContainsBlanks() {
        // GIVEN a poorly initialised normaliser with a set of blank normalised roles
        // WHEN the normaliser is executed
        // THEN an IllegalArgumentException is thrownS
        Set<String> poorDataSet = new HashSet<>();
        poorDataSet.add(null);
        poorDataSet.add("");
        poorDataSet.add(" ");
        Assertions.assertThatThrownBy(() -> new JobRoleNormaliser(poorDataSet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The normalised text data set may not be empty");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "\t\n\r\f"})
    public void testEmptyOptionalReturnedWhenInputIsBlank(final String rawJobRole)
    {
        // GIVEN blank raw input
        // WHEN the normaliser is executed
        // THEN no suggestion is returned
        Assertions.assertThat(unitUnderTest.normalise(rawJobRole)).isEmpty();
    }

    /**
     * No match (score is zero).
     */
    @Test
    public void testEmptyOptionalReturnedWhenNormalisationScoreIsZero() {
        // GIVEN some normalised job roles
        // AND a raw job role which doesn't match any of them
        final String rawJobRole = "QA";
        // WHEN the normaliser is executed
        // THEN no suggestion is returned
        Assertions.assertThat(unitUnderTest.normalise(rawJobRole)).isEmpty();
    }

    /**
     * Score is split down the middle (say someone is called a Software Accountant) - this is ambiguous and therefore not normalisable, this should score zero.
     */
    @Test
    public void testEmptyOptionalReturnedWhenNormalisationScoreIsHung() {
        // GIVEN some normalised job roles
        // AND a raw job role which equally matches two of them
        final String rawJobRole = "Software Accountant";
        // WHEN the normaliser is executed
        // THEN no suggestion is returned
        Assertions.assertThat(unitUnderTest.normalise(rawJobRole)).isEmpty();
    }

    /**
     * Strong match.
     * @param rawJobRole
     * @param expectedNormalisedJobRole
     */
    @ParameterizedTest
    @CsvSource({
            "Java engineer, Software engineer",
            "C# engineer, Software engineer",
            "Accountant, Accountant",
            "Chief Accountant, Accountant"
    })
    public void testNormalise(final String rawJobRole, final String expectedNormalisedJobRole) {
        // GIVEN some normalised job roles
        // AND a raw job role which is a strong score for a normalised option
        // WHEN the normaliser is executed
        // THEN the expected suggestion is returned
        Assertions.assertThat(unitUnderTest.normalise(rawJobRole)).isPresent();
        Assertions.assertThat(unitUnderTest.normalise(rawJobRole)).get().isEqualTo(expectedNormalisedJobRole);
    }
}
