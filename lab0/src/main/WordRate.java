package main;

public class WordRate {
    private final String word;
    private int rate;

    public WordRate(String word) {
        this.word = word;
        this.rate = 1;
    }

    public String getWord() {
        return word;
    }

    public int getRate() {
        return rate;
    }

    public void increment() {
        rate++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        WordRate w = (WordRate) o;
        return word.equals((w).word);
    }

    @Override
    public int hashCode () {
        return this.word.hashCode();
    }
}
