package com.emb.ecom.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class ProductDto {

	private Long id;
	
	@Pattern(regexp = "^[a-zA-Z0-9 ]{3,40}+$", message = "Invalid product name!(3-40 characters)")
	private String name;
	
	private int categoryId;
	
	private double price;
	
	private int currentQuantity;
	
    @Length(min = 3, max = 100, message = "Invalid product description!(3-300 characters)")
	private String description;
	
	private String imageName;

	public ProductDto(){	
		
	}

	public ProductDto(Long id, String name, int categoryId, double price, int currentQuantity, String description,
			String imageName) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.price = price;
		this.currentQuantity = currentQuantity;
		this.description = description;
		this.imageName = imageName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(int i) {
		this.currentQuantity = i;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", price=" + price
				+ ", currentQuantity=" + currentQuantity + ", description=" + description + ", imageName=" + imageName
				+ "]";
	}
}
