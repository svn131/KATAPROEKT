package com.javamentor.qa.platform.models.entity;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(generator = "Comment_seq")
    private Long id;


    @NotNull
    @Column
    private String text;

    @Enumerated
    @NotNull
    @Column//(columnDefinition = "smallint")
    private CommentType commentType;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @Column(name = "last_redaction_date")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public Comment(CommentType commentType) {
        this.commentType = commentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(persistDateTime, comment.persistDateTime) &&
                Objects.equals(lastUpdateDateTime, comment.lastUpdateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, persistDateTime, lastUpdateDateTime);
    }
}

