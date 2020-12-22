package com.falcon.product;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.fileupload.FileDTO;
import com.falcon.fileupload.FileInfo;
import com.falcon.fileupload.FileInfoRepository;
import com.falcon.fileupload.StorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {

	private ProductRepository productRepository;
	private ProductCategoryRepository productCategoryRepository;
	private FileInfoRepository fileInfoRepository;
	private StorageService storageService;

	public ProductController(
			ProductRepository productRepository
			,ProductCategoryRepository productCategoryRepository
			,FileInfoRepository fileInfoRepository
			,StorageService storageService
			) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
		this.fileInfoRepository = fileInfoRepository;
		this.storageService = storageService;
	}
	
	@GetMapping({"/productcategories"})
	public String listAllProductCategories(Model model) {
		Iterable<ProductCategory> categories = productCategoryRepository.findAll();
		model.addAttribute("categories", categories);
		return "product/categorylist";
	}
	
	@GetMapping({"/productcategories/{categoryId}"})
	public String categoryDetails(@PathVariable long categoryId, Model model) {
		ProductCategory cat = productCategoryRepository.findById(categoryId)
				.orElse(new ProductCategory(""));
		model.addAttribute("category", cat);
		return "product/category";
	}
	
	@GetMapping({"/productcategories/{categoryId}/edit"})
	public String editCategoryDetails(@PathVariable long categoryId, Model model) {
		ProductCategory cat = productCategoryRepository.findById(categoryId)
				.orElse(new ProductCategory(""));
		model.addAttribute("productCategory", cat);
		return "product/categoryform";
	}
	
	@GetMapping({"/productcategories/new"})
	public String newProductCategoryForm(Model model) {
		model.addAttribute("productCategory", new ProductCategory());
		return "product/categoryform";
	}
	
	@PostMapping({"/productcategories"})
	public String saveProductCategory(
			@Valid ProductCategory product, Errors errors
			, final RedirectAttributes redirect) {
		if (errors.hasErrors()) {
			return "product/categoryform";
		}
		productCategoryRepository.save(product);
		redirect.addFlashAttribute("message", "New Product Category saved successfully.");
		return "redirect:/productcategories";
	}
	
	@GetMapping({"/products"})
	public String listAllProducts(Model model) {
		Iterable<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "product/productlist";
	}
	
	@GetMapping({"/products/category/{categoryId}"})
	public String listAllProductsByCategory(@PathVariable long categoryId, Model model) {
		Pageable pageOne = PageRequest.of(0, 20);
		Iterable<Product> products = productRepository.findAllByCategoryId(categoryId, pageOne);
		model.addAttribute("products", products);
		return "product/productlist";
	}
	
	@GetMapping({"/products/category/{categoryId}/new"})
	public String newProductsByCategory(@PathVariable long categoryId, Model model) {
		Pageable pageOne = PageRequest.of(0, 20);
		Iterable<Product> products = productRepository.findAllByCategoryId(categoryId, pageOne);
		model.addAttribute("products", products);
		Product p = new Product();
		p.setCategory(productCategoryRepository.findById(categoryId).orElseGet(() -> new ProductCategory()));
		model.addAttribute("product", p);
		model.addAttribute("categories", productCategoryRepository.findAll());
		return "product/productform";
	}
	
	@GetMapping({"/products/{productId}"})
	public String productDetails(@PathVariable long productId, Model model) {
		Optional<Product> product = productRepository.findById(productId);
		Product p = product.orElseGet(() ->new Product());
		model.addAttribute("product", p);
		model.addAttribute("imageList", fileInfoRepository.findAllByProductId(productId));
		return "product/product";
	}
	
	@GetMapping({"/products/{productId}/edit"})
	public String editProductDetails(@PathVariable long productId, Model model) {
		Optional<Product> product = productRepository.findById(productId);
		Product p = product.orElseGet(() ->new Product());
		model.addAttribute("product", p);
		model.addAttribute("categories", productCategoryRepository.findAll());
		model.addAttribute("imageList", fileInfoRepository.findAllByProductId(productId));
		return "product/productform";
	}
	
	@GetMapping({"/products/new"})
	public String newProductForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", productCategoryRepository.findAll());
		return "product/productform";
	}
	
	@PostMapping({"/products"})
	public String saveProduct(
			@Valid Product product, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			model.addAttribute("categories", productCategoryRepository.findAll());
			return "product/productform";
		}
		productRepository.save(product);
		redirect.addFlashAttribute("message", "New Product added successfully.");
		return "redirect:/products";
	}
	
	@PostMapping("/products/upload-photo/{productId}")
	@Transactional
	public String uploadFile(@RequestParam("file") MultipartFile file,
    		@PathVariable long productId
    		, Model model
    		, HttpServletRequest request
    		) throws Exception {
            log.info("REST request to upload file");
            //upload files
            FileDTO fileDTO = storageService.uploadFile(file);
            String username = request.getUserPrincipal().getName();
            fileInfoRepository.save(toFileInfo(fileDTO, username, productId));
            return "redirect:/products/" + productId;
	}
	
	private FileInfo toFileInfo(FileDTO fileDTO, String username, long productId) {
		return new FileInfo(0L,
				fileDTO.getFilename(),
				fileDTO.getContentType(),
				fileDTO.getDownloadUri(),
				fileDTO.getFileSize(),
				LocalDate.now(),
				username,
				productId
				);
	}
}
