package com.falcon.common;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
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
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;

@Controller
public class DashboardController {

    private SalesOrderRepository salesOrderRepository;
    private ExpenseRepository expenseRepository;
    private ProductRepository productRepository;
    private StockRepository stockRepository;

    public DashboardController(SalesOrderRepository orderRepository
            , ExpenseRepository expenseRepository
            , ProductRepository productRepository
            , StockRepository stockRepository) {
        this.salesOrderRepository = orderRepository;
        this.expenseRepository = expenseRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        List<SalesOrder> ordersPastSevenDays = this.salesOrderRepository.findByTransDateGreaterThanAndStatus(LocalDate.now().minusDays(7), "PROCESSED");
        Map<String, Double> ordersSevenDaysMap = new LinkedHashMap<>();
        for (SalesOrder order : ordersPastSevenDays) {
            double totalSalesAmount = 0;
            for (SalesOrderItem item : order.getItems()) {
                totalSalesAmount = totalSalesAmount + item.getNetSellingPrice().doubleValue();
            }
            
            if (ordersSevenDaysMap.get(order.getTransDate().toString()) != null) {
                double oldAmount = ordersSevenDaysMap.get(order.getTransDate().toString());
                double newAmount = oldAmount + totalSalesAmount;
                ordersSevenDaysMap.put(order.getTransDate().toString(), newAmount);
            } else {
                ordersSevenDaysMap.put(order.getTransDate().toString(), totalSalesAmount);
            }
        }
        
//        List<SalesOrderItem> orderItemsSevenDays = ordersPastSevenDays.stream()
//                .map(SalesOrder::getItems)
//                .flatMap(List::stream)
//                .collect(toList());
//        Map<Object, Long> ordersSevenDaysMap = orderItemsSevenDays.stream()
//                .collect(Collectors.groupingBy(item -> item.getStock().getProduct().getName()
//                        , counting()));
        
        List<SalesOrder> ordersToday = this.salesOrderRepository.findByTransDateAndStatus(LocalDate.now(), "PROCESSED");
        
        // Daily Product Sales Count
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
        List<Expense> expensesToday = this.expenseRepository.findByExpenseDate(LocalDate.now());
        long totalExpenseAmount = 0;
        for (Expense expense : expensesToday) {
            totalExpenseAmount = totalExpenseAmount + expense.getAmount().longValue();
        }
        
        int lowProductCount = 0;
        Iterable<Stock> stocks = this.stockRepository.findAll();
        
        for (Stock st : stocks) {
            if (st.getLowCountIndicator() >= st.getQuantity()) {
                lowProductCount++;
            }
        }

        model.addAttribute("weeklySalesData", ordersSevenDaysMap);
        model.addAttribute("dailyProductSales", ordersTodayMap);
        
        model.addAttribute("totalSalesAmount", totalSalesAmount);
        model.addAttribute("lowProductCount", lowProductCount);
        model.addAttribute("totalExpenseAmount", totalExpenseAmount);
        model.addAttribute("orderCountToday", ordersToday.size());
        return "common/dashboard";
    }
}
