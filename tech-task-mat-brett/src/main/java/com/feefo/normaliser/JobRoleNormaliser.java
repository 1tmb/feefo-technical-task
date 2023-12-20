package com.feefo.normaliser;

import com.feefo.ranking.ScoredJobRole;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tokenising normaliser which compares some raw input to a dataset of normalised string terms, and decides which normalised string term is the best match.
 * One scoring point is awarded per raw token which appears in each normalised data item.
 * If a single normalised data item is deemed to be the strongest match, that normalised item is returned.
 * Otherwise, no normalised data item is returned.
 */
public class JobRoleNormaliser implements Normaliser<String> {

    private final Set<String> normalisedOptions;
    public JobRoleNormaliser(final Set<String> normalisedOptions) {
        // TODO - constructor tests

        this.normalisedOptions = Optional.ofNullable(normalisedOptions)
                .map(options -> options.stream()
                        .filter(((Predicate<String>) String::isBlank).negate())
                        .collect(Collectors.toSet())
                )
                .orElse(Collections.emptySet());

        if (this.normalisedOptions.isEmpty()) {
            throw new IllegalArgumentException("The normalised text data set may not be empty");
        }
    }

    @Override
    public Optional<String> normalise(final String rawInput) {
        if (null == rawInput || rawInput.isBlank()) {
            return Optional.empty();
        }

        final List<ScoredJobRole> scoredJobRoles = new ArrayList<>();

        final List<String> tokenisedRawInput = getStringAsTokens(rawInput);

        for (final String normalisedOption: normalisedOptions) {
            int qualityScore = 0;
            for (final String rawToken: tokenisedRawInput) {
                if (normalisedOption.contains(rawToken)) {
                    qualityScore++;
                }
            }
            scoredJobRoles.add(new ScoredJobRole(normalisedOption, qualityScore));
        }

        final Optional<ScoredJobRole> highestScoringJobRole = scoredJobRoles.stream()
                .max(Comparator.comparing(ScoredJobRole::getScore));

        if (highestScoringJobRole.isEmpty() || highestScoringJobRole.get().getScore() == 0) {
            return Optional.empty();
        }

        return Optional.of(highestScoringJobRole.get().getType());
    }

    private List<String> getStringAsTokens(final String s) {
        return Arrays.stream(s.split(" "))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
