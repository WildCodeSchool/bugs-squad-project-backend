package com.templateproject.api.entity;

import java.util.Collection;
import com.templateproject.api.entity.LinksCollection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, message = "Username must be at least 2 characters long")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;

    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> authorities = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "origin")
    private Origin origin;

    public Origin getOrigin() {
        return origin;
    }
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ToDoList> toDoLists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LinksCollection> collections;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RssFeed> rssFeeds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dashboard_id", referencedColumnName = "id")
    private Dashboard dashboard;

    public User(String username, String encodedPassword, String email, Set<Role> roles) {
        this.username = username;
        this.password = encodedPassword;
        this.email = email;
        this.authorities = roles;
    }

    public User(String username, String email, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.authorities = roles;
    }

    public Long getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    public String getUserStringName() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

       public List<ToDoList> getToDoLists() {
      return toDoLists;
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
      this.toDoLists = toDoLists;
    }

    public List<LinksCollection> getCollections() {
      return collections;
    }

    public void setCollections(List<LinksCollection> collections) {
      this.collections = collections;
    }

    public List<RssFeed> getRssFeeds() {
      return rssFeeds;
    }

    public void setRssFeeds(List<RssFeed> rssFeeds) {
      this.rssFeeds = rssFeeds;
    }


}
