package engine.service;

import engine.model.Role;
import engine.model.SolvedQuiz;
import engine.model.User;
import engine.repository.SolvedQuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    @Autowired
    public UserService(UserRepository userRepository, SolvedQuizRepository solvedQuizRepository) {
        this.userRepository = userRepository;
        this.solvedQuizRepository = solvedQuizRepository;
//        this.solvedQuizService = solvedQuizService;
    }

    public void registerUser(User user) {
        user.setUsername(user.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Page<SolvedQuiz> findAllSolvedQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        User user = getUserFromContext();

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
//        Pageable paging = PageRequest.of(pageNo, pageSize);
        return solvedQuizRepository.findAllSolvedQuizzesByUser(paging, user.getId());
    }

    public User getUserFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return getUserByUsername(username);
    }

}
