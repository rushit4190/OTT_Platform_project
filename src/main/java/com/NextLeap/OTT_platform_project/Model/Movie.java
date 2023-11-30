package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "movies", schema = "OTT_project", indexes = @Index(name = "title_index", columnList = "title", unique = true))
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {
    @Id
    @UuidGenerator
    private String Id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;

    private Long movieDuration;  //Store movie duration in minutes

    private Double rating;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] poster;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] trailer;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("id")
    private List<MovieLanguage> languages;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("id")
    private List<MovieGenre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonManagedReference
    @JsonIgnoreProperties("id")
    private List<MovieCastCrew> movieCastCrews;


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MovieVideoData> movieVideoDataSet;
}
