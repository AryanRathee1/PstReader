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
@Embeddable
public class PstAccessTable {

    @EmbeddedId
    private PstAccessTableId pstAccessTableId;

    @MapsId(value = "userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId(value = "pstId")
    @ManyToOne
    @JoinColumn(name = "pst_id")
    private PstFile pstFile;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    private LocalDateTime accessStartTime;
    private LocalDateTime accessEndTime;    //  null if accessType is Permanent

}
