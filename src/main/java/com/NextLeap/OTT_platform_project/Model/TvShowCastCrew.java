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
@Table(name = "tv_show_cast_crew", schema = "OTT_project", indexes = {@Index(name = "tv_show_id_index", columnList = "tv_show_id"), @Index(name = "cast_crew_index_id", columnList = "cast_crew_id")},
        uniqueConstraints = @UniqueConstraint(name = "unique_tv_show_cast_crew", columnNames = {"tv_show_id", "cast_crew_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class TvShowCastCrew implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tv_show_id", referencedColumnName = "id", nullable = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    TvShow tvShow;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cast_crew_id", referencedColumnName = "id", nullable = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
    @JsonIgnoreProperties("id")
    CastCrew tvShowCastCrew;
}
