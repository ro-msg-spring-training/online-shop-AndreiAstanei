package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.exceptions.ProductNotCreatedException;
import ro.msg.learning.shop.exceptions.ProductNotFoundException;
import ro.msg.learning.shop.mappers.ProductMapper;
import ro.msg.learning.shop.repositories.jdbcBasedRepositories.JDBCBasedProductCategoryRepository;
import ro.msg.learning.shop.repositories.jdbcBasedRepositories.JDBCBasedProductRepository;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final JDBCBasedProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final JDBCBasedProductRepository jdbcBasedProductRepository;

    @Override
    public List<ProductDTO> getProducts() {
        return jdbcBasedProductRepository.findAll().stream().map(productMapper::mapProductToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Integer id) throws ProductNotFoundException {
        Optional<Product> receivedProductFromDatabase = jdbcBasedProductRepository.findById(id);
        if (receivedProductFromDatabase.isPresent()) {
            return productMapper.mapProductToProductDTO(receivedProductFromDatabase.get());
        } else {
            throw new ProductNotFoundException("Couldn't find the product with the specified id!");
        }
    }

    @Override
    public String deleteProductById(Integer id) {
        if (jdbcBasedProductRepository.findById(id).isPresent()) {
            jdbcBasedProductRepository.deleteById(id);
            return "Product deleted!";
        } else {
            return "Product not found!";
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO newProductValues) throws ProductNotFoundException {
        Optional<Product> receivedProductFromDatabase = jdbcBasedProductRepository.findById(newProductValues.getId());
        Optional<ProductCategory> receivedProductCategory = productCategoryRepository.findByNameEquals(newProductValues.getProductCategoryName());

        // if the product is present in the database, update the product's data
        if (receivedProductFromDatabase.isPresent() && receivedProductCategory.isPresent()) {
            Product auxProduct = receivedProductFromDatabase.get();

            auxProduct.setName(newProductValues.getName());
            auxProduct.setDescription(newProductValues.getDescription());
            auxProduct.setPrice(newProductValues.getPrice());
            auxProduct.setWeight(newProductValues.getWeight());
            auxProduct.setImageUrl(newProductValues.getImageUrl());
            auxProduct.setCategory(receivedProductCategory.get());
            auxProduct.setSupplier(supplierRepository.findByNameEquals(newProductValues.getProductSupplierName()));

            // And save the new data to the database
            return productMapper.mapProductToProductDTO(jdbcBasedProductRepository.save(auxProduct).get());
        } else {
            throw new ProductNotFoundException("Couldn't find the product with the specified id!");
        }
    }

    @Override
    public ProductDTO createProduct(ProductDTO productData) throws ProductNotCreatedException {
        ProductDTO newlyCreatedProduct = null;
        Optional<ProductCategory> productCategory = productCategoryRepository.findByNameEquals(productData.getProductCategoryName());

        if (productCategory.isPresent()) {
            // Get the required information for the new product from the DTO and from the database
            Product toBeCreatedProduct = productMapper.mapProductDTOToProduct(productData);
            toBeCreatedProduct.setCategory(productCategory.get());
            toBeCreatedProduct.setSupplier(supplierRepository.findByNameEquals(productData.getProductSupplierName()));

            // add the new product to the database
            newlyCreatedProduct = productMapper.mapProductToProductDTO(jdbcBasedProductRepository.save(toBeCreatedProduct).get());
        }

        if (newlyCreatedProduct != null) {
            return newlyCreatedProduct;
        } else {
            throw new ProductNotCreatedException("Something went wrong while creating the new product! Please check the data and try again.");
        }
    }
}


