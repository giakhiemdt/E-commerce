package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.model.request.frontend.user.AddCartProductRequest;
import com.example.backend.model.request.frontend.user.DeleteCartProductRequest;
import com.example.backend.model.request.frontend.user.UpdateCartProductRequest;
import com.example.backend.model.response.ProductResponse;
import com.example.backend.model.response.seller.SellerOrderResponse;
import com.example.backend.model.response.user.CartHistoryResponse;
import com.example.backend.model.response.user.UserCartResponse;
import com.example.backend.repository.AddRepository;
import com.example.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddRepository addRepository;

    public void addProduct(Add add) {
        addRepository.save(add);
    }

    @Transactional
    public void updateProductQuantity(int quantity, Cart cart, long productId) {
        addRepository.updateQuantityByCartAndProductId(quantity, cart, productId);
    }

    @Transactional
    public void deleteProduct(Cart cart, long productId) {
        addRepository.deleteByCartAndProductId(cart, productId);
    }

    public List<Cart> getCartByStatus(Status status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = accountService.getAccountByUserName(username).map(Account::getUsers);
        return user.map(users -> cartRepository.findCartByUserAndStatus(users, status)).orElse(null);
    }

    public Cart getCurrentCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = accountService.getAccountByUserName(username).map(Account::getUsers);
        return user.map(users -> cartRepository.findCartByUserAndStatus(users, Status.CREATED).getFirst()).orElse(null);
    }

    // USERRRR!

    public UserCartResponse getUserCart() {
        UserCartResponse response = new UserCartResponse();

        Cart currentCart = getCurrentCart();
        response.setPhone(currentCart.getCartDetail().getPhone());
        response.setAdress(currentCart.getCartDetail().getAddress());

        Map<ProductResponse, Integer> cartItems = currentCart.getAdds().stream()
                .collect(Collectors.toMap(
                        add -> createProductResponse(add.getProduct()),
                        Add::getQuantity
                ));
        response.setCart(cartItems);

        return response;
    }

    private ProductResponse createProductResponse(Product product) {
        return new ProductResponse(
                product.getId(), product.getName(),
                product.getProductType().getName(),
                product.getProductDetail().getPrice(),
                product.getProductDetail().getDiscount(),
                product.getProductDetail().getImageUrl()
        );
    }

    public void addProductToCart(AddCartProductRequest request) {
        Cart currentCart = getCurrentCart();
        Optional<Product> existingProduct = productService.getProductById(currentCart.getId());
        existingProduct.ifPresent(product -> addProduct(new Add(currentCart, product, request.getQuantity(), OrderStatus.CREATED)));
    }

    @Transactional
    public void updateProductInCart(UpdateCartProductRequest request) {
        Cart currentCart = getCurrentCart();
        updateProductQuantity(request.getQuantity(), currentCart, request.getProductId());
    }

    @Transactional
    public void deleteProductInCart(DeleteCartProductRequest request) {
        Cart currentCart = getCurrentCart();
        deleteProduct(currentCart, request.getProductId());
    }

    public List<CartHistoryResponse> getCartHistory() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = accountService.getAccountByUserName(username).map(Account::getUsers);
        if (user.isPresent()) {
            List<Cart> carts = user.get().getCarts();
            return carts.stream().map(
                    cart -> new CartHistoryResponse(
                            cart.getId(),
                            cart.getCreatedDate(),
                            cart.getStatus(),
                            cart.getCartDetail().getPhone(),
                            cart.getCartDetail().getAddress()
                    )
                    ).toList();
        }
        return null;

    }

    // SELLER!!!

    public List<SellerOrderResponse> getCurrentOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Seller> seller = accountService.getAccountByUserName(username).map(Account::getSeller);
        if (seller.isPresent()) {
            List<Product> products = productService.getSellerProduct(seller.get());
            
        }

    }

}
