package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import com.example.backend.entity.ProductType;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.product.HandleProductRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.product.*;
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

    public ProductType getProductTypeByName(String name) {
        return productTypeRepository.findByName(name);
    }

    //Lấy tất cả producttype
    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    //Lấy product theo id nè!
    public Optional<Product> getProductById(long id) {
        return productRepository.findAllById(id);
    }

    public List<Product> getSellerProduct(Seller seller) {
        return productRepository.findAllBySeller(seller);
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
    public void updatePriceByProduct(long price, Product product) {
        productDetailRepository.updatePriceByProduct(price, product);
    }

    @Transactional
    public void updateSystemFeeByProduct(long systemFee, Product product) {
        productDetailRepository.updateSystemFeeByProduct(systemFee, product);
    }

    @Transactional
    public void updateProductByDiscount(int discount, Product product) {
        productDetailRepository.updateDiscountByProduct(discount, product);
    }

    @Transactional
    public void updateProductByDescription(String description, Product product) {
        productDetailRepository.updateDescriptionByProduct(description, product);
    }

    @Transactional
    public void updateProductByImageUrl(String imageUrl, Product product) {
        productDetailRepository.updateImageUrlByProduct(imageUrl, product);
    }

    @Transactional
    public void deleteProductById(long productId) {
        productRepository.deleteProductById(productId);
    }

    @Transactional
    public void deleteProductDetailById(long productDetailId) {
        productDetailRepository.deleteProductDetailById(productDetailId);
    }

    public boolean isSellerProduct(long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Product> existingProduct = getProductById(productId);
        return existingProduct.map(product -> product.getSeller().getAccount().getUsername().equals(username)).orElse(false);
    }

    //PUBLIC!!!!!!!!!

    // Cái này là lấy product type rồi thêm vào phẩn phản hồi product type để kiểm soát thông tin phản hồi tránh lộ thông tin nhạy cảm!!!
    // Cái này liên kết với lấy tất cả producttype á!
    public ProductTypeListResponse getProductTypes() {
        return new ProductTypeListResponse(getAllProductType().stream()
                .map(productType -> new ProductTypeResponse(productType.getId(), productType.getName()))
                .collect(Collectors.toList()));
    }

    // Còn cái này thì liên kết với ông products để lấy danh sách sản phẩm, tất nhiên sẽ phân loại theo loại sản phẩm
    public ProductListWithTypeResponse getAllProductSplitWithTypeId() { //Product sẽ tự đông phân loại theo type nha!
        Map<String, List<ProductResponse>> map = new HashMap<>();

        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();
            List<ProductResponse> productResponse = products.stream()
                    .filter(Product::isActive) // Nếu bị Seller hoặc Admin đóng thì cũng dell hiện ra!!!
                    .filter(product -> product.getQuantity() > 0) //Nếu hết hàng thì không hiển thị ra
                    .map(product -> new ProductResponse(
                    product.getId(), product.getName(),
                    productType.getName(),
                    product.getProductDetail().getProductPrice() // Lấy giá cộng tới phí hệ thống 10% hoa hồng
                            + product.getProductDetail().getSystemFee(),
                    product.getProductDetail().getDiscount(),
                    product.getProductDetail().getImageUrl())).toList();

            map.put(productType.getName(), productResponse);
        }

        return new ProductListWithTypeResponse(map);
    }

    //Cái này là lấy thông tin chi tiết của sản phẩm nếu người dùng cần xem nè!
    public UserProductDetailResponse getUserProductDetail(long productId) {
        Optional<Product> existingProduct = getProductById(productId);
        return existingProduct
                .filter(Product::isActive) // Này copy của thằng trên thôi ~~
                .map(product -> new UserProductDetailResponse(
                        product.getSeller().getFullname(),
                        product.getProductDetail().getDescription())
                ).orElse(null);
    }

    public ProductListResponse searchProduct(String keyWord) {
        return new ProductListResponse(getProductsContainKeyword(keyWord).stream()
                .filter(Product::isActive) // Này copy của thằng trên với thằng trên nữa!!
                .filter(product -> product.getQuantity() > 0)
                .map(product -> new ProductResponse(
                        product.getId(), product.getName(),
                        product.getProductType().getName(),
                        product.getProductDetail().getProductPrice() // Lấy giá cộng tới phí hệ thống 10% hoa hồng
                                + product.getProductDetail().getSystemFee(),
                        product.getProductDetail().getDiscount(),
                        product.getProductDetail().getImageUrl())
                ).toList());
    }

    // SELLERRRRR AND ADMINNNN!!!!!!!!!!

    // Phần này là của seller lấy danh sách sản phẩm mà họ bán nè!
    public ProductListWithTypeResponse getMyProducts() {

        Map<String, List<ProductResponse>> map = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ProductType> productTypes = getAllProductType();
        for (ProductType productType : productTypes) {
            List<Product> products = productType.getProducts();
            List<ProductResponse> productResponse = products.stream()
                    .filter(product -> product.getSeller().getAccount().getUsername().equals(username))
                    .map(product -> new ProductResponse(
                            product.getId(), product.getName(),
                            productType.getName(),
                            product.getProductDetail().getProductPrice(), // Lấy giá gốc
                            product.getProductDetail().getDiscount(),
                            product.getProductDetail().getImageUrl())).toList();

            map.put(productType.getName(), productResponse);
        }

        return new ProductListWithTypeResponse(map);
    }

    public SellerProductDetailResponse getSellerProductDetail(long productId) {
        Optional<Product> existingProduct = getProductById(productId);
        return existingProduct
                .map(product -> new SellerProductDetailResponse(
                        product.getQuantity(), product.isActive(),
                        product.getProductDetail().getPostedDate(),
                        product.getProductDetail().getSale(),
                        product.getProductDetail().getDescription()
                        )
                ).orElse(null);
    }

    // Cái này là liên kết với add-product để thêm product mới.
    public StatusResponse createProduct(HandleProductRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (request != null) {
            Product product = saveProduct(new Product(
                    sellerService.getSellerByUserName(username),
                    getProductTypeByName(request.getProductTypeName()),
                    request.getProductName(), request.getQuantity(), request.isActive()));
            saveProductDetail(new ProductDetail(product, request.getPrice(), Math.round(request.getPrice() * 0.05),
                    request.getDiscount(), request.getDescription(), request.getImageUrl()));
            return new StatusResponse(true, "Created!");
        }
        return new StatusResponse(false, "Invalid product detail!");
    }

    //Cập nhật thông tin bờ rồ duck ở chỗ này nè!!!
    @Transactional
    public StatusResponse updateProduct(Long productId, HandleProductRequest request) {
        Optional<Product> product = getProductById(productId);

        if (product.isPresent() && isSellerProduct(productId)) {
            if (request.getProductTypeName() != null) {
                updateProductByType(getProductTypeByName(request.getProductTypeName()), product.get());
            }
            if (request.getProductName() != null) {
                updateProductByName(request.getProductName(), product.get());
            }
            if (request.getQuantity() != product.get().getQuantity()) { // Số lượng thay đổi được nè!
                updateProductByQuantity(request.getQuantity(), product.get());
            }
            if (request.isActive() != product.get().isActive()) {
                updateProductByActive(request.isActive(), product.get());
            }
            if (request.getPrice() != product.get().getProductDetail().getProductPrice()) {
                updatePriceByProduct(request.getPrice(), product.get());
                updateSystemFeeByProduct(Math.round(request.getPrice() * 0.05), product.get());
            }
            if (request.getDiscount() != product.get().getProductDetail().getDiscount()) {
                updateProductByDiscount(request.getDiscount(), product.get());
            }
            if (request.getDescription() != null) {
                updateProductByDescription(request.getDescription(), product.get());
            }
            if (request.getImageUrl() != null) {
                updateProductByImageUrl(request.getImageUrl(), product.get());
            }
            return new StatusResponse(true, "Updated!");
        }
        return new StatusResponse(false, "Invalid productID!");
    }

    // Lúc đầu t không nghĩ xóa đâu nhưng mà nếu chỉ thay đổi trạng thái thì nó khác mẹ gì update đâu???
    @Transactional
    public StatusResponse deleteProduct(Long productId) {
        Optional<Product> product = getProductById(productId);
        if (product.isPresent() && isSellerProduct(productId)) {
            deleteProductDetailById(product.get().getProductDetail().getId()); // Trước mắt tạm thời làm như vầy!
            deleteProductById(productId);
            return new StatusResponse(true, "Deleted!");
        }
        return new StatusResponse(false, "Invalid productID!");
    }

}
