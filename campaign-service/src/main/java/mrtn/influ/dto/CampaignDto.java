package mrtn.influ.dto;

import java.util.List;

public record CampaignDto (
    Long id,
    String userId,
    String title,
    String description,
    Integer maxFee,
    Integer minFee,
    List<PitchDto> pitches
){}
