package com.gayathri.projects.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name= "HR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String Username;

    @Column(nullable=false)
    private String Password;

    @Column(nullable=false)
    private String Email;

    //fetch the roles immediately whenever the column is called
    @ElementCollection(fetch = FetchType.EAGER)
    // We store the collection as below mentioned table
    @CollectionTable(
            name = "role",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "role")
    private Set<String> roles;

    private boolean enabled = true;


}