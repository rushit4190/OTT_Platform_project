package com.NextLeap.OTT_platform_project.Controller;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowInfoDto;
import com.NextLeap.OTT_platform_project.Enum.GenreEnum;
import com.NextLeap.OTT_platform_project.Enum.LanguageEnum;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.Movie;
import com.NextLeap.OTT_platform_project.Model.TvShow;
import com.NextLeap.OTT_platform_project.Service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ottplatform/v1/")
public class TvShowController {

    public final TvShowService tvShowService;

    @Autowired
    public TvShowController(TvShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @PostMapping("/addtvshow/")
    public ResponseEntity<?> uploadTvShow(@RequestBody Map<String, Object> requestBody){

        try {
            TvShow tvShowAdded = tvShowService.saveTvShow(requestBody);
            return ResponseEntity.ok().body(tvShowAdded);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addtvshow/poster")
    public ResponseEntity<?> uploadTvShowPosterData(@RequestParam("poster") MultipartFile posterFile, @RequestParam String title){

        try{
            TvShow tvShowToEdit = tvShowService.uploadTvShowPoster(posterFile, title);
            return ResponseEntity.ok().body(tvShowToEdit);
        } catch (IOException | NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addtvshow/genre")
    public ResponseEntity<?> setTvShowGenreData(@RequestParam String genre, @RequestParam String title){

        try {
            TvShow tvShowToEdit = tvShowService.setTvShowGenre(genre, title);
            return ResponseEntity.ok().body(tvShowToEdit);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addtvshow/language")
    public ResponseEntity<?> setTvShowLanguageData(@RequestParam String language, @RequestParam String title){

        try {
            TvShow tvShowToEdit = tvShowService.setTvShowLanguage(language, title);
            return ResponseEntity.ok().body(tvShowToEdit);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addtvshow/tvShowCastCrew")
    public ResponseEntity<?> setTvShowCastCrewData(@RequestBody Map<String, Object> requestBody){

        try{
            TvShow tvShowToEdit = tvShowService.setTvShowCastCrew(requestBody);
            return ResponseEntity.ok().body(tvShowToEdit);
        } catch ( NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tvshows/language/{language}")
    public ResponseEntity<?> getTvShowDtosByLanguage(@PathVariable String language, @RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "5") Integer pageSize){

        List<TvShowDto> tvShowDtoListByLanguage = tvShowService.fetchTvShowDtoListByLanguage(language, pageNo, pageSize);
        return ResponseEntity.ok().body(tvShowDtoListByLanguage);
    }

    @GetMapping("/tvshows/genre/{genre}")
    public ResponseEntity<?> getTvShowDtosByGenre(@PathVariable String genre, @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "5") Integer pageSize){

        List<TvShowDto> tvShowDtoListByGenre = tvShowService.fetchTvShowDtoListByGenre(genre, pageNo, pageSize);
        return ResponseEntity.ok().body(tvShowDtoListByGenre);
    }

    @GetMapping("/tvshows/top")
    public ResponseEntity<?> getTvShowDtosOrderByRatingsDesc( @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize){

        List<TvShowDto> tvShowDtoListByRatings = tvShowService.fetchTvShowDtoListOrderByRatingDesc( pageNo, pageSize);
        return ResponseEntity.ok().body(tvShowDtoListByRatings);
    }
    @GetMapping("/tvshows/latest")
    public ResponseEntity<?> getTvShowDtosOrderByReleaseDateDesc( @RequestParam(defaultValue = "0") Integer pageNo,
                                                              @RequestParam(defaultValue = "5") Integer pageSize){

        List<TvShowDto> tvShowDtoListByReleaseDate = tvShowService.fetchTvShowDtoListOrderByReleaseDateDesc( pageNo, pageSize);
        return ResponseEntity.ok().body(tvShowDtoListByReleaseDate);
    }

    @GetMapping("/tvshows/title/{tvShowTitle}")
    public ResponseEntity<?> getTvShowInfoDtoByTitle( @PathVariable String tvShowTitle){
        try {
            TvShowInfoDto tvShowInfoDto = tvShowService.fetchTvShowInfoDtoByTitle(tvShowTitle);
            return ResponseEntity.ok().body(tvShowInfoDto);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tvshows/id/{tvShowId}")
    public ResponseEntity<?> getTvShowInfoDtoById( @PathVariable String tvShowId){
        try {
            TvShowInfoDto tvShowInfoDto = tvShowService.fetchTvShowInfoDtoById(tvShowId);
            return ResponseEntity.ok().body(tvShowInfoDto);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tvshows")
    public ResponseEntity<?> getTvShowDtosByDifferentCategories(  @RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "5") Integer pageSize){
        Map<String, List<?>> responseMap = new HashMap<>();

        //Languages - English, Hindi
        List<TvShowDto> englishTvShowDtoList = tvShowService.fetchTvShowDtoListByLanguage(LanguageEnum.ENGLISH.name(), pageNo, pageSize);
        List<TvShowDto> hindiTvShowDtoList = tvShowService.fetchTvShowDtoListByLanguage(LanguageEnum.HINDI.name(), pageNo, pageSize);

        //Genre - Action, Crime, Thriller, Kids
        List<TvShowDto> actionTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.ACTION.name(), pageNo, pageSize);
        List<TvShowDto> crimeTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.CRIME.name(), pageNo, pageSize);
        List<TvShowDto> dramaTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.DRAMA.name(), pageNo, pageSize);

        //Other categories
        List<TvShowDto> latestTvShowDto = tvShowService.fetchTvShowDtoListOrderByReleaseDateDesc( pageNo, pageSize);
        List<TvShowDto> topTvShowDto = tvShowService.fetchTvShowDtoListOrderByRatingDesc( pageNo, pageSize);

//        List<String> languages = new ArrayList<>();
//
//        for(LanguageEnum languageEnum : LanguageEnum.values()){
//            languages.add(languageEnum.name())
//        }
//
//        List<String> genres = new ArrayList<>();
//
//        for (GenreEnum genreEnum : GenreEnum.values()) {
//            genres.add(genreEnum.name());
//        }

        // put all lists into response Map
        responseMap.put("English TvShows", englishTvShowDtoList);
        responseMap.put("Hindi TvShows", hindiTvShowDtoList);
        responseMap.put("Action TvShows", actionTvShowDtoList);
        responseMap.put("Crime TvShows", crimeTvShowDtoList);
        responseMap.put("Drama TvShows", dramaTvShowDtoList);
        responseMap.put("Latest TvShows", latestTvShowDto);
        responseMap.put("Top Rated TvShows on IMDb", topTvShowDto);

        return ResponseEntity.ok().body(responseMap);

    }
}
