package com.NextLeap.OTT_platform_project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDto {
    private String Id;
    private String title;
    private String description;
    private Long episodeDuration;
    private Integer episodeIndex;
}
