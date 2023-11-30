package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Model.MovieLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLanguageRepository extends JpaRepository<MovieLanguage, String> {
}
