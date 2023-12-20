package com.feefo.normaliser;

import java.util.Optional;

public class JobRoleNormaliser implements Normaliser<String> {
    @Override
    public Optional<String> normalise(final String t) {
        if(null == t || t.isBlank()) {
            return Optional.empty();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
