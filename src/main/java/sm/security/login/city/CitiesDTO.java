package sm.security.login.city;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CitiesDTO {

    @NonNull
    private List<CityDTO> sitiesDTO;

    public static CitiesDTO of(List<CityDTO> citiesDTO) {
        return new CitiesDTO(citiesDTO);
    }
}
