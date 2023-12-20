package com.feefo.ranking;

public class ScoredJobRole implements ScoredType<String> {

    private String jobRole;
    private int score;

    public ScoredJobRole(final String jobRole, final int score)
    {
        this.jobRole = jobRole;
        this.score = score;
    }
    @Override
    public String getType() {
        return jobRole;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("Job role: %s - Score: %s", jobRole, score);
    }
}
