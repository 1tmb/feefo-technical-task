package com.feefo.normaliser;

import java.util.List;
import java.util.Optional;

public class JobRoleNormaliser implements Normaliser<String> {

    private final List<String> normalisedOptions;
    public JobRoleNormaliser(final List<String> normalisedOptions) {
        this.normalisedOptions = normalisedOptions;
    }

    @Override
    public Optional<String> normalise(final String t) {
        if(null == t || t.isBlank()) {
            return Optional.empty();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
