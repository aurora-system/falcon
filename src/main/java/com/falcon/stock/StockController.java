package com.falcon.stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.Product;
import com.falcon.product.ProductRepository;
import com.falcon.purchase.PurchaseRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class StockController {

    private StockRepository stockRepository;
    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;

    public StockController(
            StockRepository stockRepository
            , PurchaseRepository purchaseRepository
            , ProductRepository productRepository
            , SupplierRepository supplierRepository
            ) {
        this.stockRepository = stockRepository;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/stocks")
    public String availableStocks(Model model) {

        model.addAttribute(this.stockRepository.findAll());
        return "stocks/stocklist";
    }

    @GetMapping("/stocks/{id}/edit")
    public String updateMarkupForm(Model model, @PathVariable long id) {
        model.addAttribute(this.stockRepository.findById(id).orElseGet(Stock::new));
        model.addAttribute(this.stockRepository.findAll());
        return "stocks/stockform";
    }

    @PostMapping("/stocks")
    public String updateMarkup(
            @Valid Stock stock, Errors errors
            , final RedirectAttributes redirect
            , Model model
            ) {
        if (errors.hasErrors()) {
            return "stocks/stocklist";
        }
        this.stockRepository.save(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/stockhistory/{id}")
    public String stockHistory(Model model, @PathVariable long id) {

        // query Purchase, Purchase return, SalesOrder, SalesReturn by stockId
        // extract trans/return date, Stock, unitCost, quantity, compute for updatedCount
        // add to List<StockHistory>
        // sort by transDate/returnDate
        // set to ModelAttribute

        List<StockHistory> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(new StockHistory(
                LocalDate.now(),
                new Product(1, "Name", "Brand", "Descriptioin", "Category",null,null),
                new BigDecimal("100.00"),
                100,
                "IN",
                100
                ));
        model.addAttribute(stockHistoryList);
        return "stocks/history";
    }
}
