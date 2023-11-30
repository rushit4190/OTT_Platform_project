package com.NextLeap.OTT_platform_project.Service.Impl;

import com.NextLeap.OTT_platform_project.Dto.EpisodeDto;
import com.NextLeap.OTT_platform_project.Dto.SeasonInfoDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowDto;
import com.NextLeap.OTT_platform_project.Dto.TvShowInfoDto;
import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.*;
import com.NextLeap.OTT_platform_project.Repository.*;
import com.NextLeap.OTT_platform_project.Service.CastCrewService;
import com.NextLeap.OTT_platform_project.Service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TvShowServiceImpl implements TvShowService {

    public final TvShowRepository tvShowRepository;
    public final TvShowGenreRepository tvShowGenreRepository;
    public final TvShowCastCrewRepository tvShowCastCrewRepository;
    public final TvShowLanguageRepository tvShowLanguageRepository;
    public final SeasonInfoRepository seasonInfoRepository;

    public final EpisodeInfoRepository episodeInfoRepository;
    public final EpisodeVideoDataRepository episodeVideoDataRepository;
    public final CastCrewService castCrewService;

    @Autowired
    public TvShowServiceImpl(TvShowRepository tvShowRepository, TvShowGenreRepository tvShowGenreRepository, TvShowCastCrewRepository tvShowCastCrewRepository, TvShowLanguageRepository tvShowLanguageRepository, SeasonInfoRepository seasonInfoRepository, EpisodeInfoRepository episodeInfoRepository, EpisodeVideoDataRepository episodeVideoDataRepository, CastCrewService castCrewService) {
        this.tvShowRepository = tvShowRepository;
        this.tvShowGenreRepository = tvShowGenreRepository;
        this.tvShowCastCrewRepository = tvShowCastCrewRepository;
        this.tvShowLanguageRepository = tvShowLanguageRepository;
        this.seasonInfoRepository = seasonInfoRepository;
        this.episodeInfoRepository = episodeInfoRepository;
        this.episodeVideoDataRepository = episodeVideoDataRepository;
        this.castCrewService = castCrewService;
    }

    @Override
    public TvShow fetchTvShowById(String tvShowId) {
        Optional<TvShow> tvShow =  tvShowRepository.findById(tvShowId);

        if(tvShow.isPresent()){
            return tvShow.get();
        }
        else{
            throw new NoSuchElementException("TvShow not available for the given id");
        }

    }

    @Override
    public SeasonInfo fetchSeasonInfoById(String seasonInfoId) {
        Optional<SeasonInfo> seasonInfo =  seasonInfoRepository.findById(seasonInfoId);

        if(seasonInfo.isPresent()){
            return seasonInfo.get();
        }
        else{
            throw new NoSuchElementException("SeasonInfo not available for the given id");
        }
    }

    @Override
    public EpisodeInfo fetchEpisodeInfoById(String episodeInfoId) {
        Optional<EpisodeInfo> episodeInfo =  episodeInfoRepository.findById(episodeInfoId);

        if(episodeInfo.isPresent()){
            return episodeInfo.get();
        }
        else{
            throw new NoSuchElementException("Episode Info not available for the given id");
        }
    }

    @Override
    public TvShow fetchTvShowByTitle(String title) {
        Optional<TvShow> tvShow =  tvShowRepository.findByTitleIgnoreCase(title);

        if(tvShow.isPresent()){
            return tvShow.get();
        }
        else{
            throw new NoSuchElementException("TvShow not available for the given title");
        }

    }

    @Override
    public TvShowInfoDto fetchTvShowInfoDtoById(String tvShowId) throws NoSuchElementException {
        TvShow tvShowToView = fetchTvShowById(tvShowId);
        List<SeasonInfo> seasonInfoList = fetchSeasonInfoListByTvShowId(tvShowId);
        Integer totalNoOfSeasons = seasonInfoList.size();

        TvShowInfoDto result = new TvShowInfoDto();
        result.setTvShow(tvShowToView);
        result.setSeasonInfoList(seasonInfoList);
        result.setTotalSeasons(totalNoOfSeasons);

        return result;
    }

    @Override
    public TvShowInfoDto fetchTvShowInfoDtoByTitle(String tvShowTitle) throws NoSuchElementException {
        TvShow tvShowToView = fetchTvShowByTitle(tvShowTitle);
        List<SeasonInfo> seasonInfoList = fetchSeasonInfoListByTvShowId(tvShowToView.getId());
        Integer totalNoOfSeasons = seasonInfoList.size();

        TvShowInfoDto result = new TvShowInfoDto();
        result.setTvShow(tvShowToView);
        result.setSeasonInfoList(seasonInfoList);
        result.setTotalSeasons(totalNoOfSeasons);

        return result;
    }

    @Override
    public List<TvShowDto> fetchTvShowDtoListByGenre(String genre, Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return tvShowRepository.findTvShowIdTitleByGenresGenre(genre, pageQuery).getContent();

    }

    @Override
    public List<TvShowDto> fetchTvShowDtoListByLanguage(String language, Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return tvShowRepository.findTvShowIdTitleByLanguagesLanguage(language, pageQuery).getContent();
    }

    @Override
    public List<TvShowDto> fetchTvShowDtoListOrderByRatingDesc(Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return tvShowRepository.findTvShowIdTitleByOrderByRatingDesc(pageQuery).getContent();
    }

    @Override
    public List<TvShowDto> fetchTvShowDtoListOrderByReleaseDateDesc(Integer pageNo, Integer pageSize) {
        Pageable pageQuery = PageRequest.of(pageNo, pageSize);
        return tvShowRepository.findTvShowIdTitleByOrderByReleaseDateDesc(pageQuery).getContent();
    }

    @Override
    public SeasonInfoDto fetchSeasonInfoDtoById(String seasonInfoId) throws NoSuchElementException{
        SeasonInfo seasonInfoToView = fetchSeasonInfoById(seasonInfoId);
        List<EpisodeDto> episodeDtoList = fetchEpisodeDtoListBySeasonInfoId(seasonInfoId);
        Integer totalNoOfEpisodes = episodeDtoList.size();

        SeasonInfoDto result = new SeasonInfoDto();
        result.setSeasonInfo(seasonInfoToView);
        result.setEpisodeDtoList(episodeDtoList);
        result.setTotalEpisodes(totalNoOfEpisodes);

        return result;
    }

    @Override
    public List<SeasonInfo> fetchSeasonInfoListByTvShowId(String tvShowId) {
        return seasonInfoRepository.findAllByTvShowId(tvShowId);
    }

    @Override
    public List<EpisodeDto> fetchEpisodeDtoListBySeasonInfoId(String seasonInfoId) {
        return episodeInfoRepository.findEpisodeDtoBySeasonInfoId(seasonInfoId);
    }


    @Override
    public TvShow saveTvShow(Map<String, Object> tvShowInfoBody) throws InvalidContentDataException {
        TvShow tvShowToSave = new TvShow();

        String title = (String) tvShowInfoBody.getOrDefault("title", "");
        String description = (String) tvShowInfoBody.getOrDefault("description", "");

        if(title.isEmpty() || description.isEmpty()){
            throw new InvalidContentDataException(" TvShow title or description cannot be empty. Please resend the data");
        }

        LocalDate releaseDate = LocalDate.parse((String) tvShowInfoBody.getOrDefault("releaseDate", "1000-01-01")); // giving a default to manage the error
        LocalDate endDate = LocalDate.parse((String) tvShowInfoBody.getOrDefault("endDate", "1000-01-01")); // giving a default to manage the error

        Double rating = Double.parseDouble((String) tvShowInfoBody.getOrDefault("rating", null));

        tvShowToSave.setTitle(title);
        tvShowToSave.setDescription(description);
        tvShowToSave.setReleaseDate(releaseDate.equals( LocalDate.parse("1000-01-01")) ? null : releaseDate );
        tvShowToSave.setEndDate(endDate.equals( LocalDate.parse("1000-01-01")) ? null : endDate );

        tvShowToSave.setRating(rating);

        return tvShowRepository.save(tvShowToSave);
    }

    @Override
    public SeasonInfo saveSeasonInfo(Map<String, Object> seasonInfoBody) throws InvalidContentDataException {
        SeasonInfo seasonInfoToSave = new SeasonInfo();

        String title = (String) seasonInfoBody.getOrDefault("title", "");
        String description = (String) seasonInfoBody.getOrDefault("description", "");
        String tvShowId = (String) seasonInfoBody.getOrDefault("tvShowId", "");

        if(title.isEmpty() || description.isEmpty() || tvShowId.isEmpty()){
            throw new InvalidContentDataException(" Season title or description or tvShowId cannot be empty. Please resend the data");
        }

        LocalDate releaseDate = LocalDate.parse((String) seasonInfoBody.getOrDefault("releaseDate", "1000-01-01")); // giving a default to manage the error
        Double rating = Double.parseDouble((String) seasonInfoBody.getOrDefault("rating", null));
        Integer seasonIndex = Integer.parseInt((String) seasonInfoBody.getOrDefault("seasonIndex","0"));

        seasonInfoToSave.setTitle(title);
        seasonInfoToSave.setDescription(description);
        seasonInfoToSave.setReleaseDate(releaseDate.equals( LocalDate.parse("1000-01-01")) ? null : releaseDate );
        seasonInfoToSave.setRating(rating);
        seasonInfoToSave.setSeasonIndex(seasonIndex);
        seasonInfoToSave.setTvShowId(tvShowId);

        return seasonInfoRepository.save(seasonInfoToSave);
    }

    @Override
    public EpisodeInfo saveEpisodeInfo(Map<String, Object> episodeInfoBody) throws InvalidContentDataException {
        EpisodeInfo episodeInfoToSave = new EpisodeInfo();

        String title = (String) episodeInfoBody.getOrDefault("title", "");
        String description = (String) episodeInfoBody.getOrDefault("description", "");
        String seasonInfoId = (String) episodeInfoBody.getOrDefault("seasonInfoId", "");

        if(title.isEmpty() || description.isEmpty() || seasonInfoId.isEmpty()){
            throw new InvalidContentDataException(" Episode title or description or seasonId cannot be empty. Please resend the data");
        }

        LocalDate releaseDate = LocalDate.parse((String) episodeInfoBody.getOrDefault("releaseDate", "1000-01-01")); // giving a default to manage the error
        Long episodeDuration = Long.parseLong((String) episodeInfoBody.getOrDefault("episodeDuration", null));
        Double rating = Double.parseDouble((String) episodeInfoBody.getOrDefault("rating", null));
        Integer episodeIndex = Integer.parseInt((String) episodeInfoBody.getOrDefault("episodeIndex","0"));

        episodeInfoToSave.setTitle(title);
        episodeInfoToSave.setDescription(description);
        episodeInfoToSave.setReleaseDate(releaseDate.equals( LocalDate.parse("1000-01-01")) ? null : releaseDate );
        episodeInfoToSave.setRating(rating);
        episodeInfoToSave.setEpisodeDuration(episodeDuration);
        episodeInfoToSave.setEpisodeIndex(episodeIndex);
        episodeInfoToSave.setSeasonInfoId(seasonInfoId);

        return episodeInfoRepository.save(episodeInfoToSave);
    }

    @Override
    public TvShow uploadTvShowPoster(MultipartFile posterFile, String tvShowTitle) throws IOException, InvalidContentDataException, NoSuchElementException {
        if(tvShowTitle.isEmpty()){
            throw new InvalidContentDataException(" TvShow title cannot be empty. Please resend the data");
        }

        TvShow tvShowToEdit = fetchTvShowByTitle(tvShowTitle);
        tvShowToEdit.setPoster(posterFile.getBytes());

        return tvShowRepository.save(tvShowToEdit);
    }

    @Override
    public TvShow setTvShowGenre(String genre, String tvShowTitle) throws InvalidContentDataException, NoSuchElementException {
        if(tvShowTitle.isEmpty() || genre.isEmpty()){
            throw new InvalidContentDataException(" TvShow title or genre cannot be empty. Please resend the data");
        }

        TvShow tvShowToEdit = fetchTvShowByTitle(tvShowTitle);

        TvShowGenre tvShowGenre = new TvShowGenre();
        tvShowGenre.setGenre(genre);
        tvShowGenre.setTvShow(tvShowToEdit);

        tvShowGenreRepository.save(tvShowGenre);
        tvShowToEdit.getGenres().add(tvShowGenre);

        return tvShowToEdit;
    }

    @Override
    public TvShow setTvShowLanguage(String language, String tvShowTitle) throws InvalidContentDataException, NoSuchElementException {
        if(tvShowTitle.isEmpty() || language.isEmpty()){
            throw new InvalidContentDataException(" TvShow title or language cannot be empty. Please resend the data");
        }

        TvShow tvShowToEdit = fetchTvShowByTitle(tvShowTitle);

        TvShowLanguage tvShowLanguage = new TvShowLanguage();
        tvShowLanguage.setLanguage(language);
        tvShowLanguage.setTvShow(tvShowToEdit);

        tvShowLanguageRepository.save(tvShowLanguage);

        return tvShowToEdit;
    }

    @Override
    public TvShow setTvShowCastCrew(Map<String, Object> requestBody) throws InvalidContentDataException, NoSuchElementException {
        String castCrewId = (String) requestBody.getOrDefault("castCrewId", "");
        String tvShowTitle = (String) requestBody.getOrDefault("tvShowTitle", "");

        if(tvShowTitle.isEmpty() || castCrewId.isEmpty()){
            throw new InvalidContentDataException(" TvShow title or cast_crew_id cannot be empty. Please resend the data");
        }
        TvShow tvShowToEdit = fetchTvShowByTitle(tvShowTitle);
        CastCrew castCrewToSet = castCrewService.fetchById(castCrewId);

        TvShowCastCrew tvShowCastCrew = new TvShowCastCrew();
        tvShowCastCrew.setTvShowCastCrew(castCrewToSet);
        tvShowCastCrew.setTvShow(tvShowToEdit);

        tvShowToEdit.getTvShowCastCrews().add(tvShowCastCrew);

        tvShowCastCrewRepository.save(tvShowCastCrew);

        return tvShowToEdit;
    }

    @Override
    public EpisodeInfo uploadTvShowEpisodeLanguageVideo(MultipartFile videoFile, String episodeId, String language) throws IOException, InvalidContentDataException, NoSuchElementException {
        if(episodeId.isEmpty() || language.isEmpty()){
            throw new InvalidContentDataException(" Episode id or language cannot be empty. Please resend the data");
        }

        EpisodeInfo episodeInfoToEdit = fetchEpisodeInfoById(episodeId);

        EpisodeVideoData episodeVideoData = new EpisodeVideoData();
        episodeVideoData.setLanguage(language);
        episodeVideoData.setEpisode(episodeInfoToEdit);
        episodeVideoData.setVideoData(videoFile.getBytes());

        episodeVideoDataRepository.save(episodeVideoData);

//        episodeInfoToEdit.getEpisodeVideoDataSet().add(episodeVideoData);

        return episodeInfoToEdit;
    }
}
