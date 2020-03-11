package ro.msg.learning.shop.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.DTOs.productDto.ProductDTO;
import ro.msg.learning.shop.Entities.Product;
import ro.msg.learning.shop.Repositories.ProductCategoryRepository;
import ro.msg.learning.shop.Repositories.ProductRepository;
import ro.msg.learning.shop.Repositories.SupplierRepository;
import ro.msg.learning.shop.configuration.OrderStrategyConfiguration;
import ro.msg.learning.shop.mappers.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final OrderStrategyConfiguration orderStrategyConfiguration;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(this::convertProductToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Integer id) {
        ProductDTO productDTO = new ProductDTO();

        Optional<Product> receivedProductFromDatabase = productRepository.findById(id);
        if (receivedProductFromDatabase.isPresent()) {
            productDTO = productMapper.mapProductToProductDTO(receivedProductFromDatabase.get());
        }

        return productDTO;
    }

    @Override
    public String deleteProductById(Integer id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return "Product deleted!";
        } else {
            return "Product not found!";
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO newProductValues) {
        ProductDTO productDTO = new ProductDTO();

        Optional<Product> receivedProductFromDatabase = productRepository.findById(newProductValues.getId());

        // if the product is present in the database, update the product's data
        if (receivedProductFromDatabase.isPresent()) {
            Product auxProduct = receivedProductFromDatabase.get();
            Product failSafeProduct = new Product(
                    auxProduct.getId(),
                    auxProduct.getName(),
                    auxProduct.getDescription(),
                    auxProduct.getPrice(),
                    auxProduct.getWeight(),
                    auxProduct.getImageUrl(),
                    auxProduct.getCategory(),
                    auxProduct.getSupplier(),
                    auxProduct.getOrderDetails(),
                    auxProduct.getStocks()
            );

            auxProduct.setName(newProductValues.getName());
            auxProduct.setDescription(newProductValues.getDescription());
            auxProduct.setPrice(newProductValues.getPrice());
            auxProduct.setWeight(newProductValues.getWeight());
            auxProduct.setImageUrl(newProductValues.getImageUrl());
            auxProduct.setCategory(productCategoryRepository.findByNameEquals(newProductValues.getProductCategoryName()));
            auxProduct.setSupplier(supplierRepository.findByNameEquals(newProductValues.getProductSupplierName()));

            try {
                // And save the new data to the database
                productDTO = productMapper.mapProductToProductDTO(productRepository.save(auxProduct));
            } catch (DataIntegrityViolationException exceptionType1) {
                return productMapper.mapProductToProductDTO(failSafeProduct);
            }
        }

        return productDTO;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productData) {
        ProductDTO newlyCreatedProduct;

        // Get the required information for the new product from the DTO and from the database
        Product toBeCreatedProduct = productMapper.mapProductDTOToProduct(productData);
        toBeCreatedProduct.setCategory(productCategoryRepository.findByNameEquals(productData.getProductCategoryName()));
        toBeCreatedProduct.setSupplier(supplierRepository.findByNameEquals(productData.getProductSupplierName()));

        try {
            // add the new product to the database
            newlyCreatedProduct = productMapper.mapProductToProductDTO(productRepository.save(toBeCreatedProduct));
        } catch (DataIntegrityViolationException exceptionType1) {
            return null;
        }

        return newlyCreatedProduct;
    }


    private ProductDTO convertProductToDTO(Product product) {
        return productMapper.mapProductToProductDTO(product);
    }
}


