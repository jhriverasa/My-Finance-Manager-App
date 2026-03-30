package com.myfinancemanager.api.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * User domain entity representing a registered user in the system.
 * This is a pure POJO without any framework dependencies.
 */
public class User {

    private final Long id;
    private final String email;
    private final String passwordHash;
    private final String firstName;
    private final String lastName;
    private final Instant createdAt;
    private final Instant updatedAt;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(User user) {
        return new Builder()
                .id(user.id)
                .email(user.email)
                .passwordHash(user.passwordHash)
                .firstName(user.firstName)
                .lastName(user.lastName)
                .createdAt(user.createdAt)
                .updatedAt(user.updatedAt);
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {
        private Long id;
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public User build() {
            return new User(this);
        }
    }
}