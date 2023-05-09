package sm.security.login.ticket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class TicketDTO {

    @NonNull
    private long userId;

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

    @NonNull
    private int userPrice;



}
