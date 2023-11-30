package com.NextLeap.OTT_platform_project.Controller;

import com.NextLeap.OTT_platform_project.Dto.ContentDto;
import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowDto;
import com.NextLeap.OTT_platform_project.Enum.GenreEnum;
import com.NextLeap.OTT_platform_project.Enum.LanguageEnum;
import com.NextLeap.OTT_platform_project.Service.MovieService;
import com.NextLeap.OTT_platform_project.Service.TvShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ottplatform/v1/")
public class HomePageController {
    private final MovieService movieService;
    public final TvShowService tvShowService;

    public HomePageController(MovieService movieService, TvShowService tvShowService) {
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }

    @GetMapping("/home")
    public ResponseEntity<?> getHomePageContent(@RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "5") Integer pageSize){
        Map<String, List<?>> responseMap = new HashMap<>();

        //Top content on IMDb

        List<TvShowDto> topTvShowDto = tvShowService.fetchTvShowDtoListOrderByRatingDesc( pageNo, pageSize);
        List<MovieDto> topMovieDto = movieService.fetchMovieDtosOrderByRatingDesc( pageNo, pageSize);

        List<ContentDto> topContentDto = new ArrayList<>();

        topContentDto.addAll(topMovieDto);
        topContentDto.addAll(topTvShowDto);
        Collections.shuffle(topContentDto);  // This operation can be made efficient by using random(1,2) and using the result.

        responseMap.put("Top Rated content on IMDb", topContentDto);

        //Languages - English, Hindi
        List<MovieDto> englishMovieDtoList = movieService.fetchMovieDtosByLanguage(LanguageEnum.ENGLISH.name(), pageNo, pageSize);
        List<MovieDto> hindiMovieDtoList = movieService.fetchMovieDtosByLanguage(LanguageEnum.HINDI.name(), pageNo, pageSize);

        //Genre - Action, Crime, Thriller, Kids
        List<MovieDto> actionMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.ACTION.name(), pageNo, pageSize);
        List<MovieDto> crimeMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.CRIME.name(), pageNo, pageSize);
        List<MovieDto> dramaMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.DRAMA.name(), pageNo, pageSize);

        //Other categories
        List<MovieDto> latestMovieDto = movieService.fetchMovieDtosOrderByReleaseDateDesc( pageNo, pageSize);

        // put all lists into response Map
        responseMap.put("English Movies", englishMovieDtoList);
        responseMap.put("Hindi Movies", hindiMovieDtoList);
        responseMap.put("Action Movies", actionMovieDtoList);
        responseMap.put("Crime Movies", crimeMovieDtoList);
        responseMap.put("Drama Movies", dramaMovieDtoList);
        responseMap.put("Latest Releases", latestMovieDto);


        //Languages - English, Hindi
        List<TvShowDto> englishTvShowDtoList = tvShowService.fetchTvShowDtoListByLanguage(LanguageEnum.ENGLISH.name(), pageNo, pageSize);
        List<TvShowDto> hindiTvShowDtoList = tvShowService.fetchTvShowDtoListByLanguage(LanguageEnum.HINDI.name(), pageNo, pageSize);

        //Genre - Action, Crime, Thriller, Kids
        List<TvShowDto> actionTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.ACTION.name(), pageNo, pageSize);
        List<TvShowDto> crimeTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.CRIME.name(), pageNo, pageSize);
        List<TvShowDto> dramaTvShowDtoList= tvShowService.fetchTvShowDtoListByGenre(GenreEnum.DRAMA.name(), pageNo, pageSize);

        //Other categories
        List<TvShowDto> latestTvShowDto = tvShowService.fetchTvShowDtoListOrderByReleaseDateDesc( pageNo, pageSize);


        // put all lists into response Map
        responseMap.put("English TvShows", englishTvShowDtoList);
        responseMap.put("Hindi TvShows", hindiTvShowDtoList);
        responseMap.put("Action TvShows", actionTvShowDtoList);
        responseMap.put("Crime TvShows", crimeTvShowDtoList);
        responseMap.put("Drama TvShows", dramaTvShowDtoList);
        responseMap.put("Latest TvShows", latestTvShowDto);


        return ResponseEntity.ok().body(responseMap);

    }
}
