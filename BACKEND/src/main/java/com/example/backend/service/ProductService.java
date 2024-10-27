package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import com.example.backend.entity.ProductType;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.AddProductRequest;
import com.example.backend.model.response.ProductDetailResponse;
import com.example.backend.model.response.ProductResponse;
import com.example.backend.model.response.ProductTypesResponse;
import com.example.backend.repository.ProductDetailRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ProductTypeRepository;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    //Lấy producttype theo id
    public ProductType getProductTypeById(long productTypeId) {
        return productTypeRepository.findById(productTypeId);
    }

    //Lấy tất cả producttype
    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    //Lấy tất cả product
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    //Lấy product theo id nè!
    public Optional<Product> getProductById(long id) {
        return productRepository.findAllById(id);
    }

    //Cái này là lấy theo name, t dự định để dùng nó cho chức năng tìm kiếm.
    public List<Product> getProductByName(String name) {
        return productRepository.findAllByName(name);
    }

    //Cái này là lưu thông tin chi tiết của sản phẩm
    public void saveProductDetail(ProductDetail productDetail) {
        productDetailRepository.save(productDetail);
    }

    //Cái này mới là lưu sản phẩm nè!
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsContainKeyword(String keyWord) {
        return productRepository.findByNameContaining(keyWord);
    }

    //PUBLIC!!!!!!!!!

    // Cái này là lấy product type rồi thêm vào phẩn phản hồi product type để kiểm soát thông tin phản hồi tránh lộ thông tin nhạy cảm!!!
    // Cái này liên kết với lấy tất cả producttype á!
    public List<ProductTypesResponse> getProductTypes() {
        return getAllProductType().stream()
                .map(productType -> new ProductTypesResponse(productType.getId(), productType.getName()))
                .collect(Collectors.toList());
    }

    // Cái này là liên kết với add-product để thêm product mới.
    public void createProduct(AddProductRequest addProductRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller = sellerService.getSellerByUserName(username);

        ProductType productType = getProductTypeById(addProductRequest.getTypeId());
        Product product = saveProduct(new Product(seller, productType, addProductRequest.getName(), addProductRequest.getQuantity()));
        saveProductDetail(new ProductDetail(product, addProductRequest.getPrice(), addProductRequest.getDescription(), addProductRequest.getImageUrl()));

    }

    // Còn cái này thì liên kết với ông products để lấy danh sách sản phẩm, tất nhiên sẽ phân loại theo loại sản phẩm
    public Map<String, List<ProductResponse>> getAllProductSplitWithTypeId() { //Product sẽ tự đông phân loại theo type nha!
        Map<String, List<ProductResponse>> map = new HashMap<>();

        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();;
            List<ProductResponse> productResponse = products.stream().map(product -> new ProductResponse(
                    product.getId(), product.getName(),
                    productType.getName(),
                    product.getProductDetail().getPrice(),
                    product.getProductDetail().getDiscount(),
                    product.getProductDetail().getImageUrl())).toList();

            map.put(productType.getName(), productResponse);
        }

        return map;
    }

    //Cái này là lấy thông tin chi tiết của sản phẩm nếu người dùng cần xem nè!
    public ProductDetailResponse getProductDetail(long productId) {
        Optional<Product> existingProduct = getProductById(productId);
        return existingProduct.map(product -> new ProductDetailResponse(product.getSeller().getFullname(),
                product.getProductDetail().getDescription())).orElse(null);
    }

    public List<ProductResponse> searchProduct(String keyWord) {
        List<Product> products = getProductsContainKeyword(keyWord);
        return products.stream().map(product -> new ProductResponse(
                product.getId(), product.getName(),
                product.getProductType().getName(),
                product.getProductDetail().getPrice(),
                product.getProductDetail().getDiscount(),
                product.getProductDetail().getImageUrl())
        ).toList();
    }

    // SELLERRRRR!!!!!!!!!!

    // Phần này là của seller lấy danh sách sản phẩm mà họ bán nè!
    public List<Product> getMyProducts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller = sellerService.getSellerByUserName(username);
        return seller.getProducts();
    }
}
