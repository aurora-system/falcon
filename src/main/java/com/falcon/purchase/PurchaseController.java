package com.falcon.purchase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.CategoryRepository;
import com.falcon.product.Product;
import com.falcon.product.ProductRepository;
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;
import com.falcon.supplier.SupplierRepository;

import lombok.AllArgsConstructor;

@Controller @AllArgsConstructor
public class PurchaseController {

    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private StockRepository stockRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("/purchases")
    public String listPurchases(Model model) {
        List<Purchase> purchaseList = this.purchaseRepository.findAllByIsDeleted(false);
        Collections.sort(purchaseList,
                (a,b) -> a.getTransDate().isBefore(b.getTransDate()) ? 1 : a.getTransDate().isAfter(b.getTransDate()) ? -1 : 0);
        for (int i = 0; i < purchaseList.size(); i++) {
            System.out.println(purchaseList.get(i).getTransDate());
        }
        model.addAttribute(purchaseList);
        return "purchases/purchaselist";
    }

    @GetMapping("/purchases/new")
    public String newPurchaseForm(Model model) {
        model.addAttribute(this.categoryRepository.findAll());
        model.addAttribute(this.productRepository.findAll());
        model.addAttribute(this.supplierRepository.findAll());
        model.addAttribute(new Purchase());
        return "purchases/purchaseform";
    }

    @GetMapping("/purchases/{id}/edit")
    public String editPurchaseForm(Model model, @PathVariable long id) {
        model.addAttribute(this.productRepository.findAll());
        model.addAttribute(this.supplierRepository.findAll());
        model.addAttribute(this.purchaseRepository.findById(id).orElseGet(Purchase::new));
        return "purchases/purchaseform";
    }

    @GetMapping("/purchases/{id}/delete")
    @Transactional
    public String deletePurchase(Model model, @PathVariable long id, final RedirectAttributes redirect) {
        Purchase purchase = this.purchaseRepository.findById(id).orElseGet(Purchase::new);
        purchase.setDeleted(true);
        this.purchaseRepository.save(purchase);
        Optional<Stock> stock = this.stockRepository.findBySkuAndUnitCost(purchase.getProduct().getSku(), purchase.getUnitCost());
        if (stock.isPresent()) {
            Stock s = stock.get();
            s.setQuantity(s.getQuantity() - purchase.getQuantity());
            this.stockRepository.save(s);
            redirect.addFlashAttribute("message", "Purchase record deleted successfully.");
        } else {
            redirect.addFlashAttribute("message", "Purchase record not found in stocks.");
        }
        return "redirect:/purchases";
    }

    @PostMapping({"/purchases"})
    @Transactional
    public String savePurchase(@Valid Purchase purchase
            , Model model
            , Errors errors
            , final RedirectAttributes redirect) {

        String successMsg = "";
        if (purchase.getId() != 0) {
            successMsg = "Purchase edited successfully.";
        } else {
            successMsg = "New purchase added successfully.";
        }

        if (errors.hasErrors()) {
            model.addAttribute(this.productRepository.findAll());
            model.addAttribute(this.supplierRepository.findAll());
            return "purchases/purchaseform";
        }
        this.purchaseRepository.save(purchase);
        Product product = this.productRepository.findById(purchase.getProduct().getId())
                .orElseGet(Product::new);
        Optional<Stock> stock = this.stockRepository.findBySkuAndUnitCost(product.getSku(), purchase.getUnitCost());
        if (stock.isPresent()) {
            Stock s = stock.get();
            s.setQuantity(s.getQuantity() + purchase.getQuantity());
            this.stockRepository.save(s);
        } else {
            Stock s = new Stock();
            s.setProduct(product);
            s.setSupplier(purchase.getSupplier());
            s.setSku(product.getSku());
            s.setQuantity(purchase.getQuantity());
            s.setUnitCost(purchase.getUnitCost());
            this.stockRepository.save(s);
        }
        redirect.addFlashAttribute("message", successMsg);
        return "redirect:/purchases";
    }
}
