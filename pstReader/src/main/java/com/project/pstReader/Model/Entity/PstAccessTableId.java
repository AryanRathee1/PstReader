package com.project.pstReader.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PstAccessTableId implements Serializable {

    // many-to-one relation with PstFile Table
    private Integer pstId;

    // one-to-one relation with User Table
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PstAccessTableId that = (PstAccessTableId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(pstId, that.pstId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, pstId);
    }
}
