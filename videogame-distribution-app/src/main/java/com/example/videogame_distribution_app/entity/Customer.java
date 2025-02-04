package com.example.videogame_distribution_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Customer")
public class Customer extends User {
    private String membershipType;
    private int points;

    public String getMembershipType() {
        return membershipType;
    }

    public int getPoints() {
        return points;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
