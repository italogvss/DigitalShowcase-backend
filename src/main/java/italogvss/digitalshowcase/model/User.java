package italogvss.digitalshowcase.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "user")
@Entity(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean isActive;

    @Column( length = 60, nullable = true)
    private String cpf;

    @Column(length = 60, nullable = true)
    private String telephone;

    @Column(length = 60, nullable = true)
    private String email;

    @Column(length = 60, nullable = true)
    private String city;

    @Column(length = 60, nullable = true)
    private String state;

    @Column(length = 100, nullable = true)
    private String adress;

    @Column(length = 60, nullable = true)
    private LocalDate validUntil;

    @JoinColumn(name = "owner_id", nullable = true)
    @ManyToOne
    private User owner;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (this.role) {
            case ADMIN:
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_MANAGER"));
            case MANAGER:
            return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"), new SimpleGrantedAuthority("ROLE_USER"));
            case USER:
            default:
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    
    public User(String username, String name, String password, UserRole role, Boolean isActive,
            String cpf, String telephone, String email, String city, String state, String adress,
            LocalDate validUntil, User owner) {

        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.cpf = cpf;
        this.telephone = telephone;
        this.email = email;
        this.city = city;
        this.state = state;
        this.adress = adress;
        this.validUntil = validUntil;
        this.owner = owner;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    

    public User() {
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    

}
