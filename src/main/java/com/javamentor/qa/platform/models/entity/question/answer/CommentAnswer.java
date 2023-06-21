package com.javamentor.qa.platform.models.entity.question.answer;

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
@Table(name = "comment_answer")
public class CommentAnswer implements Serializable {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Comment comment = new Comment(CommentType.ANSWER);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public CommentAnswer(String text, User user) {
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
        if (this.comment.getCommentType() != CommentType.ANSWER) {
            throw new ApiRequestException("У экземпляра Comment, связанного с CommentAnswer, " +
                    "поле commentType должно принимать значение CommentType.ANSWER");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        CommentAnswer com = (CommentAnswer) o;
        return id != null &&
                id.equals(com.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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
