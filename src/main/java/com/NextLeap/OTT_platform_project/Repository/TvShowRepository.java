package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Dto.MovieDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowDto;
import com.NextLeap.OTT_platform_project.Model.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, String > {
    Optional<TvShow> findByTitleIgnoreCase(String title);

    Page<TvShowDto> findTvShowIdTitleByLanguagesLanguage(String language, Pageable pageable);

    Page<TvShowDto> findTvShowIdTitleByGenresGenre(String genre, Pageable pageable);

    Page<TvShowDto> findTvShowIdTitleByOrderByRatingDesc(Pageable pageable);

    Page<TvShowDto> findTvShowIdTitleByOrderByReleaseDateDesc(Pageable pageable);
}
