package engine.repository;

import engine.model.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends PagingAndSortingRepository<SolvedQuiz, Long> {

    @Query(value = "select sq from SolvedQuiz sq where sq.userId = :userId")
    Page<SolvedQuiz> findAllSolvedQuizzesByUser(Pageable pageable, long userId);
}
