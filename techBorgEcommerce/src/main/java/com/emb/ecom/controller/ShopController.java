package com.emb.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.emb.ecom.global.GlobalData;
import com.emb.ecom.model.Category;
import com.emb.ecom.model.Product;
import com.emb.ecom.service.CategoryService;
import com.emb.ecom.service.ProductService;

@Controller
//@RequestMapping("/shop")
public class ShopController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	private static final Logger log = LogManager.getLogger(ShopController.class);
	
	{
		BasicConfigurator.configure();
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		List<Category> categoryView = categoryService.getAllCategory();
		List<Product> productView = productService.getAllProduct();
		model.addAttribute("categories", categoryView);
		model.addAttribute("products", productView);
		model.addAttribute("categorySize", categoryView.size());
		model.addAttribute("productSize", productView.size());
		//model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		//model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";
	}
	
	@GetMapping("/shop/product/{id}")
	public String shopByProduct(Model model, @PathVariable int id) {
		model.addAttribute("products", productService.getProductById(id).get());
		//model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		//model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}
}
