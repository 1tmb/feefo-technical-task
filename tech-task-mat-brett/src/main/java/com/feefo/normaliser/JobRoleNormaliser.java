package com.feefo.normaliser;

import com.feefo.ranking.ScoredJobRole;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        this.normalisedOptions = Optional.ofNullable(normalisedOptions)
                .map(options -> options.stream()
                        .filter(s -> s != null && !s.isBlank())
                        .collect(Collectors.toSet())
                )
                .orElse(Collections.emptySet());

        if (this.normalisedOptions.isEmpty()) {
            throw new IllegalArgumentException("The normalised text data set may not be empty");
        }
    }
    public Optional<String> normalise(final String rawInput) {
        if (null == rawInput || rawInput.isBlank()) {
            return Optional.empty();
        }

        final List<ScoredJobRole> scoredJobRoles = scoreRawInput(rawInput);

        final Optional<ScoredJobRole> highestScoringJobRole = findHighestScoringJobRole(scoredJobRoles);

        if (highestScoringJobRole.isEmpty() || highestScoringJobRole.get().getScore() == 0
                || countOccurrencesOfHighestScore(scoredJobRoles, highestScoringJobRole.get().getScore()) != 1) {
            return Optional.empty();
        }

        return Optional.of(highestScoringJobRole.get().getType());
    }

    private List<ScoredJobRole> scoreRawInput(final String rawInput) {
        final List<String> tokenisedRawInput = getStringAsTokens(rawInput);
        final List<ScoredJobRole> scoredJobRoles = new ArrayList<>();

        for (String normalisedOption : normalisedOptions) {
            int qualityScore = (int) tokenisedRawInput.stream()
                    .filter(normalisedOption::contains)
                    .count();
            scoredJobRoles.add(new ScoredJobRole(normalisedOption, qualityScore));
        }

        return scoredJobRoles;
    }

    private Optional<ScoredJobRole> findHighestScoringJobRole(final List<ScoredJobRole> scoredJobRoles) {
        return scoredJobRoles.stream()
                .max(Comparator.comparing(ScoredJobRole::getScore));
    }

    private int countOccurrencesOfHighestScore(final List<ScoredJobRole> scoredJobRoles, int highestScore) {
        return (int) scoredJobRoles.stream()
                .filter(scoredJobRole -> scoredJobRole.getScore() == highestScore)
                .count();
    }

    private List<String> getStringAsTokens(final String s) {
        return Arrays.stream(s.split(" "))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
