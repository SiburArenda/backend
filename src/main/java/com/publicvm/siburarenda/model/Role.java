package com.publicvm.siburarenda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Simple domain object that represents application user's role - ADMIN, USER, etc.
 *
 * @author Valera
 * @version 1.0
 */

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String toString() {
        return name;
    }
}
