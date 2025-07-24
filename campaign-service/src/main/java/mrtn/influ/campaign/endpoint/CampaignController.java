package mrtn.influ.campaign.endpoint;

import jakarta.transaction.Transactional;
import mrtn.influ.campaign.dto.*;
import mrtn.influ.campaign.log.LogRequestResponse;
import mrtn.influ.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // TODO only for testing without gateway
@Controller
public class CampaignController implements CampaignApi {

    @Autowired
    private CampaignService campaignService;

    @LogRequestResponse
    @Transactional
    @Override
    public ResponseEntity<Void> createCampaign(String xUserId, CreateCampaignRequest createCampaignRequest) {
        campaignService.saveCampaign(createCampaignRequest, xUserId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @LogRequestResponse
    @Transactional
    @Override
    public ResponseEntity<Void> deleteCampaign(Integer id, String xUserId) {
        campaignService.deleteCampaign(id, xUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<GetAllCampaignsResponse> getAllCampaigns() {
        return ResponseEntity.ok(new GetAllCampaignsResponse(campaignService.getAllCampaign()));
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<GetAllCampaignsForUserResponse> getAllCampaignsForUser(String xUserId) {
        List<Campaign> campaigns = campaignService.getCampaignsForUser(xUserId);
        return new ResponseEntity<>(new GetAllCampaignsForUserResponse(campaigns), HttpStatus.OK);
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<GetCampaignResponse> getCampaign(Integer id) {
        Campaign campaignDto = campaignService.getCampaignById(id.longValue());
        return ResponseEntity.ok(new GetCampaignResponse(campaignDto));
    }
}
