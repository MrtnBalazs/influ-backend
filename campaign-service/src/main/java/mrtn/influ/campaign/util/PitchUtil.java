package mrtn.influ.campaign.util;

import mrtn.influ.campaign.dao.entity.PitchState;

import java.util.Map;
import java.util.Set;

public class PitchUtil {

    private static final Map<PitchState, Set<PitchState>> ALLOWED_PITCH_STATE_TRANSITIONS =
            Map.of(
                    PitchState.PENDING, Set.of(
                            PitchState.SELECTED,
                            PitchState.REJECTED
                    ),
                    PitchState.SELECTED, Set.of(
                            PitchState.PENDING,
                            PitchState.ACCEPTED,
                            PitchState.REJECTED
                    ),
                    PitchState.ACCEPTED, Set.of(
                            PitchState.DONE
                    ),
                    PitchState.DONE, Set.of(),
                    PitchState.REJECTED, Set.of()
            );

    static public boolean isPitchStateUpdateValid(PitchState oldState, PitchState newState) {
        return ALLOWED_PITCH_STATE_TRANSITIONS.get(oldState).contains(newState);
    }
}
