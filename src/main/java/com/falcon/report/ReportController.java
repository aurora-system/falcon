package com.falcon.report;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.falcon.product.Product;
import com.falcon.product.ProductRepository;
import com.falcon.salesorder.SalesOrder;
import com.falcon.salesorder.SalesOrderItem;
import com.falcon.salesorder.SalesOrderRepository;

@Controller
public class ReportController {

    private ProductRepository productRepository;
    private SalesOrderRepository salesOrderRepository;

    public ReportController(
            ProductRepository productRepository
            ,SalesOrderRepository salesOrderRepository
            ) {
        this.productRepository = productRepository;
        this.salesOrderRepository = salesOrderRepository;
    }

    @GetMapping({"/reports"})
    public String showReports(Model model) {
        return "report/reports";
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
}
