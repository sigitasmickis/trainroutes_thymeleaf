package sm.security.login.train;

import lombok.*;
//padaryti validaciją laukų
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TrainDTO {

    @NonNull
    private String trainNumber;

    @NonNull
    private String cityFrom;

    @NonNull
    private String cityTo;

    @NonNull
    private String departTime;

    @NonNull
    private String arrivalTime;



}
