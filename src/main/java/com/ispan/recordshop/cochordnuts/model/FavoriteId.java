package com.ispan.recordshop.cochordnuts.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class FavoriteId implements Serializable {
    private Integer memberId;

    private Integer productStyleId;

    public FavoriteId() {
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getProductStyleId() {
        return productStyleId;
    }

    public void setProductStyleId(Integer productStyleId) {
        this.productStyleId = productStyleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productStyleId, memberId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FavoriteId other = (FavoriteId) obj;
        return Objects.equals(productStyleId, other.productStyleId) && Objects.equals(memberId, other.memberId);
    }
}
