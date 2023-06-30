package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes_on_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuestion implements Serializable {

    @Id
    @GeneratedValue(generator = "VoteQuestion_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private VoteType vote;

    public VoteQuestion(User user, Question question, VoteType vote) {
        this.user = user;
        this.question = question;
        this.vote = vote;
    }

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (vote != VoteType.UP && vote != VoteType.DOWN) {
            throw new ConstrainException("В сущности VoteQuestion допускается передача значения в поле VoteTypeQ только VoteTypeQ.UP или VoteTypeQ.DOWN");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        VoteQuestion voteQuestion = (VoteQuestion) o;
        return id != null &&
                id.equals(voteQuestion.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

