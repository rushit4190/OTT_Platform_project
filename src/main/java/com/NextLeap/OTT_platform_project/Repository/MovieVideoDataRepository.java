package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Model.MovieVideoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieVideoDataRepository extends JpaRepository<MovieVideoData, String> {
}
