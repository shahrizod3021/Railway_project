package ali.project.railway_project.Entity;

import ali.project.railway_project.Entity.AbsEntity.AbsEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {
    private String name;
    private String lastName;
    private String phoneNumber;

    private String password;

    private Long chatId;

    private String email;

    @OneToMany
    private List<Clothe> clothe;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> role;

    private boolean enabled = true;

    private boolean credintialNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean accauntNonExpired = true;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accauntNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credintialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public User(String name, String lastName, String phoneNumber, String password, List<Role> role, String email) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(String name, String lastName, String phoneNumber, Long chatId, String email, List<Role> role) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.chatId = chatId;
        this.email = email;
        this.role = role;
    }

    public User(String name, String lastName, String phoneNumber, String email, List<Role> role) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }
    public User(String name, String lastName, String phoneNumber, Long chatId, List<Role> role) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
