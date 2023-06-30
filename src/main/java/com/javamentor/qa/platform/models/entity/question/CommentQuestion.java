package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_question")
public class CommentQuestion implements Serializable {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Comment comment = new Comment(CommentType.QUESTION);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    public CommentQuestion(String text, User user) {
        comment.setText(text);
        comment.setUser(user);
    }

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.comment.getCommentType() != CommentType.QUESTION) {
            throw new ApiRequestException("У экземпляра Comment, связанного с CommentQuestion, " +
                    "поле commentType должно принимать значение CommentType.QUESTION");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        CommentQuestion commentQuestion = (CommentQuestion) o;
        return id != null &&
                id.equals(commentQuestion.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setText(String text) {
        comment.setText(text);
    }

    public void setUser(User user) {
        comment.setUser(user);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

