package com.NextLeap.OTT_platform_project.Service;

import com.NextLeap.OTT_platform_project.Dto.*;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.EpisodeInfo;
import com.NextLeap.OTT_platform_project.Model.SeasonInfo;
import com.NextLeap.OTT_platform_project.Model.TvShow;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TvShowService {

    TvShow fetchTvShowById(String tvShowId); // write exceptions

    SeasonInfo fetchSeasonInfoById(String seasonInfoId);

    EpisodeInfo fetchEpisodeInfoById(String episodeInfoId);

    TvShow fetchTvShowByTitle(String title);

    TvShowInfoDto fetchTvShowInfoDtoById(String tvShowId);

    TvShowInfoDto fetchTvShowInfoDtoByTitle(String tvShowTitle);
    List<TvShowDto> fetchTvShowDtoListByGenre(String genre, Integer pageNo, Integer pageSize);

    List<TvShowDto> fetchTvShowDtoListByLanguage(String language, Integer pageNo, Integer pageSize);

    List<TvShowDto> fetchTvShowDtoListOrderByRatingDesc(Integer pageNo, Integer pageSize);

    List<TvShowDto> fetchTvShowDtoListOrderByReleaseDateDesc(Integer pageNo, Integer pageSize);

    SeasonInfoDto fetchSeasonInfoDtoById(String seasonInfoId);
    List<SeasonInfo> fetchSeasonInfoListByTvShowId(String tvShowId);

    List<EpisodeDto> fetchEpisodeDtoListBySeasonInfoId(String seasonInfoId);

    TvShow saveTvShow(Map<String, Object> tvShowInfoBody) throws InvalidContentDataException;

    SeasonInfo saveSeasonInfo(Map<String, Object> seasonInfoBody) throws InvalidContentDataException;

    EpisodeInfo saveEpisodeInfo(Map<String, Object> episodeInfoBody) throws InvalidContentDataException;
    TvShow uploadTvShowPoster(MultipartFile posterFile, String tvShowTitle) throws IOException, InvalidContentDataException;

    TvShow setTvShowGenre(String genre, String tvShowTitle) throws InvalidContentDataException;

    TvShow setTvShowLanguage(String language, String tvShowTitle) throws InvalidContentDataException;

    TvShow setTvShowCastCrew(Map<String, Object> requestBody) throws InvalidContentDataException;

    EpisodeInfo uploadTvShowEpisodeLanguageVideo(MultipartFile videoFile, String episodeId, String language) throws IOException, InvalidContentDataException;


}
