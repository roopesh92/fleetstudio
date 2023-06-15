package com.fleetstudio.county.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="county")
public class County {

    public County() {
        super();
    }

    public County(String fips, String state, String name) {
        this.fips = fips;
        this.state = state;
        this.name = name;
    }
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/
    @Id
    private String fips;
    private String state;
    private String name;
    
}
