package com.NextLeap.OTT_platform_project.Controller;

import com.NextLeap.OTT_platform_project.Exceptions.InvalidContentDataException;
import com.NextLeap.OTT_platform_project.Model.CastCrew;
import com.NextLeap.OTT_platform_project.Model.Movie;
import com.NextLeap.OTT_platform_project.Service.CastCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ottplatform/v1/")
public class CastCrewController {

    private final CastCrewService castCrewService;

    @Autowired
    public CastCrewController(CastCrewService castCrewService) {
        this.castCrewService = castCrewService;
    }

    @PostMapping("/addcastcrew/")
    public ResponseEntity<?> uploadCastCrew(@RequestBody Map<String, Object> requestBody){
        try {
            CastCrew castCrewAdded = castCrewService.saveCastCrew(requestBody);
            return ResponseEntity.ok().body(castCrewAdded);
        } catch (InvalidContentDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
