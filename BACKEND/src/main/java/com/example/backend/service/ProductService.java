package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import com.example.backend.entity.ProductType;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.AddProductRequest;
import com.example.backend.repository.ProductDetailRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ProductTypeRepository;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public ProductType getProductTypeById(long productTypeId) {
        return productTypeRepository.findById(productTypeId);
    }

    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findAllById(id);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findAllByName(name);
    }

    public List<Product> getProductByType(String typeName) {
        return productRepository.findProductByProductTypeName(typeName);
    }

    public List<Product> findProductBySellerId(long id) {
        return productRepository.findProductBySellerId(id);
    }

    public void saveProductDetail(ProductDetail productDetail) {
        productDetailRepository.save(productDetail);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean createProduct(String authHeader, AddProductRequest addProductRequest) {
        String token = tokenService.trueToken(authHeader);
        if (tokenService.isValidToken(token) && tokenService.isSELLER(token)) {
            Seller seller = sellerService.getSellerByUserName(jwtUtil.extractName(token));
            ProductType productType = getProductTypeById(addProductRequest.getTypeId());
            Product product = saveProduct(new Product(seller, productType, addProductRequest.getName(), addProductRequest.getQuantity()));
            saveProductDetail(new ProductDetail(product, addProductRequest.getPrice(), addProductRequest.getDescription(), addProductRequest.getImageUrl()));
            return true;
        }
        return false;
    }

//    public List<Product> getAllProducts(String authHeader) {
//        String token = tokenService.trueToken(authHeader);
//        if (tokenService.isValidToken(token)) {
//            if (tokenService.isSELLER(token)) { // Nếu là Seller thì lấy
//
//            }else if (tokenService.isADMIN(token)) {
//
//            }
//
//        }
//        return null;
//    }

    public List<Product> getMyProducts(String authHeader) {
        String token = tokenService.trueToken(authHeader);
        if (tokenService.isValidToken(token) && tokenService.isSELLER(token)) {
            Seller seller = sellerService.getSellerByUserName(jwtUtil.extractName(token));
            return findProductBySellerId(seller.getId());
        }
        return null;
    }
}
