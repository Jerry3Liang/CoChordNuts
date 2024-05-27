package com.ispan.recordshop.cochordnuts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
public class Favorite {

    @EmbeddedId
    private FavoriteId favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productStyleId")
    private ProductStyle productStyle;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    public Favorite() {
    }

    public FavoriteId getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(FavoriteId favoriteId) {
        this.favoriteId = favoriteId;
    }

    public ProductStyle getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(ProductStyle productStyle) {
        this.productStyle = productStyle;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
