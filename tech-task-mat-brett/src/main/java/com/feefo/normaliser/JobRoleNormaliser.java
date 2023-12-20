package com.feefo.normaliser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JobRoleNormaliser implements Normaliser<String> {

    private final Set<String> normalisedOptions;
    public JobRoleNormaliser(final Set<String> normalisedOptions) {
        // TODO - after initially solving the challenge - we could do with some validation to defend against empty lists and lists with blank entries!
        this.normalisedOptions = normalisedOptions;
    }

    @Override
    public Optional<String> normalise(final String t) {
        if (null == t || t.isBlank()) {
            return Optional.empty();
        }

//        // tokeneise the search string
//        // iterate over the dictionary and for each item:
//         - tokeneise the dictionary item
//                match the word - score 1 point if present
//                group the results by score
//                pop the highest scoring item
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
