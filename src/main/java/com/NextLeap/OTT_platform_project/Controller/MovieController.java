package com.NextLeap.OTT_platform_project.Controller;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Enum.GenreEnum;
import com.NextLeap.OTT_platform_project.Enum.LanguageEnum;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.Movie;
import com.NextLeap.OTT_platform_project.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/ottplatform/v1/")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/addmovie/")
    public ResponseEntity<?> uploadMovie(@RequestBody Map<String, Object> requestBody){

        try {
            Movie movieAdded = movieService.saveMovie(requestBody);
            return ResponseEntity.ok().body(movieAdded);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addmovie/poster")
    public ResponseEntity<?> uploadMoviePosterData(@RequestParam("poster") MultipartFile posterFile, @RequestParam String title){

        try{
            Movie movieToEdit = movieService.uploadMoviePoster(posterFile, title);
            return ResponseEntity.ok().body(movieToEdit);
        } catch (IOException | NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addmovie/genre")
    public ResponseEntity<?> setMovieGenreData(@RequestParam String genre, @RequestParam String title){

        try{
            Movie movieToEdit = movieService.setMovieGenre(genre, title);
            return ResponseEntity.ok().body(movieToEdit);
        } catch ( NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addmovie/movieLanguageVideo")
    public ResponseEntity<?> uploadMovieLanguageVideoData(@RequestParam("video") MultipartFile videoFile, @RequestParam String title, @RequestParam String language){

        try{
            Movie movieToEdit = movieService.uploadMovieLanguageVideo(videoFile, title, language);
            return ResponseEntity.ok().body(movieToEdit);
        } catch ( IOException | NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addmovie/movieCastCrew")
    public ResponseEntity<?> setMovieCastCrewData(@RequestBody Map<String, Object> requestBody){

        try{
            Movie movieToEdit = movieService.setMovieCastCrew(requestBody);
            return ResponseEntity.ok().body(movieToEdit);
        } catch ( NoSuchElementException | InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/movies/language/{language}")
    public ResponseEntity<?> getMovieDtosByLanguage(@PathVariable String language, @RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "5") Integer pageSize){

        List<MovieDto> movieDtoByLanguage = movieService.fetchMovieDtosByLanguage(language, pageNo, pageSize);
        return ResponseEntity.ok().body(movieDtoByLanguage);

    }

    @GetMapping("/movies/genre/{genre}")
    public ResponseEntity<?> getMovieDtosByGenre(@PathVariable String genre, @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "5") Integer pageSize){

        List<MovieDto> movieDtosByGenre= movieService.fetchMovieDtosByGenre(genre, pageNo, pageSize);
        return ResponseEntity.ok().body(movieDtosByGenre);

    }

    @GetMapping("/movies/top")
    public ResponseEntity<?> getMovieDtosOrderByRatingsDesc( @RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "5") Integer pageSize){

        List<MovieDto> movieDtos = movieService.fetchMovieDtosOrderByRatingDesc( pageNo, pageSize);
        return ResponseEntity.ok().body(movieDtos);

    }

    @GetMapping("/movies/latest")
    public ResponseEntity<?> getMovieDtosOrderByReleaseDateDesc( @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "5") Integer pageSize){

        List<MovieDto> movieDtos = movieService.fetchMovieDtosOrderByReleaseDateDesc( pageNo, pageSize);
        return ResponseEntity.ok().body(movieDtos);

    }

    @GetMapping("/movies/title/{movieTitle}")
    public ResponseEntity<?> getMovieByTitle( @PathVariable String movieTitle){
        try {
            Movie movieToView = movieService.fetchMovieByTitle(movieTitle);
            return ResponseEntity.ok().body(movieToView);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/movies/id/{movieId}")
    public ResponseEntity<?> getMovieById( @PathVariable String movieId){
        try {
            Movie movieToView = movieService.fetchMovieById(movieId);
            return ResponseEntity.ok().body(movieToView);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/movies")
    public ResponseEntity<?> getMovieDtosByDifferentCategories(  @RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "5") Integer pageSize){
        Map<String, List<?>> responseMap = new HashMap<>();

        //Languages - English, Hindi
        List<MovieDto> englishMovieDtoList = movieService.fetchMovieDtosByLanguage(LanguageEnum.ENGLISH.name(), pageNo, pageSize);
        List<MovieDto> hindiMovieDtoList = movieService.fetchMovieDtosByLanguage(LanguageEnum.HINDI.name(), pageNo, pageSize);

        //Genre - Action, Crime, Thriller, Kids
        List<MovieDto> actionMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.ACTION.name(), pageNo, pageSize);
        List<MovieDto> crimeMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.CRIME.name(), pageNo, pageSize);
        List<MovieDto> dramaMovieDtoList= movieService.fetchMovieDtosByGenre(GenreEnum.DRAMA.name(), pageNo, pageSize);

        //Other categories
        List<MovieDto> latestMovieDto = movieService.fetchMovieDtosOrderByReleaseDateDesc( pageNo, pageSize);
        List<MovieDto> topMovieDto = movieService.fetchMovieDtosOrderByRatingDesc( pageNo, pageSize);

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
        responseMap.put("English Movies", englishMovieDtoList);
        responseMap.put("Hindi Movies", hindiMovieDtoList);
        responseMap.put("Action Movies", actionMovieDtoList);
        responseMap.put("Crime Movies", crimeMovieDtoList);
        responseMap.put("Drama Movies", dramaMovieDtoList);
        responseMap.put("Latest Releases", latestMovieDto);
        responseMap.put("Top Rated Movies on IMDb", topMovieDto);

        return ResponseEntity.ok().body(responseMap);

    }




    @GetMapping("/")
    public String hello(){
//        System.out.println("Printing Hello on console");
        return "Hello Checking connection!";
    }


}
