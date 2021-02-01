package com.falcon.stock;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.falcon.purchase.Purchase;
import com.falcon.purchase.PurchaseRepository;
import com.falcon.purchasereturn.PurchaseReturn;
import com.falcon.purchasereturn.PurchaseReturnRepository;
import com.falcon.salesorder.SalesOrderRepository;
import com.falcon.salesreturn.SalesReturn;
import com.falcon.salesreturn.SalesReturnRepository;
import com.falcon.supplier.Supplier;
import com.falcon.supplier.SupplierRepository;

@Controller
public class StockController {

    private StockRepository stockRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseReturnRepository purchaseReturnRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private SalesOrderRepository salesOrderRepository;
    private SalesReturnRepository salesReturnRepository;

    public StockController(StockRepository stockRepository
            , PurchaseRepository purchaseRepository
            , PurchaseReturnRepository purchaseReturnRepository
            , ProductRepository productRepository
            , SupplierRepository supplierRepository
            , SalesOrderRepository salesOrderRepository
            , SalesReturnRepository salesReturnRepository
            ) {
        this.stockRepository = stockRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.salesOrderRepository =salesOrderRepository;
        this.salesReturnRepository = salesReturnRepository;
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
        List<StockHistory> stockHistoryList = new ArrayList<>();

        // query Purchase, Purchase return, SalesOrder, SalesReturn by stockId
        // extract trans/return date, Stock, unitCost, quantity, compute for updatedCount
        // add to List<StockHistory>
        // sort by transDate/returnDate
        // set to ModelAttribute
        Stock stock = this.stockRepository.findById(id).orElseGet(Stock::new);
        Product product = stock.getProduct();
        Supplier supplier = stock.getSupplier();
        List<Purchase> purchases = this.purchaseRepository.findAllByProductIdAndSupplierIdAndUnitCost(product.getId(),
                supplier.getId(), stock.getUnitCost());
        List<PurchaseReturn> purchaseReturns = this.purchaseReturnRepository.findAllByProductIdAndSupplierIdAndUnitCost(product.getId(),
                supplier.getId(), stock.getUnitCost());
        List<SalesOrderProjection> salesOrdersProjection = this.salesOrderRepository.findHistoryByStockId(stock.getId());
        //List<SalesOrder> salesOrders = this.salesOrderRepository.findAllByStockId(stock.getId());
        List<SalesReturn> salesReturns = salesReturnRepository.findAllByStockId(stock.getId());

        List<StockHistory> purchasesHistory = purchases.stream().map(p -> new StockHistory(
                p.getTransDate(),
                p.getProduct(),
                p.getUnitCost(),
                p.getQuantity(),
                "IN",
                0
                )).collect(toList());

        List<StockHistory> purchaseReturnsHistory = purchaseReturns.stream().map(pr -> new StockHistory(
                pr.getReturnDate(),
                pr.getProduct(),
                pr.getUnitCost(),
                pr.getQuantity()*-1,
                "OUT",
                0
                )).collect(toList());

        List<StockHistory> salesOrdersHistory = salesOrdersProjection.stream().map(sop -> new StockHistory(
                sop.getTransDate(),
                product,
                sop.getUnitCost(),
                sop.getQuantity()*-1,
                "OUT",
                0
                )).collect(toList());
        
        List<StockHistory> salesReturnHistory = salesReturns.stream().map(sr -> new StockHistory(
                sr.getReturnDate(),
                sr.getStock().getProduct(),
                sr.getStock().getUnitCost(),
                sr.getQuantity(),
                "IN",
                0
                )).collect(toList());

        stockHistoryList.addAll(purchasesHistory);
        stockHistoryList.addAll(purchaseReturnsHistory);
        stockHistoryList.addAll(salesOrdersHistory);
        stockHistoryList.addAll(salesReturnHistory);

        // TODO: sort by date and compute for updated count
        Collections.sort(stockHistoryList,
                (a,b) -> a.getTransDate().isBefore(b.getTransDate()) ? -1 : a.getTransDate().isAfter(b.getTransDate()) ? 1 : 0);

        long updatedCount = stockHistoryList.stream().collect(Collectors.summingLong(StockHistory::getQuantity));
        model.addAttribute(stockHistoryList);
        model.addAttribute("updatedCount",updatedCount);
        return "stocks/history";
    }
}
