package com.zeljko.gamelibrary.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="user")
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    // user_id
    private Integer id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_games_table",
    joinColumns = {
            @JoinColumn(name="user_id", referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name="games_id", referencedColumnName = "id")
    })

    private Set<Game> games = new HashSet<Game>();


    public Set<Game> addGameToUser(Game game) {
        this.games.add(game);

        return games;
    }

    public void removeGame(Game game){
        games.remove(game);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }


}
