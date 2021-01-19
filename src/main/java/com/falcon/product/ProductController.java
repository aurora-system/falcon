package com.falcon.product;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.fileupload.FileDTO;
import com.falcon.fileupload.StorageService;

@Controller
public class ProductController {

    private ProductRepository productRepository;
    private StorageService storageService;

    public ProductController(
            ProductRepository productRepository
            ,StorageService storageService
            ) {
        this.productRepository = productRepository;
        this.storageService = storageService;
    }

    @GetMapping({"/products"})
    public String listAllProducts(Model model) {
        Iterable<Product> products = this.productRepository.findAll();
        model.addAttribute("products", products);
        return "product/productlist";
    }

    @GetMapping({"/products/category/{categoryId}"})
    public String listAllProductsByCategory(@PathVariable String categoryId, Model model) {
        Pageable pageOne = PageRequest.of(0, 20);
        Iterable<Product> products = this.productRepository.findAllByCategory(categoryId, pageOne);
        model.addAttribute("products", products);
        return "product/productlist";
    }

    @GetMapping({"/products/category/{categoryId}/new"})
    public String newProductsByCategory(@PathVariable String categoryId, Model model) {
        Pageable pageOne = PageRequest.of(0, 20);
        Iterable<Product> products = this.productRepository.findAllByCategory(categoryId, pageOne);
        model.addAttribute("products", products);
        Product p = new Product();
        model.addAttribute("product", p);
        return "product/productform";
    }

    @GetMapping({"/products/{productId}"})
    public String productDetails(@PathVariable long productId, Model model) {
        Optional<Product> product = this.productRepository.findById(productId);
        Product p = product.orElseGet(Product::new);
        model.addAttribute("product", p);
        return "product/product";
    }

    @GetMapping({"/products/{productId}/edit"})
    public String editProductDetails(@PathVariable long productId, Model model) {
        Optional<Product> product = this.productRepository.findById(productId);
        Product p = product.orElseGet(Product::new);
        model.addAttribute("product", p);
        return "product/productform";
    }

    @GetMapping({"/products/new"})
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/productform";
    }

    @PostMapping({"/products"})
    @Transactional
    public String saveProduct(@Valid Product product
            , Errors errors
            , final RedirectAttributes redirect
            , Model model
            ) {
        if (errors.hasErrors()) {
            return "product/productform";
        }

        String successMsg = "";
        if (product.getId() != 0) {
            successMsg = "Product edited successfully.";
        } else {
            successMsg = "New product added successfully.";
        }
        MultipartFile[] photoFiles = product.getPhotoFiles();
        if (photoFiles.length > 0 && !photoFiles[0].getOriginalFilename().isEmpty()) {
            for (int i = 0; i < photoFiles.length; i++) {
                try {
                    String fileExt = photoFiles[i].getOriginalFilename()
                            .substring(photoFiles[i].getOriginalFilename().lastIndexOf("."));
                    String fileName = "product_photo_" + product.getName() + "_" + i + "_" + System.currentTimeMillis()
                    + fileExt;
                    FileDTO fileDTO = this.storageService.uploadFile(photoFiles[i], fileName);
                    product.getPhotos().add(fileDTO.getDownloadUri());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.productRepository.save(product);
        redirect.addFlashAttribute("message", successMsg);
        return "redirect:/products";
    }
}
