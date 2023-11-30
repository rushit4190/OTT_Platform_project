package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Model.SeasonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonInfoRepository extends JpaRepository<SeasonInfo, String> {

    List<SeasonInfo> findAllByTvShowId(String tvShowId);
}
