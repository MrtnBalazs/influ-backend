package mrtn.influ.campaign;

import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class CampaignServiceApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CampaignServiceApp.class, args);
    }

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public void run(String... args) {
        CampaignEntity campaign = new CampaignEntity();
        campaign.setTitle("Test Campaign");
        campaign.setDescription("Test description");
        campaign.setOwnerId("username");
        campaign.setFee(5);
        campaign.setPitches(new ArrayList<>());
        PitchEntity pitchEntity = new PitchEntity();
        pitchEntity.setCampaign(campaign);
        pitchEntity.setOwnerId("username1");
        pitchEntity.setText("Test pitch text");
        pitchEntity.setTitle("Test pitch title");
        campaign.getPitches().add(pitchEntity);

        CampaignEntity campaign2 = new CampaignEntity();
        campaign2.setTitle("Test campaign2");
        campaign2.setDescription("Test description2");
        campaign2.setOwnerId("username");
        campaign2.setFee(5);

        CampaignEntity campaign3 = new CampaignEntity();
        campaign3.setTitle("Test campaign3");
        campaign3.setDescription("Test description3");
        campaign3.setOwnerId("username2");
        campaign3.setFee(5);
        //campaign3.getFavoritedBy().add("username");

        campaignRepository.save(campaign);
        campaignRepository.save(campaign2);
        campaignRepository.save(campaign3);
    }
}