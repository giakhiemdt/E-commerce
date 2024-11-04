package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.model.response.payment.DashboardResponse;
import com.example.backend.model.response.payment.RevenueWithMonthResponse;
import com.example.backend.repository.PaymentRepository;
import com.example.backend.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AccountService accountService;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void saveTransactions(Transactions transactions) {
        transactionsRepository.save(transactions);
    }

    public Integer getNumberOfPayment() {
        return paymentRepository.getNumberOfPayments();
    }

    public Integer getNumberOfPaymentByYearAndSeller(int year, Seller seller) {
        return paymentRepository.countPaymentsByYearAndSeller(year, seller);
    }

    public Integer getNumberOfPaymentByYear(int year) {
        return paymentRepository.countPaymentsByYear(year);
    }

    public Integer getNumberOfPaymentByYearAndMonth(int year, int month) {
        return paymentRepository.countPaymentsByYearAndMonth(year, month);
    }

    public Integer getNumberOfPaymentByYearAndMonthAndSeller(int year, int month, Seller seller) {
        return paymentRepository.countPaymentsByYearAndMonthAndSeller(year, month, seller);
    }

    public Long getGrossRevenueOFPaymentByYearAndMonth(int year, int month) {
        return paymentRepository.sumPaymentsTotalFeeByYearAndMonth(year, month);
    }

    public Long getGrossRevenueOFPaymentByYearAndMonthAndSeller(int year, int month, Seller seller) {
        return paymentRepository.sumPaymentsTotalFeeByYearAndMonthAndSeller(year, month, seller);
    }

    public Long getGrossMerchandiseValueByGrossRevenue(Long grossRevenue) {
        return Math.round((double) grossRevenue / 105 * 100);
    }

    public DashboardResponse getDashboard(Integer year) {
        year = (year == null) ? LocalDate.now().getYear() : year;
        Map<Integer, RevenueWithMonthResponse> monthRevenues = new HashMap<>();
        int totalYearOrders = getNumberOfPaymentByYear(year);
        int currentMonth = LocalDate.now().getMonthValue();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ADMIN")) { // Lấy tổng quan tất cả sản phẩm
            for (int month = 1; month <= currentMonth; month++) { // Lập qua từng tháng trước
                int totalMonthOrders = getNumberOfPaymentByYearAndMonth(year, month);
                Long grossRevenue = getGrossRevenueOFPaymentByYearAndMonth(year, month);
                grossRevenue = grossRevenue != null ? grossRevenue : 0;
                monthRevenues.put(month, new RevenueWithMonthResponse(totalMonthOrders, grossRevenue,
                        getGrossMerchandiseValueByGrossRevenue(grossRevenue), 0 // Chưa phát triển phần tính chi phí vận hành sàn
                ));
            }
        }else {
            Optional<Account> existingAccount = accountService.getAccountByUserName(authentication.getName());
            Seller seller = existingAccount.map(Account::getSeller).orElse(null);
            if (seller!=null) {
                totalYearOrders = getNumberOfPaymentByYearAndSeller(year, seller);
                for (int month = 1; month <= currentMonth; month++) {
                    int totalMonthOrders = getNumberOfPaymentByYearAndMonthAndSeller(year, month, seller);
                    Long grossRevenue = getGrossRevenueOFPaymentByYearAndMonthAndSeller(year, month, seller);
                    grossRevenue = grossRevenue != null ? grossRevenue : 0;
                    monthRevenues.put(month, new RevenueWithMonthResponse(totalMonthOrders, grossRevenue,
                            getGrossMerchandiseValueByGrossRevenue(grossRevenue), 0
                    ));
                }

            }
        }

        return new DashboardResponse(year, totalYearOrders, monthRevenues);

    }

}
