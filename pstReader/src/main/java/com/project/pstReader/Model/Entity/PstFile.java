package com.project.pstReader.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PstFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name= "users_sequence",allocationSize = 1)
    private Integer pstId;

    @Column(nullable = false)
    private Integer uploadedBy;
    private Integer managerId;

    @OneToMany(mappedBy = "pstFile")
    private List<PstAccessTable> pstAccessTables;

}
