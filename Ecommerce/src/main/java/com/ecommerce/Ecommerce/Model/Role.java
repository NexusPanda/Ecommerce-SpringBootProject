package com.ecommerce.Ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private AppRole roleName;
}
