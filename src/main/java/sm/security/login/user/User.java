package sm.security.login.user;

import jakarta.persistence.*;
import lombok.*;
import sm.security.login.Role;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, unique = true, length = 30)
    private String username;
    @NonNull
    @Column(nullable = false, unique = false)
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }
}
