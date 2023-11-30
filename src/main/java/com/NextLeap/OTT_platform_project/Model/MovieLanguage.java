package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "movie_languages", schema = "OTT_project", indexes = { @Index(name = "language_index", columnList = "language"), @Index(name = "movie_id_index", columnList = "movie_id")},
        uniqueConstraints = @UniqueConstraint(name = "unique_movie_language", columnNames = {"movie_id", "language"}))
@NoArgsConstructor
@AllArgsConstructor
public class MovieLanguage {
    @Id
    @UuidGenerator
    private String Id;

    private String language;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    Movie movie;
}
