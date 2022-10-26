package com.emb.ecom.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.emb.ecom.dto.ProductDto;
import com.emb.ecom.model.Category;
import com.emb.ecom.model.Product;
import com.emb.ecom.service.CategoryService;
import com.emb.ecom.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	public static String uploadDir = System.getProperty("user.dir")+ "/src/main/resources/static/productImages";

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	{
		BasicConfigurator.configure();
	}
	
	@GetMapping("/dashboard")
    public String adminHome(){
        return "admin/dashboard";
    }
	
	//CATEGORIES
	@GetMapping("/categories")
	public String getCat(Model model) {
		List<Category> categorylist = categoryService.getAllCategory();
		model.addAttribute("categories", categorylist);
		model.addAttribute("size", categorylist.size());
		return "admin/categories";
	}
	
	@GetMapping("/categories/add")
	public String getCatAdd(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/categoriesAdd";
	}
	
	@PostMapping("/categories/add")
	public String postCatAdd(@Valid @ModelAttribute("category")Category category,
			BindingResult bindingResult, Model model) {
		try {
	        if(bindingResult.hasErrors()){
	        	model.addAttribute("bindingResult", bindingResult);
	        	//model.addAttribute("successMessage", "Fill in all details!");
	            return "admin/categoriesAdd";
	        }
	        categoryService.addCategory(category);
	    	model.addAttribute("successMessage", "Category saved successfully!");
            return "admin/categoriesAdd";
		}catch(DataIntegrityViolationException e) {
    		log.error(e);
			model.addAttribute("successMessage", "Category already exists!");
        }catch (Exception e){
        	log.error(e);
            model.addAttribute("successMessage", "Something went wrong!");
        }
        return "admin/categoriesAdd";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCat(@PathVariable int id, Model model ) {
		try{
			categoryService.removeCategoryById(id);
			model.addAttribute("successMessage", "Deleted successfully!");
		}catch (Exception e){
        	log.error(e);
            model.addAttribute("successMessage", "Failed to delete!");
        }
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "admin/categoriesAdd";
		}else {
			throw new RuntimeException("Category not found with Id: " + id);
		}
	}
	
	
	//PRODUCTS
	@GetMapping("/products")
	public String getPro(Model model) {
		List<Product> productlist = productService.getAllProduct();
		model.addAttribute("products", productlist);
		model.addAttribute("size", productlist.size());
		return "admin/products";
	}
	
	@GetMapping("/products/add")
	public String getProAdd(Model model) {
		ProductDto productDto = new ProductDto();
		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("productDto", productDto);
		model.addAttribute("categories", categories);		
		return "admin/productsAdd";
	}
	
	@PostMapping("/products/add")
	public String postProAdd(@ModelAttribute("productDto")ProductDto productDto,
			BindingResult bindingResult, 
			@RequestParam ("productImage")MultipartFile file,
			@RequestParam ("imgName")String imgName,
			Model model) throws IOException{
		
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()).get());
		product.setPrice(productDto.getPrice());
		product.setCurrentQuantity(productDto.getCurrentQuantity());
		product.setDescription(productDto.getDescription());
		String imageUUID;
		if(!file.isEmpty()){
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);

		try {
        	if(bindingResult.hasErrors()){
	        	model.addAttribute("bindingResult", bindingResult);
	        	//model.addAttribute("successMessage", "Fill in all details!");
	        	return "admin/productsAdd";
	        }
        	productService.addProduct(product);
        	model.addAttribute("successMessage", "Product saved successfully");
        	return "admin/productsAdd";
		}catch(DataIntegrityViolationException e) {
        	log.error(e);
			model.addAttribute("successMessage", "Product already exists!");
        }catch (Exception e){
        	log.error(e);
            model.addAttribute("successMessage", "Something went wrong!");
        }
		return "admin/productsAdd";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deletePro(@PathVariable long id, Model model) {
		try{
			productService.removeProductById(id);
			model.addAttribute("successMessage", "Deleted successfully!");
		}catch (Exception e){
			log.error(e);
			model.addAttribute("successMessage", "Failed to delete!");
		}
		return "redirect:/admin/products";
		}
	
	@GetMapping("/product/update/{id}")
	public String updatePro(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		List<Category> categories = categoryService.getAllCategory();
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setCategoryId((product.getCategory().getId()));
		productDto.setPrice(product.getPrice());
		productDto.setCurrentQuantity(product.getCurrentQuantity());
		productDto.setDescription(product.getDescription());
		productDto.setImageName(product.getImageName());
		model.addAttribute("categories", categories);
		model.addAttribute("productDto", productDto);
		return "admin/productsAdd";
	}	
}
