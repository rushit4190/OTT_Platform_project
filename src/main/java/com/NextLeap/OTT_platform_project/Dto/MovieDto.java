package com.NextLeap.OTT_platform_project.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDto extends ContentDto{

    private String Id;
    private String title;
    // Can also add poster field
}
