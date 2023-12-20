package com.feefo.ranking;

/**
 * Contact for a container type which can be used for ranking the contained type {@code T}.
 * @param <T> Type
 */
public interface ScoredType<T> {

    T getType();
    int getScore();
}
