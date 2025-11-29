package mrtn.influ.campaign;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dao.repository.PitchRepository;
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
    @Autowired
    private PitchRepository pitchRepository;

    @Override
    public void run(String... args) {}
}