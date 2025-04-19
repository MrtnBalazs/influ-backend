package mrtn.influ.endpoint;


import jakarta.ws.rs.HeaderParam;
import mrtn.influ.dao.CampaignRepository;
import mrtn.influ.dto.GetCampaignForUserResponse;
import mrtn.influ.dto.GetCampaignResponse;
import mrtn.influ.entity.CampaignEntity;
import mrtn.influ.mapper.CampaignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CampaignMapper campaignMapper;

    @GetMapping("/test")
    public ResponseEntity<Void> getCampaign() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampaignResponse> getCampaignById(@PathVariable(name = "id") Long id) {
        Optional<CampaignEntity> optionalCampaignEntity = campaignRepository.findById(id);
        return optionalCampaignEntity
                .map(campaignEntity -> ResponseEntity.ok(new GetCampaignResponse(campaignMapper.mapCampaign(campaignEntity))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/user")
    public ResponseEntity<GetCampaignForUserResponse> getCampaignForUser(@RequestHeader("X-User-Id") String userId) {
        if(userId == null)
            throw new RuntimeException("Missing user id!");
        List<CampaignEntity> campaignEntity = campaignRepository.findAllByUserId(userId);
        return new ResponseEntity<>(new GetCampaignForUserResponse(campaignMapper.mapCampaign(campaignEntity)), HttpStatus.OK);
    }

}
