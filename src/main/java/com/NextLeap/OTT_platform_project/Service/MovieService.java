package com.NextLeap.OTT_platform_project.Service;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MovieService {
    Movie fetchMovieById(String movieId); // write exceptions

    Movie fetchMovieByTitle(String title);
    List<MovieDto> fetchMovieDtosByGenre(String genre, Integer pageNo, Integer pageSize);

    List<MovieDto> fetchMovieDtosByLanguage(String language, Integer pageNo, Integer pageSize);

    List<MovieDto> fetchMovieDtosOrderByRatingDesc(Integer pageNo, Integer pageSize);

    List<MovieDto> fetchMovieDtosOrderByReleaseDateDesc(Integer pageNo, Integer pageSize);



    Movie saveMovie(Map<String, Object> movieToSave) throws InvalidContentDataException;

    Movie uploadMoviePoster(MultipartFile posterFile, String movieTitle) throws IOException, InvalidContentDataException;

    Movie setMovieGenre(String genre, String movieTitle) throws InvalidContentDataException;

    Movie uploadMovieLanguageVideo(MultipartFile videoFile, String movieTitle, String language) throws IOException, InvalidContentDataException;

    Movie setMovieCastCrew(Map<String, Object> requestBody) throws InvalidContentDataException;

}
