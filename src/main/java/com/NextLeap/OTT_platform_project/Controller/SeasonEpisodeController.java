package com.NextLeap.OTT_platform_project.Controller;


import com.NextLeap.OTT_platform_project.Dto.SeasonInfoDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowInfoDto;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.EpisodeInfo;
import com.NextLeap.OTT_platform_project.Model.Movie;
import com.NextLeap.OTT_platform_project.Model.SeasonInfo;
import com.NextLeap.OTT_platform_project.Model.TvShow;
import com.NextLeap.OTT_platform_project.Service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ottplatform/v1/")
public class SeasonEpisodeController {

    public final TvShowService tvShowService;

    @Autowired
    public SeasonEpisodeController(TvShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @PostMapping("/addseasoninfo/")
    public ResponseEntity<?> uploadSeasonInfo(@RequestBody Map<String, Object> requestBody){

        try {
            SeasonInfo seasonInfoAdded = tvShowService.saveSeasonInfo(requestBody);
            return ResponseEntity.ok().body(seasonInfoAdded);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addepisodeinfo/")
    public ResponseEntity<?> uploadEpisodeInfo(@RequestBody Map<String, Object> requestBody){

        try {
            EpisodeInfo episodeInfo = tvShowService.saveEpisodeInfo(requestBody);
            return ResponseEntity.ok().body(episodeInfo);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/addepisodeinfo/episodeVideo")
    public ResponseEntity<?> uploadTvShowEpisodeLanguageVideoData(@RequestParam("video") MultipartFile videoFile, @RequestParam String episodeId, @RequestParam String language){

        try{
            EpisodeInfo episodeInfoToEdit = tvShowService.uploadTvShowEpisodeLanguageVideo(videoFile, episodeId, language);
            return ResponseEntity.ok().body(episodeInfoToEdit);
        } catch (IOException | NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tvshows/seasons/{seasonInfoId}")
    public ResponseEntity<?> getSeasonInfoDtoById( @PathVariable String seasonInfoId){
        try {
            SeasonInfoDto seasonInfoDto = tvShowService.fetchSeasonInfoDtoById(seasonInfoId);
            return ResponseEntity.ok().body(seasonInfoDto);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tvshows/seasons/episodes/{episodeInfoId}")
    public ResponseEntity<?> getEpisodeInfoById( @PathVariable String episodeInfoId){
        try {
            EpisodeInfo episodeInfo = tvShowService.fetchEpisodeInfoById(episodeInfoId);
            return ResponseEntity.ok().body(episodeInfo);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
