package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "tv_show_genres", schema = "OTT_project",  indexes = {@Index(name = "tv_show_id_index", columnList = "tv_show_id"), @Index(name = "genre_index", columnList = "genre")},
        uniqueConstraints = @UniqueConstraint(name = "unique_tv_show_genre", columnNames = {"tv_show_id", "genre"}))
@NoArgsConstructor
@AllArgsConstructor
public class TvShowGenre {
    @Id
    @UuidGenerator
    private String Id;

    private String genre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tv_show_id", referencedColumnName = "id", nullable = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    TvShow tvShow;
}
