package com.NextLeap.OTT_platform_project.Dto;

import com.NextLeap.OTT_platform_project.Model.SeasonInfo;
import com.NextLeap.OTT_platform_project.Model.TvShow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TvShowInfoDto {
    private TvShow tvShow;
    private Integer totalSeasons;
    private List<SeasonInfo> seasonInfoList;
}
