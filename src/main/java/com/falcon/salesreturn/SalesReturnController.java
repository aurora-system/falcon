package com.falcon.salesreturn;

import java.util.ArrayList;
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

import com.falcon.product.ProductRepository;
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class SalesReturnController {

    private SalesReturnRepository salesReturnRepository;
    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;

    public SalesReturnController(
            SalesReturnRepository salesReturnRepository
            , StockRepository stockRepository
            , ProductRepository productRepository
            , SupplierRepository supplierRepository
            ) {

        this.salesReturnRepository = salesReturnRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/salesreturns")
    public String listSalesReturns(Model model) {
        List<SalesReturn> salesReturnList = new ArrayList<>();
        this.salesReturnRepository.findAll().forEach(salesReturnList::add);
        Collections.sort(salesReturnList,
                (a,b) -> a.getReturnDate().isBefore(b.getReturnDate()) ? 1 : a.getReturnDate().isAfter(b.getReturnDate()) ? -1 : 0);
        model.addAttribute(salesReturnList);
        return "returns/salesreturnlist";
    }

    @GetMapping("/salesreturns/new")
    public String newSalesReturnForm(Model model) {
        model.addAttribute(this.stockRepository.findAll());
        model.addAttribute(new SalesReturn());
        return "returns/salesreturnform";
    }

    @GetMapping("/salesreturns/{id}/edit")
    public String editSalesReturnForm(Model model, @PathVariable long id) {
        model.addAttribute(this.stockRepository.findAll());
        model.addAttribute(this.salesReturnRepository.findById(id).orElseGet(SalesReturn::new));
        return "returns/salesorderform";
    }

    @PostMapping("/salesreturns")
    @Transactional
    public String saveSalesReturn(
            @Valid SalesReturn salesReturn
            , Errors errors
            , final RedirectAttributes redirect
            , Model model
            ) {
        if (errors.hasErrors()) {
            model.addAttribute(this.productRepository.findAll());
            model.addAttribute(this.supplierRepository.findAll());
            return "returns/salesorderform";
        }
        this.salesReturnRepository.save(salesReturn);
        Optional<Stock> stock = this.stockRepository.findById(salesReturn.getStock().getId());
        if (stock.isPresent()) {
            Stock s = stock.get();
            s.setQuantity(s.getQuantity() + salesReturn.getQuantity());
            this.stockRepository.save(s);
        } else {
            redirect.addFlashAttribute("message", "Stock is not found in system.");
            return "redirect:/purchasereturns";
        }
        redirect.addFlashAttribute("message", "New Sales Return added successfully.");
        return "redirect:/salesreturns";
    }
}
