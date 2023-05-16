package com.multi.gazee.product;

public class ProductVO {
	int productId;
	String memberId;
	String category;
	String productName;
	String productContent;
	int price;
	int productViews;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductContent() {
		return productContent;
	}
	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getProductViews() {
		return productViews;
	}
	public void setProductViews(int productViews) {
		this.productViews = productViews;
	}
	
	@Override
	public String toString() {
		return "ProductVO [productId=" + productId + ", memberId=" + memberId + ", category=" + category
				+ ", productName=" + productName + ", productContent=" + productContent + ", price=" + price
				+ ", productViews=" + productViews + "]";
	}
	
	
}
