package sm.security.login.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class TrainsDTO {

    @NonNull
    private List<TrainDTO> trainsDTO;

    public static TrainsDTO of(List<TrainDTO> trainsDTO) {
        return new TrainsDTO(trainsDTO);
    }


}
