package engine.controller;

import engine.model.Answer;
import engine.model.Quiz;
import engine.model.Response;
import engine.model.SolvedQuiz;
import engine.service.QuizService;
import engine.service.SolvedQuizService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;
    private final SolvedQuizService solvedQuizService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService, SolvedQuizService solvedQuizService) {
        this.quizService = quizService;
        this.userService = userService;
        this.solvedQuizService = solvedQuizService;
    }

    @PostMapping(consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.add(quiz);
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable long id) {
        return quizService.getById(id);
    }

    @GetMapping
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {

        return quizService.getAllQuizzes(page, pageSize);
    }

    @PostMapping(value = "/{id}/solve", consumes = "application/json")
    public Response solveQuiz(@PathVariable long id, @RequestBody Answer answer) {
        Quiz quiz = quizService.getById(id);
        final boolean quizSolved = quizService.isRightAnswer(quiz, answer);
        if (quizSolved) solvedQuizService.saveSolvedQuiz(id);

        return quizSolved
                ? new Response(true, "Congratulations, you're right!")
                : new Response(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable long id) {
        Quiz quiz = quizService.getById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        if (!userService.getUserFromContext().getQuizzes().contains(quiz)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        quizService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public Page<SolvedQuiz> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "completedAt") String sortBy) {

        return userService.findAllSolvedQuizzes(page, pageSize, sortBy);
    }

    @GetMapping("/completed/all")
    public List<SolvedQuiz> getAllSolvedQuizzes() {
        return solvedQuizService.findAllSolvedQuizzes();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid quest")
    public void UsernameNotFoundExceptionHandler(Exception e) {
    }
}
