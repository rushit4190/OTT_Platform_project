package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "movie_genres", schema = "OTT_project", indexes = {@Index(name = "genre_index", columnList = "genre"), @Index(name = "movie_id_index", columnList = "movie_id")},
        uniqueConstraints = @UniqueConstraint(name = "unique_movie_genre", columnNames = {"movie_id", "genre"}))
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenre {
    @Id
    @UuidGenerator
    private String Id;

    private String genre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Movie movie;
}
