package mrtn.influ.dto;

import java.util.List;

public class GetPitchesForUserResponse {
    private List<PitchDto> pitches;

    public GetPitchesForUserResponse(List<PitchDto> pitches) {
        this.pitches = pitches;
    }

    public List<PitchDto> getPitches() {
        return pitches;
    }

    public void setPitches(List<PitchDto> pitches) {
        this.pitches = pitches;
    }

    @Override
    public String toString() {
        return "GetPitchesForUserResponse{" +
                "pitches=" + pitches +
                '}';
    }
}
