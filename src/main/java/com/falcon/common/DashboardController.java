package com.falcon.common;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.falcon.expense.Expense;
import com.falcon.expense.ExpenseRepository;
import com.falcon.product.ProductRepository;
import com.falcon.salesorder.SalesOrder;
import com.falcon.salesorder.SalesOrderItem;
import com.falcon.salesorder.SalesOrderRepository;

@Controller
public class DashboardController {

    private SalesOrderRepository salesOrderRepository;
    private ExpenseRepository expenseRepository;
    private ProductRepository productRepository;

    public DashboardController(SalesOrderRepository orderRepository, ExpenseRepository expenseRepository, ProductRepository productRepository) {
        this.salesOrderRepository = orderRepository;
        this.expenseRepository = expenseRepository;
        this.productRepository = productRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        // Weekly Sales Data - dummy
        Map<String, Integer> weeklySalesData = new LinkedHashMap<>();
        weeklySalesData.put("9/20", 57500);
        weeklySalesData.put("9/21", 20400);
        weeklySalesData.put("9/22", 30500);
        weeklySalesData.put("9/23", 35760);
        weeklySalesData.put("9/24", 14211);
        weeklySalesData.put("9/25", 8950);
        weeklySalesData.put("9/26", 88300);

        // Daily Product Sales - dummy
        Map<String, Integer> dailyProductSalesData = new LinkedHashMap<>();
        dailyProductSalesData.put("Toyota Corolla bumper", 5);
        dailyProductSalesData.put("Honda Civic windshield", 2);
        dailyProductSalesData.put("Clifford's Diffuser", 3);
        dailyProductSalesData.put("Vossen VIP Rims", 8);
        dailyProductSalesData.put("Wild Trak Exhaust", 3);

        model.addAttribute("weeklySalesData", weeklySalesData);
        model.addAttribute("dailyProductSalesData", dailyProductSalesData);
        model.addAttribute("message", "Hello from @GetMapping.");

        List<SalesOrder> ordersToday = this.salesOrderRepository.findByTransDate(LocalDate.now());
        List<Expense> expensesToday = this.expenseRepository.findByExpenseDate(LocalDate.now());

        // Daily Product Sales
        List<SalesOrderItem> orderItems = ordersToday.stream()
                .map(SalesOrder::getItems)
                .flatMap(List::stream)
                .collect(toList());
        Map<Object, Long> ordersTodayMap = orderItems.stream()
                .collect(Collectors.groupingBy(item -> item.getStock().getProduct().getName()
                        , counting()));

        // Todal Order Sales Amount
        long totalSalesAmount = 0;
        for (SalesOrder order : ordersToday) {
            totalSalesAmount = totalSalesAmount + order.getTotalAmount().longValue();
        }

        // Todal Order Sales Amount
        long totalExpenseAmount = 0;
        for (Expense expense : expensesToday) {
            totalExpenseAmount = totalExpenseAmount + expense.getAmount().longValue();
        }

        model.addAttribute("dailyProductSales", ordersTodayMap);
        model.addAttribute("totalSalesAmount", totalSalesAmount);
        model.addAttribute("totalExpenseAmount", totalExpenseAmount);
        model.addAttribute("orderCountToday", ordersToday.size());
        return "common/dashboard";
    }
}
