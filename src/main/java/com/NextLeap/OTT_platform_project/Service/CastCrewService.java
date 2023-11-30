package com.NextLeap.OTT_platform_project.Service;

import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.CastCrew;

import java.util.Map;
import java.util.Optional;

public interface CastCrewService {

    CastCrew saveCastCrew(Map<String, Object> castCrewToSave) throws InvalidContentDataException;

    CastCrew fetchById(String castCrewId);
}
