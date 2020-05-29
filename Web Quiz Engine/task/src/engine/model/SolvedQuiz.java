package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SolvedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long entityID;

    private long id;
    private LocalDateTime completedAt;

//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    @JsonIgnore
    private long userId;

    public long getEntityID() {
        return entityID;
    }

    public void setEntityID(long id) {
        this.entityID = id;
    }

    public long getId() {
        return id;
    }

    public void setQuizId(long quizId) {
        this.id = quizId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SolvedQuiz{" +
                "id=" + entityID +
                ", id=" + id +
                ", completedAt=" + completedAt +
                ", userId=" + userId +
                '}';
    }
}
