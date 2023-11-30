package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "episode_video_data", schema = "OTT_project", indexes = @Index(name = "video_data_index", columnList = "episode_id, language"))
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeVideoData {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "episode_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    EpisodeInfo episode;

    private String language;

    @Lob
    @Column(name = "video_data",columnDefinition = "LONGBLOB")
    private byte[] videoData;
}
