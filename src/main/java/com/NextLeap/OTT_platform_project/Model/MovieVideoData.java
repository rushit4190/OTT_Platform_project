package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "movie_video_data", schema = "OTT_project", indexes = @Index(name = "video_data_index", columnList = "movie_id, language"))
@NoArgsConstructor
@AllArgsConstructor
public class MovieVideoData {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Movie movie;

    private String language;

    @Lob
    @Column(name = "video_data",columnDefinition = "LONGBLOB")
    private byte[] videoData;
}
