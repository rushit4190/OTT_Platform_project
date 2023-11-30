package com.NextLeap.OTT_platform_project.Service.Impl;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.*;
import com.NextLeap.OTT_platform_project.Repository.*;
import com.NextLeap.OTT_platform_project.Service.CastCrewService;
import com.NextLeap.OTT_platform_project.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieGenreRepository movieGenreRepository;

    private final MovieLanguageRepository movieLanguageRepository;

    private final MovieVideoDataRepository movieVideoDataRepository;

    private final MovieCastCrewRepository movieCastCrewRepository;

    private final CastCrewService castCrewService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieGenreRepository movieGenreRepository, MovieLanguageRepository movieLanguageRepository, MovieVideoDataRepository movieVideoDataRepository, MovieCastCrewRepository movieCastCrewRepository, CastCrewService castCrewService) {
        this.movieRepository = movieRepository;
        this.movieGenreRepository = movieGenreRepository;
        this.movieLanguageRepository = movieLanguageRepository;
        this.movieVideoDataRepository = movieVideoDataRepository;
        this.movieCastCrewRepository = movieCastCrewRepository;
        this.castCrewService = castCrewService;
    }


    @Override
    public Movie fetchMovieById(String movieId) {
        Optional<Movie> movie =  movieRepository.findById(movieId);

        if(movie.isPresent()){
            return movie.get();
        }
        else{
            throw new NoSuchElementException("Movie not available for the given title");
        }

    }

    @Override
    public Movie fetchMovieByTitle(String title) throws NoSuchElementException{
        Optional<Movie> movie =  movieRepository.findByTitleIgnoreCase(title);

        if(movie.isPresent()){
            return movie.get();
        }
        else{
            throw new NoSuchElementException("Movie not available for the given title");
        }
    }

    @Override
    public List<MovieDto> fetchMovieDtosByGenre(String genre, Integer pageNo, Integer pageSize) {

        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return movieRepository.findMovieIdTitleByGenresGenre(genre, pageQuery).getContent();

    }

    @Override
    public List<MovieDto> fetchMovieDtosByLanguage(String language, Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return movieRepository.findMovieIdTitleByLanguagesLanguage(language, pageQuery).getContent();

    }

    @Override
    public List<MovieDto> fetchMovieDtosOrderByRatingDesc(Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return movieRepository.findMovieIdTitleByOrderByRatingDesc(pageQuery).getContent();

    }

    @Override
    public List<MovieDto> fetchMovieDtosOrderByReleaseDateDesc(Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return movieRepository.findMovieIdTitleByOrderByReleaseDateDesc(pageQuery).getContent();

    }

    @Override
    public Movie saveMovie(Map<String, Object> movieInfo) throws InvalidContentDataException {
        Movie movieToSave = new Movie();

        String title = (String) movieInfo.getOrDefault("title", "");
        String description = (String) movieInfo.getOrDefault("description", "");

        if(title.isEmpty() || description.isEmpty()){
            throw new InvalidContentDataException(" Movie title or description cannot be empty. Please resend the data");
        }

        LocalDate releaseDate = LocalDate.parse((String) movieInfo.getOrDefault("releaseDate", "1000-01-01")); // giving a default to manage the error
        Long movieDuration = Long.parseLong( (String) movieInfo.getOrDefault("movieDuration", null));
        Double rating = Double.parseDouble((String) movieInfo.getOrDefault("rating", null));

        movieToSave.setTitle(title);
        movieToSave.setDescription(description);
        movieToSave.setReleaseDate(releaseDate.equals( LocalDate.parse("1000-01-01")) ? null : releaseDate );
        movieToSave.setMovieDuration(movieDuration);
        movieToSave.setRating(rating);

        return movieRepository.save(movieToSave);
    }

    @Override
    public Movie uploadMoviePoster(MultipartFile posterFile, String movieTitle) throws IOException, NoSuchElementException, InvalidContentDataException {
        if(movieTitle.isEmpty()){
            throw new InvalidContentDataException(" Movie title cannot be empty. Please resend the data");
        }

        Movie movieToEdit = fetchMovieByTitle(movieTitle);
        movieToEdit.setPoster(posterFile.getBytes());

        return movieRepository.save(movieToEdit);
    }

    @Override
    public Movie setMovieGenre(String genre, String movieTitle) throws InvalidContentDataException, NoSuchElementException {
        if(movieTitle.isEmpty() || genre.isEmpty()){
            throw new InvalidContentDataException(" Movie title or genre cannot be empty. Please resend the data");
        }

        Movie movieToEdit = fetchMovieByTitle(movieTitle);

        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenre(genre);
        movieGenre.setMovie(movieToEdit);

        movieGenreRepository.save(movieGenre);

        movieToEdit.getGenres().add(movieGenre);
        return movieToEdit;
    }

    @Override
    public Movie uploadMovieLanguageVideo(MultipartFile videoFile, String movieTitle, String language) throws IOException, NoSuchElementException, InvalidContentDataException {
        if(movieTitle.isEmpty()){
            throw new InvalidContentDataException(" Movie title cannot be empty. Please resend the data");
        }

        Movie movieToEdit = fetchMovieByTitle(movieTitle);

        MovieLanguage movieLanguage = new MovieLanguage();
        movieLanguage.setLanguage(language);
        movieLanguage.setMovie(movieToEdit);

        movieLanguageRepository.save(movieLanguage);

        MovieVideoData movieVideoData = new MovieVideoData();
        movieVideoData.setLanguage(language);
        movieVideoData.setVideoData(videoFile.getBytes());
        movieVideoData.setMovie(movieToEdit);

        movieVideoDataRepository.save(movieVideoData);

        movieToEdit.getLanguages().add(movieLanguage);

        return movieToEdit;
    }

    @Override
    public Movie setMovieCastCrew(Map<String, Object> requestBody) throws InvalidContentDataException, NoSuchElementException {
        String castCrewId = (String) requestBody.getOrDefault("castCrewId", "");
        String movieTitle = (String) requestBody.getOrDefault("movieTitle", "");

        if(movieTitle.isEmpty() || castCrewId.isEmpty()){
            throw new InvalidContentDataException(" Movie title or cast_crew_id cannot be empty. Please resend the data");
        }

        Movie movieToEdit = fetchMovieByTitle(movieTitle);
        CastCrew castCrewToSet = castCrewService.fetchById(castCrewId);

        MovieCastCrew movieCastCrew = new MovieCastCrew();
        movieCastCrew.setMovie(movieToEdit);
        movieCastCrew.setMovieCastCrew(castCrewToSet);

        movieToEdit.getMovieCastCrews().add(movieCastCrew);

        movieCastCrewRepository.save(movieCastCrew);

        return movieToEdit;
    }


}
