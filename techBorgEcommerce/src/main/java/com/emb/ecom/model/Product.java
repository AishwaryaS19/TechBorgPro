package com.emb.ecom.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.emb.ecom.model.Category;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Pattern(regexp = "^[a-zA-Z0-9 ]{3,40}+$", message = "Invalid product name!(3-40 characters)")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)		//,optional= false
	@JoinColumn(name = "category_id", referencedColumnName = "category_id") //,nullable = false
	private Category category;
	
	private double price;
	
	private int currentQuantity;
	
    @Length(min = 3, max = 100, message = "Invalid product description!(3-300 characters)")
	private String description;
	
	private String imageName;

	
	public Product() {
		
	}

	public Product(Long id, String name, Category category, double price, int currentQuantity, String description,
			String imageName) {
		this.id = id;
		this.name = name;
		this.category = category;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", currentQuantity="
				+ currentQuantity + ", description=" + description + ", imageName=" + imageName + "]";
	}
}
