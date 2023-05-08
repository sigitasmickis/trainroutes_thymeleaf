package sm.security.login.city;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CityDTO {


    @NonNull
    private String name;
}
