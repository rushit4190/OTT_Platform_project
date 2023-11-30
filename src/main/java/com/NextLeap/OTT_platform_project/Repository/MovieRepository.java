package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    Optional<Movie> findByTitleIgnoreCase(String title);

    Page<MovieDto> findMovieIdTitleByLanguagesLanguage(String language, Pageable pageable);

    Page<MovieDto> findMovieIdTitleByGenresGenre(String genre, Pageable pageable);

    Page<MovieDto> findMovieIdTitleByOrderByRatingDesc(Pageable pageable);

    Page<MovieDto> findMovieIdTitleByOrderByReleaseDateDesc(Pageable pageable);
}
