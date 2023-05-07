package sm.security.login.ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class TicketsDTO {

    @NonNull
    private List<TicketDTO> ticketsDTO;

    public static TicketsDTO of(List<TicketDTO> ticketsDTO) {
        return new TicketsDTO(ticketsDTO);
    }
}
