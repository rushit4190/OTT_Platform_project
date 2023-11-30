package com.NextLeap.OTT_platform_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;


@Entity
@Data
@Table(name = "cast_crew", schema = "OTT_project", uniqueConstraints = @UniqueConstraint(name = "unique_name_role", columnNames = {"name", "role"}))
@NoArgsConstructor
@AllArgsConstructor
public class CastCrew {
    @Id
    @UuidGenerator
    private String Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;

    private String description;

}
