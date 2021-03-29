package com.falcon.purchasereturn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.ProductRepository;
import com.falcon.purchase.Purchase;
import com.falcon.purchase.PurchaseRepository;
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class PurchaseReturnController {

    private PurchaseReturnRepository purchaseReturnRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private StockRepository stockRepository;
    private PurchaseRepository purchaseRepository;

    public PurchaseReturnController(
            PurchaseReturnRepository purchaseReturnRepository
            , ProductRepository productRepository
            , SupplierRepository supplierRepository
            , StockRepository stockRepository
            , PurchaseRepository purchaseRepository
            ) {
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.stockRepository = stockRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/purchasereturns")
    public String listPurchaseReturns(Model model) {
        List<PurchaseReturn> purchaseReturnList = new ArrayList<>();
        this.purchaseReturnRepository.findAllByIsDeleted(false).forEach(purchaseReturnList::add);
        Collections.sort(purchaseReturnList,
                (a,b) -> a.getReturnDate().isBefore(b.getReturnDate()) ? 1 : a.getReturnDate().isAfter(b.getReturnDate()) ? -1 : 0);
        model.addAttribute(purchaseReturnList);
        return "returns/purchasereturnlist";
    }

    @GetMapping("/purchasereturns/new")
    public String newPurchaseReturnForm(Model model) {
        model.addAttribute(this.productRepository.findAll());
        model.addAttribute(this.supplierRepository.findAll());
        model.addAttribute(new PurchaseReturn());
        return "returns/purchasereturnform";
    }

    @GetMapping("/purchasereturns/{id}/edit")
    public String editPurchaseReturn(Model model, @PathVariable long id) {
        model.addAttribute(this.productRepository.findAll());
        model.addAttribute(this.supplierRepository.findAll());
        model.addAttribute(this.purchaseReturnRepository.findById(id).orElseGet(PurchaseReturn::new));
        return "returns/purchasereturnform";
    }

    @GetMapping("/purchasereturns/{id}/delete")
    @Transactional
    public String deletePurchaseReturn(Model model, @PathVariable long id, final RedirectAttributes redirect) {
        PurchaseReturn purchaseReturn = this.purchaseReturnRepository.findById(id).orElseGet(PurchaseReturn::new);
        purchaseReturn.setDeleted(true);
        this.purchaseReturnRepository.save(purchaseReturn);
        Optional<Stock> stock = this.stockRepository.findBySkuAndUnitCost(purchaseReturn.getProduct().getSku(), purchaseReturn.getUnitCost());
        if (stock.isPresent()) {
            Stock s = stock.get();
            s.setQuantity(s.getQuantity()+purchaseReturn.getQuantity());
            this.stockRepository.save(s);
            redirect.addFlashAttribute("message", "Purchase Return record deleted successfully.");
        } else {
            redirect.addFlashAttribute("message", "No purchase record found.");
            //TODO: Is this else block needed?
            // Product product = this.productRepository.findById(purchaseReturn.getProduct().getId())
            //         .orElseGet(Product::new);
            // Stock s = new Stock();
            // s.setProduct(product);
            // s.setQuantity(purchaseReturn.getQuantity());
            // s.setUnitCost(purchaseReturn.getUnitCost());
            // this.stockRepository.save(s);
        }
        return "redirect:/purchases";
    }

    @PostMapping({"/purchasereturns"})
    @Transactional
    public String savePurchaseReturn(
            @Valid PurchaseReturn purchaseReturn, Errors errors
            , final RedirectAttributes redirect
            , Model model
            ) {
        if (errors.hasErrors()) {
            model.addAttribute(this.productRepository.findAll());
            model.addAttribute(this.supplierRepository.findAll());
            return "returns/purchasereturnform";
        }
        this.purchaseReturnRepository.save(purchaseReturn);
        Optional<Stock> stock = this.stockRepository.findBySkuAndUnitCost(purchaseReturn.getProduct().getSku(), purchaseReturn.getUnitCost());
        if (stock.isPresent()) {
            Stock s = stock.get();
            s.setQuantity(s.getQuantity() - purchaseReturn.getQuantity());
            this.stockRepository.save(s);
        } else {
            redirect.addFlashAttribute("message", "Stock is not found in system.");
            return "redirect:/purchasereturns";
        }
        redirect.addFlashAttribute("message", "New Purchase Return added successfully.");
        return "redirect:/purchasereturns";
    }

    @GetMapping("/purchasereturns/by-supplier/{id}")
    public ResponseEntity<List<Purchase>> findAllPurchasesBySupplierId(@PathVariable long id) {
        List<Purchase> purchases = this.purchaseRepository.findAllBySupplierId(id);
        return ResponseEntity.ok(purchases);
    }
}
