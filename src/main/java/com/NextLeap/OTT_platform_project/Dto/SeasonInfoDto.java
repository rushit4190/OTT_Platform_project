package com.NextLeap.OTT_platform_project.Dto;

import com.NextLeap.OTT_platform_project.Model.EpisodeInfo;
import com.NextLeap.OTT_platform_project.Model.SeasonInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonInfoDto {
    private SeasonInfo seasonInfo;
    private Integer totalEpisodes;
    private List<EpisodeDto> episodeDtoList;

}
