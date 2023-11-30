package com.NextLeap.OTT_platform_project.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TvShowDto extends ContentDto{

    private String Id;
    private String title;
    //Can also add poster field
}
