package engine.service;

import engine.exceptions.QuizNotFoundException;
import engine.model.Answer;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserService userService;

    public QuizService(QuizRepository quizRepository, UserService userService) {
        this.quizRepository = quizRepository;
        this.userService = userService;
    }

    public Quiz getById(long id) {
        return quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        return quizRepository.findAll(paging);
    }

    public Quiz add(Quiz quiz) {
        if (quiz.getAnswer() == null) quiz.setAnswer(new int[] {});
        Quiz savedQuiz = quizRepository.save(quiz);

        User user = userService.getUserFromContext();
        user.getQuizzes().add(quiz);
        userService.saveUser(user);

        return savedQuiz;
    }

    public boolean isRightAnswer(Quiz quiz, Answer answer) {
        return Arrays.equals(quiz.getAnswer(), answer.getAnswer());
    }

    public void deleteById(long id) {

        quizRepository.deleteById(id);
    }
}
