package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


@Entity
@Data
@Table(name = "episode_info", schema = "OTT_project", indexes = @Index(name = "season_info_index", columnList = "season_info_id"))
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeInfo {
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

    private Long episodeDuration; //Store duration of episode in minutes
    private Double rating;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] poster;


    private Integer episodeIndex;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "season_info_id", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
//    //    @OnDelete(action = OnDeleteAction.CASCADE)
//    SeasonInfo seasonInfo;
    @Column(name = "season_info_id", nullable = false)
    private String seasonInfoId;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("id")
    private List<EpisodeVideoData> episodeVideoDataSet;
}
