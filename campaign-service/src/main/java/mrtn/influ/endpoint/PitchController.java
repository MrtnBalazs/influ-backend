package mrtn.influ.endpoint;

import jakarta.ws.rs.HeaderParam;
import mrtn.influ.business.service.PitchService;
import mrtn.influ.dto.CreatePitchRequest;
import mrtn.influ.dto.GetPitchResponse;
import mrtn.influ.dto.GetPitchesForUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static mrtn.influ.endpoint.CampaignController.USER_ID_HEADER;

@Controller
@RequestMapping("/api/v1/campaigns/pitches")
public class PitchController {

    @Autowired
    private PitchService pitchService;

    @GetMapping("/{id}")
    public ResponseEntity<GetPitchResponse> getPitch(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(new GetPitchResponse(pitchService.getPitchById(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<GetPitchesForUserResponse> getPitchesForUser(@RequestHeader(USER_ID_HEADER) String userId) {
        return ResponseEntity.ok(new GetPitchesForUserResponse(pitchService.getPitchesByUserId(userId)));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPitch(@RequestHeader(USER_ID_HEADER) String userId, @RequestBody CreatePitchRequest createPitchRequest) {
        pitchService.createPitch(createPitchRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
