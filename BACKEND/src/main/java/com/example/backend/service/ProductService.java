package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import com.example.backend.entity.ProductType;
import com.example.backend.model.request.frontend.seller.AddProductRequest;
import com.example.backend.model.request.frontend.seller.DeleteProductRequest;
import com.example.backend.model.request.frontend.seller.UpdateProductRequest;
import com.example.backend.model.response.ProductDetailResponse;
import com.example.backend.model.response.ProductsResponse;
import com.example.backend.model.response.ProductTypesResponse;
import com.example.backend.model.response.admin.AdminProductResponse;
import com.example.backend.model.response.seller.SellerProductResponse;
import com.example.backend.repository.ProductDetailRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductTypeRepository productTypeRepository;

    private final SellerService sellerService;

    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                           ProductTypeRepository productTypeRepository,
                           SellerService sellerService,
                           ProductDetailRepository productDetailRepository) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.sellerService = sellerService;
        this.productDetailRepository = productDetailRepository;
    }

    //Lấy producttype theo id
    public ProductType getProductTypeById(long productTypeId) {
        return productTypeRepository.findById(productTypeId);
    }

    //Lấy tất cả producttype
    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    //Lấy product theo id nè!
    public Optional<Product> getProductById(long id) {
        return productRepository.findAllById(id);
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

    @Transactional
    public void updateProductByType(ProductType productType, Product product) {
        productRepository.updateProductByProductType(productType, product);
    }

    @Transactional
    public void updateProductByName(String productName, Product product) {
        productRepository.updateProductByName(productName, product);
    }

    @Transactional
    public void updateProductByQuantity(long productQuantity, Product product) {
        productRepository.updateProductByQuantity(productQuantity, product);
    }

    @Transactional
    public void updateProductByActive(boolean isActive, Product product) {
        productRepository.updateProductByActive(isActive, product);
    }

    @Transactional
    public void updateProductByPrice(long price, Product product) {
        productRepository.updateProductByProductDetailPrice(price, product);
    }

    @Transactional
    public void updateProductByDiscount(String discount, Product product) {
        productRepository.updateProductByProductDetailDiscount(discount, product);
    }

    @Transactional
    public void updateProductByDescription(String description, Product product) {
        productRepository.updateProductByProductDetailDescription(description, product);
    }

    @Transactional
    public void updateProductByImageUrl(String imageUrl, Product product) {
        productRepository.updateProductByProductDetailImageUrl(imageUrl, product);
    }

    @Transactional
    public void deleteProductById(long productId) {
        productRepository.deleteProductById(productId);
    }

    //PUBLIC!!!!!!!!!

    // Cái này là lấy product type rồi thêm vào phẩn phản hồi product type để kiểm soát thông tin phản hồi tránh lộ thông tin nhạy cảm!!!
    // Cái này liên kết với lấy tất cả producttype á!
    public List<ProductTypesResponse> getProductTypes() {
        return getAllProductType().stream()
                .map(productType -> new ProductTypesResponse(productType.getId(), productType.getName()))
                .collect(Collectors.toList());
    }

    // Còn cái này thì liên kết với ông products để lấy danh sách sản phẩm, tất nhiên sẽ phân loại theo loại sản phẩm
    public Map<String, List<ProductsResponse>> getAllProductSplitWithTypeId() { //Product sẽ tự đông phân loại theo type nha!
        Map<String, List<ProductsResponse>> map = new HashMap<>();

        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();
            List<ProductsResponse> productsResponse = products.stream()
                    .filter(Product::isActive) // Nếu bị Seller hoặc Admin đóng thì cũng dell hiện ra!!!
                    .filter(product -> product.getQuantity() > 0) //Nếu hết hàng thì không hiển thị ra
                    .map(product -> new ProductsResponse(
                    product.getId(), product.getName(),
                    productType.getName(),
                    product.getProductDetail().getPrice(),
                    product.getProductDetail().getDiscount(),
                    product.getProductDetail().getImageUrl())).toList();

            map.put(productType.getName(), productsResponse);
        }

        return map;
    }

    //Cái này là lấy thông tin chi tiết của sản phẩm nếu người dùng cần xem nè!
    public ProductDetailResponse getProductDetail(long productId) {
        Optional<Product> existingProduct = getProductById(productId);
        return existingProduct
                .filter(Product::isActive) // Này copy của thằng trên thôi ~~
                .map(product -> new ProductDetailResponse(
                        product.getSeller().getFullname(),
                        product.getProductDetail().getDescription())
                ).orElse(null);
    }

    public List<ProductsResponse> searchProduct(String keyWord) {
        List<Product> products = getProductsContainKeyword(keyWord);
        return products.stream()
                .filter(Product::isActive) // Này copy của thằng trên với thằng trên nữa!!
                .filter(product -> product.getQuantity() > 0)
                .map(product -> new ProductsResponse(
                product.getId(), product.getName(),
                product.getProductType().getName(),
                product.getProductDetail().getPrice(),
                product.getProductDetail().getDiscount(),
                product.getProductDetail().getImageUrl())
        ).toList();
    }

    // SELLERRRRR!!!!!!!!!!

    // Phần này là của seller lấy danh sách sản phẩm mà họ bán nè!
    public Map<String, List<SellerProductResponse>> getMyProducts() {
        Map<String, List<SellerProductResponse>> map = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();
            List<SellerProductResponse> sellerProductResponse = products.stream() // Em bị ngáo ạ!!!
                    .filter(product -> product.getSeller().getAccount().getUsername().equals(username)) // Má dell nhớ cách này làm quần quần nãy giờ!!!!
                    .map(product -> new SellerProductResponse(
                            product.getId(), product.getName(),
                            productType.getName(), product.getQuantity(),
                            product.isActive(), product.getProductDetail().getPrice(),
                            product.getProductDetail().getDiscount(),
                            product.getProductDetail().getPostedDate(),
                            product.getProductDetail().getSale(),
                            product.getProductDetail().getDescription(),
                            product.getProductDetail().getImageUrl()
                    )).toList();

            map.put(productType.getName(), sellerProductResponse);
        }
        return map;
    }

    // Cái này là liên kết với add-product để thêm product mới.
    public void createProduct(AddProductRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = saveProduct(new Product(
                sellerService.getSellerByUserName(username),
                getProductTypeById(request.getTypeId()),
                request.getName(), request.getQuantity()));
        saveProductDetail(new ProductDetail(product, request.getPrice(), request.getDescription(), request.getImageUrl()));

    }

    //Cập nhật thông tin bờ rồ duck ở chỗ này nè!!!
    @Transactional
    public void updateProduct(UpdateProductRequest request) {
        Optional<Product> product = getProductById(request.getProductId());

        if (product.isPresent()) {
            if (request.getTypeId() != 0) {
                updateProductByType(getProductTypeById(request.getTypeId()), product.get());
            }
            if (request.getName() != null) {
                updateProductByName(request.getName(), product.get());
            }
            if (request.getQuantity() != product.get().getQuantity()) { // Số lượng thay đổi được nè!
                updateProductByQuantity(request.getQuantity(), product.get());
            }
            if (request.isActive() != product.get().isActive()) {
                updateProductByActive(request.isActive(), product.get());
            }
            if (request.getPrice() != 0) { //Giá đel thể nào khác 0
                updateProductByPrice(request.getPrice(), product.get());
            }
            if (request.getDiscount() != null) {
                updateProductByDiscount(request.getDiscount(), product.get());
            }
            if (request.getDescription() != null) {
                updateProductByDescription(request.getDescription(), product.get());
            }
            if (request.getImageUrl() != null) {
                updateProductByImageUrl(request.getImageUrl(), product.get());
            }
        }
    }

    // Lúc đầu t không nghĩ xóa đâu nhưng mà nếu chỉ thay đổi trạng thái thì nó khác mẹ gì update đâu???
    @Transactional
    public void deleteProduct(DeleteProductRequest request) {
        deleteProductById(request.getProductId());
    }

    // ADMIN !!~!~!~!~!

    //Gán thêm cái lấy danh sách cho bờ rồ duck là xong rồi!!
    public Map<String, List<AdminProductResponse>> getAllProductsWithDetail() {
        Map<String, List<AdminProductResponse>> map = new HashMap<>();

        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();
            List<AdminProductResponse> adminProductResponses = products.stream() // Em xin copy của anh trên!
                    .map(product -> new AdminProductResponse(
                            product.getId(), product.getName(),
                            productType.getName(), product.getQuantity(),
                            product.isActive(), product.getProductDetail().getPrice(),
                            product.getProductDetail().getDiscount(),
                            product.getProductDetail().getPostedDate(),
                            product.getProductDetail().getSale(),
                            product.getProductDetail().getDescription(),
                            product.getProductDetail().getImageUrl()
                    )).toList();

            map.put(productType.getName(), adminProductResponses);
        }
        return map;
    }

}
