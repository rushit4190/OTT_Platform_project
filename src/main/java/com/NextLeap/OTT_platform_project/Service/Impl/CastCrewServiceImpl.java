package com.NextLeap.OTT_platform_project.Service.Impl;

import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.CastCrew;
import com.NextLeap.OTT_platform_project.Repository.CastCrewRepository;
import com.NextLeap.OTT_platform_project.Service.CastCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CastCrewServiceImpl implements CastCrewService {

    private final CastCrewRepository castCrewRepository;

    @Autowired
    public CastCrewServiceImpl(CastCrewRepository castCrewRepository) {
        this.castCrewRepository = castCrewRepository;
    }

    @Override
    public CastCrew saveCastCrew(Map<String, Object> castCrewToSave) throws InvalidContentDataException {
        CastCrew castCrew = new CastCrew();

        String name = (String) castCrewToSave.getOrDefault("name", "");
        String role = (String) castCrewToSave.getOrDefault("role", "");
        String description = (String) castCrewToSave.getOrDefault("description", "");

        if(name.isEmpty() || role.isEmpty()){
            throw new InvalidContentDataException(" Cast crew name or role cannot be empty. Please resend the data");
        }

        castCrew.setName(name);
        castCrew.setRole(role);
        castCrew.setDescription(description);

        return castCrewRepository.save(castCrew);
    }

    @Override
    public CastCrew fetchById(String castCrewId) {
        Optional<CastCrew> castCrew = castCrewRepository.findById(castCrewId);
        if(castCrew.isPresent()){
            return castCrew.get();
        }
        else{
            throw new NoSuchElementException("Cast Crew not available for the given id");
        }

    }


}
