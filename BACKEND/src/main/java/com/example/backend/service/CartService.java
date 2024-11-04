//package com.example.backend.service;
//
//import com.example.backend.entity.*;
//import com.example.backend.entity.enums.OrderStatusEnum;
//import com.example.backend.model.request.frontend.cart.HandleCartRequest;
//import com.example.backend.model.response.UserOrderResponse;
//import com.example.backend.model.response.cart.CartDetailResponse;
//import com.example.backend.model.response.cart.CartListWithFinishTimeResponse;
//import com.example.backend.model.response.cart.CartResponse;
//import com.example.backend.model.response.StatusResponse;
//import com.example.backend.model.response.product.ProductResponse;
//import com.example.backend.repository.OrdersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class CartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private OrdersRepository ordersRepository;
//
//    public void addProduct(Orders orders) {
//        ordersRepository.save(orders);
//    }
//
//    public Optional<Cart> getCartById(Long cartId) {
//        return cartRepository.findCartById(cartId);
//    }
//
//    public Long getCurrentCartId() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Optional<Users> user = accountService.getAccountByUserName(username).map(Account::getUsers);
//        return user.map(users -> cartRepository.findCartByUserAndStatus(users, CartStatusEnum.CREATED).get(0).getId()).orElse(null);
//    }
//
//    @Transactional
//    public void updateProductQuantity(int quantity, Cart cart, long productId) {
//        ordersRepository.updateQuantityByCartAndProductId(quantity, cart, productId);
//    }
//
//    @Transactional
//    public void deleteProduct(Cart cart, long productId) {
//        ordersRepository.deleteByCartAndProductId(cart, productId);
//    }
//
//    public List<Cart> getCartByStatus(CartStatusEnum cartStatusEnum) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Optional<Users> user = accountService.getAccountByUserName(username).map(Account::getUsers);
//        return user.map(users -> cartRepository.findCartByUserAndStatus(users, cartStatusEnum)).orElse(null);
//    }
//
//    public boolean checkIsThisUserCart(long cartId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getAuthorities().iterator().next().getAuthority().equals("USER")) {
//            Optional<Users> user = accountService.getAccountByUserName(authentication.getName()).map(Account::getUsers);
//            Cart cart = getCartById(cartId).orElse(null);
//            return user.map(users -> users.getCarts().contains(cart)).orElse(false);
//        }
//        return true; // Không phải USER thì chỉ có ADMIN thôi!
//    }
//
//    // USERRRR!
//
//    public CartDetailResponse getCartDetail(Long cartId) {
//        if (cartId == null) {
//            cartId = getCurrentCartId();
//        }
//        Optional<Cart> cart = getCartById(cartId);
//        if (cart.isPresent() && checkIsThisUserCart(cartId)) {
//            return new CartDetailResponse(
//                    cartId, cart.get().getOrderDetail().getPhone(),
//                    cart.get().getOrderDetail().getAddress(),
//                    cart.get().getOrderDetail().getTotalPrice(),
//                    cart.get().getOrders().stream().collect(
//                            Collectors.toMap(
//                             orders -> createProductResponse(orders.getProduct()),
//                                    Orders::getQuantity
//                            ))
//            );
//        }
//        return null;
//    }
//
//    private ProductResponse createProductResponse(Product product) {
//        return new ProductResponse(
//                product.getId(), product.getName(),
//                product.getProductType().getName(),
//                product.getProductDetail().getPrice(),
//                product.getProductDetail().getDiscount(),
//                product.getProductDetail().getImageUrl()
//        );
//    }
//
//    private UserOrderResponse createOrderResponse(Orders orders) {
//        return new UserOrderResponse(
//                orders.getId(),
//                orders.getProduct().getId(),
//                orders.getQuantity()
//        );
//    }
//
//    public StatusResponse addProductToCart(long productId, HandleCartRequest request) {
//        Optional<Cart> currentCart = getCartById(getCurrentCartId());
//        if (currentCart.isPresent()) {
//            Optional<Product> existingProduct = productService.getProductById(productId);
//            existingProduct.ifPresent(product -> addProduct(new Orders(currentCart.get(), product, request.getQuantity(), OrderStatusEnum.CREATED)));
//            return new StatusResponse(true, "Added!");
//        }
//        return new StatusResponse(false, "Current cart is not exist!");
//    }
//
//    @Transactional
//    public StatusResponse updateProductInCart(long productId, HandleCartRequest request) {
//        Optional<Cart> currentCart = getCartById(getCurrentCartId());
//        return currentCart.map(cart -> {
//            updateProductQuantity(request.getQuantity(), cart, productId);
//            return new StatusResponse(true, "Updated!");
//        }).orElse(new StatusResponse(false, "Current cart is not exist!"));
//    }
//
//    @Transactional
//    public StatusResponse deleteProductInCart(long productId, HandleCartRequest request) {
//        Optional<Cart> currentCart = getCartById(getCurrentCartId());
//        return currentCart.map(cart -> {
//            deleteProduct(cart, productId);
//            return new StatusResponse(true, "Deleted!");
//        }).orElse(new StatusResponse(false, "Current cart is not exist!"));
//    }
//
//    public CartListWithFinishTimeResponse getCartHistory(Long accountId) {
//        Optional<Users> user = Optional.empty();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (accountId != null) { // nếu có id thì lấy theo userId
//            if (authentication.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
//                user = accountService.getAccountById(accountId).map(Account::getUsers);
//            }
//        }else {
//            user = accountService.getAccountByUserName(authentication.getName()).map(Account::getUsers);
//        }
//
//        Map<CartResponse, Timestamp> map = new HashMap<>();
//        if (user.isPresent()) {
//            List<Cart> carts = user.get().getCarts();
//            for (Cart cart : carts) {
//                map.put(new CartResponse(
//                        cart.getId(),
//                        cart.getOrderDetail().getPhone(),
//                        cart.getOrderDetail().getAddress(),
//                        cart.getCartStatusEnum(),
//                        cart.getFinishedDate(),
//                        cart.getPayment().getTotalFee()
//                ), cart.getFinishedDate());
//
//            }
//
//        }
//        return new CartListWithFinishTimeResponse(map);
//    }
//
//    //ADMIN
////
////    public List<AdminCartHistoryResponse> getAllCurrentCart() {
////
////    }
//
//}
