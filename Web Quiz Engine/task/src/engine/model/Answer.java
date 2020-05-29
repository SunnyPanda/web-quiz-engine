package engine.model;

import java.util.Arrays;

public class Answer {

    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answers=" + Arrays.toString(answer) +
                '}';
    }
}
