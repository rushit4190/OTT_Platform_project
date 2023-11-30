package com.NextLeap.OTT_platform_project.Repository;

import com.NextLeap.OTT_platform_project.Dto.EpisodeDto;
import com.NextLeap.OTT_platform_project.Model.EpisodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeInfoRepository extends JpaRepository<EpisodeInfo, String> {

    @Query("SELECT new com.NextLeap.OTT_platform_project.Dto.EpisodeDto(e.id, e.title, e.description, e.episodeDuration, e.episodeIndex) FROM EpisodeInfo e WHERE e.seasonInfoId = :seasonId")
    List<EpisodeDto> findEpisodeDtoBySeasonInfoId(@Param("seasonId") String seasonId);
}
