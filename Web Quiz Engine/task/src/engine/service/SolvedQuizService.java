package engine.service;

import engine.model.SolvedQuiz;
import engine.repository.SolvedQuizRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolvedQuizService {

    private final SolvedQuizRepository solvedQuizRepository;
    private final UserService userService;

    public SolvedQuizService(SolvedQuizRepository solvedQuizRepository, UserService userService) {
        this.solvedQuizRepository = solvedQuizRepository;
        this.userService = userService;
    }

    public void saveSolvedQuiz(Long quizId) {
        SolvedQuiz quiz = new SolvedQuiz();
        quiz.setQuizId(quizId);
        quiz.setCompletedAt(LocalDateTime.now());
        quiz.setUserId(userService.getUserFromContext().getId());
        solvedQuizRepository.save(quiz);
    }

//    public Page<SolvedQuiz> findAllSolvedQuizzesByUser(User user, Pageable paging) {
//        return solvedQuizRepository.findAllSolvedQuizzesByUser(paging, user.getId());
//    }

    public List<SolvedQuiz> findAllSolvedQuizzes() {
        return (List<SolvedQuiz>) solvedQuizRepository.findAll();
    }
}
