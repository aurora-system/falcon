package com.falcon.report;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falcon.product.Product;
import com.falcon.product.ProductRepository;
import com.falcon.purchase.Purchase;
import com.falcon.purchase.PurchaseRepository;
import com.falcon.purchasereturn.PurchaseReturn;
import com.falcon.purchasereturn.PurchaseReturnRepository;
import com.falcon.salesorder.SalesOrder;
import com.falcon.salesorder.SalesOrderItem;
import com.falcon.salesorder.SalesOrderRepository;
import com.falcon.salesreturn.SalesReturn;
import com.falcon.salesreturn.SalesReturnRepository;
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;

@Controller
public class ReportController {

    private ProductRepository productRepository;
    private SalesOrderRepository salesOrderRepository;
    private SalesReturnRepository salesReturnRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseReturnRepository purchaseReturnRepository;
    private StockRepository stockRepository;

    public ReportController(
            ProductRepository productRepository
            ,SalesOrderRepository salesOrderRepository
            ,SalesReturnRepository salesReturnRepository
            ,PurchaseRepository purchaseRepository
            ,PurchaseReturnRepository purchaseReturnRepository
            ,StockRepository stockRepository
            ) {
        this.productRepository = productRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.salesReturnRepository = salesReturnRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.stockRepository = stockRepository;
    }

    @GetMapping({"/reports"})
    public String showReports(Model model) {
        return "report/reports";
    }
    
    @GetMapping("/reports/dailysales")
    public String listSalesToday(Model model, @RequestParam(value="d", defaultValue="today", required=false) String d) {
        
        LocalDate datePicked = ("today".equalsIgnoreCase(d)) ? LocalDate.now() : LocalDate.parse(d);
        
        List<DailyDto> dailySales = new ArrayList<DailyDto>();
        Iterable<SalesOrder> salesOrders = this.salesOrderRepository.findByTransDate(datePicked);
        
        for (SalesOrder so : salesOrders) {
            for (SalesOrderItem soi : so.getItems()) {
                Double profit = soi.getNetSellingPrice().doubleValue() - (soi.getStock().getUnitCost().multiply(new BigDecimal(soi.getQuantity())).doubleValue());
                dailySales.add(new DailyDto(so.getInvoiceNumber()
                        , so.getTransDate(), soi.getStock(), null, "Sales", soi.getQuantity()
                        , soi.getNetSellingPrice(), new BigDecimal(profit)));
            }
        }
        
        model.addAttribute("datePicked", datePicked);
        model.addAttribute("dailySales", dailySales);
        return "report/dailysales";
    }
    
    @GetMapping("/reports/dailytotals")
    public String listTotalsToday(Model model, @RequestParam(value="d", defaultValue="today", required=false) String d) {
        LocalDate datePicked = ("today".equalsIgnoreCase(d)) ? LocalDate.now() : LocalDate.parse(d);
        
        List<DailyDto> dailyTotals = new ArrayList<DailyDto>();
        Iterable<SalesOrder> salesOrders = this.salesOrderRepository.findByTransDateAndStatus(datePicked, "PROCESSED");
        Iterable<SalesReturn> salesReturns = this.salesReturnRepository.findByReturnDate(datePicked);
        Iterable<Purchase> purchases = this.purchaseRepository.findAllByTransDate(datePicked);
        Iterable<PurchaseReturn> purchaseReturns = this.purchaseReturnRepository.findByReturnDate(datePicked);
        
        for (SalesOrder so : salesOrders) {
            for (SalesOrderItem soi : so.getItems()) {
                dailyTotals.add(new DailyDto(so.getInvoiceNumber()
                        , so.getTransDate(), soi.getStock(), soi.getStock().getProduct(), "Sales", soi.getQuantity()
                        , soi.getNetSellingPrice(), null));
            }
        }
        
        for (SalesReturn sr : salesReturns) {
                dailyTotals.add(new DailyDto(sr.getInvoiceNumber()
                        , sr.getReturnDate(), sr.getStock(), sr.getStock().getProduct(), "Returns (Sales)", sr.getQuantity()
                        , sr.getTotalAmount(), null));
        }
        
        for (Purchase p : purchases) {
            dailyTotals.add(new DailyDto(p.getPurchaseNumber()
                    , p.getTransDate(), null, p.getProduct(), "Purchase", p.getQuantity()
                    , p.getTotalAmount(), null));
        }
        
        for (PurchaseReturn pr : purchaseReturns) {
            dailyTotals.add(new DailyDto(pr.getPurchaseNumber()
                    , pr.getReturnDate(), null, pr.getProduct(), "Returns (Purchase)", pr.getQuantity()
                    , pr.getTotalAmount(), null));
        }
        
        model.addAttribute("dailyTotals", dailyTotals);
        return "report/dailytotals";
    }

    @GetMapping("/reports/orderstoday")
    public String listAllOrdersToday(Model model) {
        Iterable<SalesOrder> orders = this.salesOrderRepository.findByTransDate(LocalDate.now());
        double totalAmount = 0.0;

        for (SalesOrder order : orders) {
            totalAmount = totalAmount + order.getTotalAmount().doubleValue();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("totalAmount", totalAmount);
        return "report/orderstoday";
    }

    @GetMapping("/reports/productstoday")
    public String listAllProductsSoldToday(Model model) {

        HashMap<Long, ProductCountDto> productCountMap = new HashMap<>();
        Iterable<SalesOrder> orders = this.salesOrderRepository.findByTransDateAndStatus(LocalDate.now(), "PROCESSED");

        for (SalesOrder o : orders) {
            for (SalesOrderItem item : o.getItems()) {
                Product p = item.getStock().getProduct();
                long itemCount = item.getQuantity();
                if (productCountMap.containsKey(p.getId())) {
                    ProductCountDto pcd = productCountMap.get(p.getId());
                    long cnt = pcd.getCount();
                    pcd.setCount(cnt+itemCount);
                    productCountMap.put(p.getId(), pcd);
                } else {
                    ProductCountDto pcd = new ProductCountDto(p, itemCount);
                    productCountMap.put(p.getId(), pcd);
                }
            }
        }

        model.addAttribute("productCountMap", productCountMap);
        return "report/productstoday";
    }
    
    @GetMapping("/reports/lowproducts")
    public String listAllLowProducts(Model model) {
        
        HashMap<Long, ProductCountDto> productCountMap = new HashMap<>();
        
        Iterable<Stock> stocks = this.stockRepository.findAll();
        
        for (Stock st : stocks) {
            if (st.getLowCountIndicator() >= st.getQuantity()) {
                productCountMap.put(st.getId(), new ProductCountDto(st.getProduct(), st.getQuantity()));
            }
        }
        
        model.addAttribute("lowProducts", productCountMap);
        return "report/lowproducts";
    }
}
