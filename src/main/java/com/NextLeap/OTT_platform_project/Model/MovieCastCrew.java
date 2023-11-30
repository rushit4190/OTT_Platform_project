package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Entity
@Data
@Table(name = "movie_cast_crew", schema = "OTT_project", indexes = {@Index(name = "movie_id_index", columnList = "movie_id"), @Index(name = "cast_crew_index_id", columnList = "cast_crew_id")},
        uniqueConstraints = @UniqueConstraint(name = "unique_movie_cast_crew", columnNames = {"movie_id", "cast_crew_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class MovieCastCrew implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false, unique = false)
    @JsonIgnore
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    Movie movie;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cast_crew_id", referencedColumnName = "id", nullable = false, unique = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("id")
//    @JsonBackReference
    CastCrew movieCastCrew;
}
