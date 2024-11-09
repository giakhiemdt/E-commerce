package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.entity.enums.OrderStatusEnum;
import com.example.backend.entity.enums.PaymentMethodEnum;
import com.example.backend.model.request.frontend.order.HandleOrderRequest;
import com.example.backend.model.request.frontend.order.OrderListRequest;
import com.example.backend.model.response.OrderResponse;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.cart.CartDetailResponse;
import com.example.backend.model.response.cart.CartItemResponse;
import com.example.backend.model.response.product.ProductResponse;
import com.example.backend.model.response.order.SellerOrderResponse;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.OrdersStatusRepository;
import com.example.backend.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersStatusRepository ordersStatusRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public Optional<Orders> findOrderByOrderId(long orderId) {
        return ordersRepository.findById(orderId);
    }

    public Optional<Orders> findOrderByUserAndProductId(Users users, long productId) {
        return ordersRepository.findByUserAndProductId(users, productId);
    }

    @Transactional
    public void updateOrderStatusById(OrderStatusEnum status, long id) {
        ordersRepository.updateStatusByOrderId(status, id);
    }

    @Transactional
    public void updateQuantityByUserAndProductId(int quantity, Users users, long productId) {
        ordersRepository.updateQuantityByUserAndProductId(quantity, users, productId);
    }

    @Transactional
    public void deleteByUserAndProductId(Users users, long productId) {
        ordersRepository.deleteByUserAndProductId(users, productId);
    }

    @Transactional
    public void updateTotalPriceByOrdersStatus(long needPaid, OrdersStatus ordersStatus) {
        ordersStatusRepository.updateTotalPriceByOrderStatus(needPaid, ordersStatus);
    }

    @Transactional
    public void deleteOrderStatusesByOrderId(long orderId) {
        ordersStatusRepository.deleteOrderStatusByOrderId(orderId);
    }

    @Transactional
    public void updatePaymentMethodByOrderId(PaymentMethodEnum paymentMethodEnum, long orderId) {
        ordersRepository.updatePaymentMethodById(paymentMethodEnum, orderId);
    }

    @Transactional
    public void updateSaleByProduct(int sale, Product product) {
        productDetailRepository.updateSaleByProduct(sale, product);
    }

    public Orders saveOrders(Orders order) {
        return ordersRepository.save(order);
    }

    public OrdersStatus saveOrdersStatus(OrdersStatus ordersStatus) {
        return ordersStatusRepository.save(ordersStatus);
    }

    public List<Product> getSellerProduct() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Seller> existingSeller = accountService.getAccountByUserName(username).map(Account::getSeller);
        return existingSeller.map(seller -> productService.getSellerProduct(seller)).orElse(null);
    }

    //USER RRR!!!

    public StatusResponse addProductToCart(long productId, HandleOrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> existingUser = accountService.getAccountByUserName(username).map(Account::getUsers);
        Optional<Product> existingProduct = productService.getProductById(productId);
        if (existingUser.isPresent() && existingProduct.isPresent()) {
            if (existingProduct.get().getQuantity() > request.getQuantity()) { // Cho phép tiếp tục tạo đơn nếu hàng tồn kho đủ
                Orders order = saveOrders(new Orders(existingUser.get(), existingProduct.get(),
                        request.getQuantity(), OrderStatusEnum.CREATED));

                saveOrdersStatus(new OrdersStatus(
                        order.getQuantity() * (order.getProduct().getProductDetail().getProductPrice()
                                + order.getProduct().getProductDetail().getSystemFee()) ,
                        OrderStatusEnum.CREATED, order
                ));
                return new StatusResponse(true, "Added!");
            }
            return new StatusResponse(false, "Insufficient inventory!");
        }
        return new StatusResponse(false, "User or Product is not valid!");
    }

    @Transactional
    public StatusResponse updateProductInCart(long productId, HandleOrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> existingUser = accountService.getAccountByUserName(username).map(Account::getUsers);

        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            Optional<Orders> existingOrder = findOrderByUserAndProductId(user, productId);

            if (existingOrder.isPresent()) {
                Orders order = existingOrder.get();
                Product product = productService.getProductById(productId).orElse(null);
                if (product != null) {

                    if (product.getQuantity() > request.getQuantity()) { // Có đủ tồn kho thì tiếp tục!!!
                        updateQuantityByUserAndProductId(request.getQuantity(), user, productId);

                        long totalPrice = request.getQuantity() * (product.getProductDetail().getProductPrice()
                                + product.getProductDetail().getSystemFee());

                        updateTotalPriceByOrdersStatus(totalPrice, order.getOrdersStatusList().get(0));

                        return new StatusResponse(true, "Updated!");
                    }
                    return new StatusResponse(false, "Insufficient inventory!");
                }
                return new StatusResponse(false, "Product is not valid!");
            }
            return new StatusResponse(false, "Order is not valid!");
        }
        return new StatusResponse(false, "User is not valid!");
    }


    @Transactional
    public StatusResponse deleteProductFromCart(long productId, HandleOrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> existingUser = accountService.getAccountByUserName(username).map(Account::getUsers);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            Optional<Orders> existingOrder = findOrderByUserAndProductId(user, productId);

            if (existingOrder.isPresent()) {
                deleteOrderStatusesByOrderId(existingOrder.get().getId());
                deleteByUserAndProductId(user, productId);
                return new StatusResponse(true, "Deleted!");
            }
            return new StatusResponse(false, "Order is not valid!");
        }
        return new StatusResponse(false, "User is not valid!");
    }

    public CartDetailResponse getCurrentCart(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Optional<Users> existingUser;
        if (role.equals("ADMIN")) { // Admin có quyền xem tất cả giỏ hàng của người dùng.
            existingUser = accountService.getAccountById(userId).map(Account::getUsers);
        }else {
            existingUser = accountService.getAccountByUserName(authentication.getName()).map(Account::getUsers);
        }

        return existingUser.map(users -> new CartDetailResponse(users.getId(),
                users.getTotalPriceInCurrentCart(),
                users.getOrders().stream()
                        .filter(order -> order.getStatus().equals(OrderStatusEnum.CREATED))
                        .map(order ->
                        new CartItemResponse(new ProductResponse(
                                order.getProduct().getId(),
                                order.getProduct().getName(),
                                order.getProduct().getProductType().getName(),
                                order.getProduct().getProductDetail().getProductPrice()
                                        + order.getProduct().getProductDetail().getSystemFee(),
                                order.getProduct().getProductDetail().getDiscount(),
                                order.getProduct().getProductDetail().getImageUrl()
                        ), new OrderResponse(order.getId(),
                                order.getProduct().getId(),
                                order.getQuantity())
                        )
                ).toList()
        )).orElse(null);
    }

    @Transactional
    public StatusResponse orderProducts(OrderListRequest request) { // Chuyển tất cả từ trạng thái Create sang Order
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> existingUser = accountService.getAccountByUserName(username).map(Account::getUsers);

        if (existingUser.isPresent()) {
            for (Long orderId: request.getOrdersId()) {
                Optional<Orders> existingOrder = findOrderByOrderId(orderId);

                if (existingOrder.isEmpty()) {
                    continue;
                }

                if (existingOrder.get().getStatus().equals(OrderStatusEnum.CREATED) && existingUser.get().getOrders().contains(existingOrder.get())) {
                    if (existingOrder.get().getQuantity() > existingOrder.get().getProduct().getQuantity()) {
                        continue; // Nếu số lượng hàng đặt vượt quá số lượng tồn kho thì trực tiếp nhảy qua
                    }

                    updateOrderStatusById(OrderStatusEnum.ORDERED, orderId);

                    long needPaid = existingOrder.get().getQuantity() *
                            (existingOrder.get().getProduct().getProductDetail().getProductPrice()
                            + existingOrder.get().getProduct().getProductDetail().getSystemFee());
                    long totalFee = request.getPaymentType().equals(PaymentMethodEnum.ONLINE) ? needPaid : 0;
                    needPaid = totalFee == needPaid ? 0 : needPaid;

                    Payment payment= paymentService.savePayment(new Payment(existingOrder.get().getProduct().getSeller(), existingOrder.get(), totalFee));
                    OrdersStatus ordersStatus = saveOrdersStatus(new OrdersStatus(needPaid, OrderStatusEnum.ORDERED, existingOrder.get()));
                    if (request.getPaymentType().equals(PaymentMethodEnum.ONLINE)) { // Online mới ghi nhận luồng thanh toán
                        updatePaymentMethodByOrderId(PaymentMethodEnum.ONLINE, orderId); // Cập nhật phương thức thanh toán

                        paymentService.saveTransactions(new Transactions(payment, ordersStatus, totalFee));
                    }else {
                        updatePaymentMethodByOrderId(PaymentMethodEnum.COD, orderId);
                    }
                }

            }
            return new StatusResponse(true, "Sucessfull!");
        }
        return new StatusResponse(false, "Invalid user!");

    }

    // SELLER!!!

    public List<SellerOrderResponse> getOrderResponse(boolean onlyCurrentOrders) {
        List<SellerOrderResponse> response = new ArrayList<>();
        List<Product> products = getSellerProduct();

        for (Product product : products) {
            List<Orders> orders = product.getOrders();
            for (Orders order : orders) {
                if (!onlyCurrentOrders || order.getStatus().equals(OrderStatusEnum.ORDERED)) {
                    response.add(new SellerOrderResponse(
                            product.getId(),
                            order.getQuantity(),
                            order.getUsers().getAccount().getUsername(),
                            order.getOrderedDate()
                    ));
                }
            }
        }
        return response;
    }

    // getCurrentOrder sử dụng method chung với điều kiện
    public List<SellerOrderResponse> getCurrentOrder() {
        return getOrderResponse(true);
    }

    // getOrderHistory sử dụng method chung mà không có điều kiện
    public List<SellerOrderResponse> getOrderHistory() {
        return getOrderResponse(false);
    }

    @Transactional
    public StatusResponse handleOrderStatus(Long orderId, OrderStatusEnum requestedStatus) {

        Orders order = validateOrderAndSeller(orderId);
        if (order == null) {
            return new StatusResponse(false, "Order is not valid!");
        }

        // Xác định trạng thái mới dựa trên trạng thái hiện tại
        OrderStatusEnum newStatus = getNextStatus(order.getStatus(), requestedStatus);
        if (newStatus == null) {
            return new StatusResponse(false, "Invalid status transition!");
        }

        updateOrderStatusById(newStatus, orderId);
        saveOrdersStatus(new OrdersStatus(order.getNeedPaid(), newStatus, order));
        if (newStatus.equals(OrderStatusEnum.ACCEPTED)) { // Nếu seller chấp nhận đơn thì tự động trừ số lượng tồn kho.
            productService.updateProductByQuantity(order.getProduct().getQuantity() - order.getQuantity(), order.getProduct());
        }
        return new StatusResponse(true, newStatus + "!");
    }

    @Transactional
    public StatusResponse finishOrder(Long orderId) {

        Orders order = validateOrderAndSeller(orderId);
        if (order == null || !order.getStatus().equals(OrderStatusEnum.SHIPPING)) {
            return new StatusResponse(false, "Order is not valid!");
        }

        updateOrderStatusById(OrderStatusEnum.FINISHED, orderId);
        OrdersStatus ordersStatus = saveOrdersStatus(new OrdersStatus(order.getNeedPaid(), OrderStatusEnum.FINISHED, order));

        // Xử lý thanh toán cho phương thức COD
        if (order.getPaymentMethod().equals(PaymentMethodEnum.COD)) {
            Payment payment = paymentService.savePayment(new Payment(order.getProduct().getSeller(), order, order.getNeedPaid()));
            paymentService.saveTransactions(new Transactions(payment, ordersStatus, ordersStatus.getNeedPaid()));
        }

        // Cập nhật số lượng đã bán sau khi đơn hàng hoàn thành
        updateSaleByProduct(order.getProduct().getProductDetail().getSale() + order.getQuantity(), order.getProduct());

        return new StatusResponse(true, "Finished!");
    }

    // Phương thức kiểm tra quyền và lấy đơn hàng
    private Orders validateOrderAndSeller(Long orderId) {
        Optional<Orders> optionalOrder = findOrderByOrderId(orderId);
        if (optionalOrder.isEmpty()) {
            return null;
        }

        Orders order = optionalOrder.get();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String sellerUsername = order.getProduct().getSeller().getAccount().getUsername();

        return currentUsername.equals(sellerUsername) ? order : null;
    }

    // Phương thức xác định trạng thái tiếp theo hợp lệ
    private OrderStatusEnum getNextStatus(OrderStatusEnum currentStatus, OrderStatusEnum requestedStatus) {
        if (currentStatus.equals(OrderStatusEnum.ORDERED) && requestedStatus.equals(OrderStatusEnum.ACCEPTED)) {
            return OrderStatusEnum.ACCEPTED;
        } else if (currentStatus.equals(OrderStatusEnum.ACCEPTED) && requestedStatus.equals(OrderStatusEnum.PREPARED)) {
            return OrderStatusEnum.PREPARED;
        } else if (currentStatus.equals(OrderStatusEnum.PREPARED) && requestedStatus.equals(OrderStatusEnum.SHIPPING)) {
            return OrderStatusEnum.SHIPPING;
        }
        return null; // Trả về null nếu trạng thái chuyển đổi không hợp lệ
    }


//    @Transactional
//    public void cancelOrder(CancelOrderRequest request) {
//        Orders order = findOrderByOrderId(request.getOrderId());
//        if (order.getProduct().getSeller().getAccount().getUsername(). // Copy thằng trên
//                equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
//            updateOrderStatusById(OrderStatusEnum.REJECTED ,request.getOrderId());
//        }
//    }

}
