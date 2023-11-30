package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "season_info", schema = "OTT_project",indexes = {@Index(name = "tv_show_id_index", columnList = "tv_show_id")},
        uniqueConstraints = @UniqueConstraint(name = "unique_tv_show_season_info", columnNames = {"tv_show_id", "id"}))
@NoArgsConstructor
@AllArgsConstructor
public class SeasonInfo {
    @Id
    @UuidGenerator
    private String Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;

    private Double rating;

    @Lob
    private byte[] poster;

    private Integer seasonIndex;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tv_show_id", referencedColumnName = "id", nullable = false, unique = false)
//    //    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    TvShow tvShow;

    @Column(name = "tv_show_id")
    private String tvShowId;

//    @OneToMany(mappedBy = "seasonInfo", cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<EpisodeInfo> episodes;
}
