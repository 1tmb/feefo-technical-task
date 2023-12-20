package com.feefo.normaliser;

import org.junit.jupiter.api.Assertions;

public class JobRoleNormaliserTest {

    private JobRoleNormaliser unitUnderTest;

    public void setUp() {
        unitUnderTest = new JobRoleNormaliser();
    }

    /**
     * Cases:
     *
     *  Blank
     *  No match (score is zero)
     *  Strong match (score is one)
     *  Partial but preferred match (score is between zero and one and is not 0.5)
     *  Score is split down the middle (say someone is called a Software Accountant) - this is ambigious and therefore not normalisable, this should score zero
     */
}
