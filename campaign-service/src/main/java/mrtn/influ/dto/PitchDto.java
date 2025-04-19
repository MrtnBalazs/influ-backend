package mrtn.influ.dto;

public record PitchDto (
    Long id,
    Long creatorId,
    String title,
    String text
) {}
