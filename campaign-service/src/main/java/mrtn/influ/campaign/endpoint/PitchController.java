package mrtn.influ.campaign.endpoint;

import jakarta.transaction.Transactional;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.GetPitchesForUserResponse;
import mrtn.influ.campaign.dto.Pitch;
import mrtn.influ.campaign.log.LogRequestResponse;
import mrtn.influ.campaign.service.PitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PitchController implements PitchApi {

    @Autowired
    private PitchService pitchService;

    @LogRequestResponse
    @Transactional
    @Override
    public  ResponseEntity<Void> createPitch(String xUserId, CreatePitchRequest createPitchRequest) {
        pitchService.createPitch(createPitchRequest, xUserId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LogRequestResponse
    @Transactional
    @Override
    public ResponseEntity<Void> deletePitch(Integer id, String xUserId) {
        pitchService.deletePitch(id, xUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<GetPitchesForUserResponse> getPitchesForUser(String xUserId) {
        List<Pitch> pitchList = pitchService.getPitchesForUser(xUserId);
        return ResponseEntity.status(HttpStatus.OK).body(new GetPitchesForUserResponse().pitchList(pitchList));
    }

}
