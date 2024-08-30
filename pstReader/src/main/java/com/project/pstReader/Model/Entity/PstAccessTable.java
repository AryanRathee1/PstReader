package com.project.pstReader.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(PstAccessTableId.class)
public class PstAccessTable {

    @Id
    private Integer userId;

    @Id
    private Integer pstId;

    @MapsId(value = "userId")
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;

    @MapsId(value = "pstId")
    @ManyToOne
    @JoinColumn(name = "pst_id")
    private PstFile pstFile;

    @Enumerated(EnumType.STRING)
    private AccessStatus accessStatus;

    private LocalDateTime accessStartTime;
    private LocalDateTime accessEndTime;    //  null if accessType is Permanent

}
