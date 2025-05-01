package mrtn.influ.endpoint;


import mrtn.influ.business.service.CampaignService;
import mrtn.influ.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // TODO only for testing without gateway
@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {
    public static final String USER_ID_HEADER = "X-User-Id";

    @Autowired
    private CampaignService campaignService;

    @GetMapping()
    public ResponseEntity<GetAllCampaignsResponse> getAllCampaigns() {
        return ResponseEntity.ok(new GetAllCampaignsResponse(campaignService.getAllCampaign()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampaignResponse> getCampaign(@PathVariable(name = "id") Long id, @RequestHeader(name = USER_ID_HEADER, required = false) String userId) {
        CampaignDto campaignDto = campaignService.getCampaignById(id, userId);
        return ResponseEntity.ok(new GetCampaignResponse(campaignDto));
    }

    @GetMapping("/user")
    public ResponseEntity<GetCampaignForUserResponse> getCampaignsForUser(@RequestHeader(USER_ID_HEADER) String userId) {
        List<CampaignDto> campaignDtos = campaignService.getCampaignsForUser(userId);
        return new ResponseEntity<>(new GetCampaignForUserResponse(campaignDtos), HttpStatus.OK);
    }

    @GetMapping("/saved")
    public ResponseEntity<GetCampaignForUserResponse> getSavedCampaigns(@RequestHeader(USER_ID_HEADER) String userId) {
        List<CampaignDto> campaignDtos = campaignService.getSavedCampaignsForUser(userId);
        return new ResponseEntity<>(new GetCampaignForUserResponse(campaignDtos), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCampaign(@RequestBody CreateCampaignRequest createCampaignRequest, @RequestHeader(USER_ID_HEADER) String userId) {
        campaignService.saveCampaign(createCampaignRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
