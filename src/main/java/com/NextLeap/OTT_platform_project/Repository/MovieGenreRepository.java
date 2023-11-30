package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, String> {
}
