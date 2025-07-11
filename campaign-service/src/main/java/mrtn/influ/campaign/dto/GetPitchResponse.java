package mrtn.influ.campaign.dto;

public class GetPitchResponse {
    private PitchDto pitch;

    public GetPitchResponse(PitchDto pitch) {
        this.pitch = pitch;
    }

    public PitchDto getPitch() {
        return pitch;
    }

    public void setPitch(PitchDto pitch) {
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "GetPitchResponse{" +
                "pitch=" + pitch +
                '}';
    }
}
