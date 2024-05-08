package com.ispan.recordshop.cochordnuts.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CartId implements Serializable {

	public CartId() {
	}

	@Column(name = "fk_member_id", nullable = false)
	private Integer memberId;
	
	@Column(name = "fk_product_id", nullable = false)
	private Integer productId;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	
	
	public CartId(Integer memberId, Integer productId) {
		super();
		this.memberId = memberId;
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return Objects.equals(memberId, other.memberId) && Objects.equals(productId, other.productId);
	}

	
	
}
