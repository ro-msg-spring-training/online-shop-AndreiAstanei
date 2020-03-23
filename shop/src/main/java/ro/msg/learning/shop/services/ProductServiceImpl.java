package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.ProductNotFoundException;
import ro.msg.learning.shop.mappers.ProductMapper;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(productMapper::mapProductToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Integer id) throws ProductNotFoundException {
        Optional<Product> receivedProductFromDatabase = productRepository.findById(id);
        if (receivedProductFromDatabase.isPresent()) {
            return productMapper.mapProductToProductDTO(receivedProductFromDatabase.get());
        } else {
            throw new ProductNotFoundException("Couldn't find the product with the specified id!");
        }
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

            auxProduct.setName(newProductValues.getName());
            auxProduct.setDescription(newProductValues.getDescription());
            auxProduct.setPrice(newProductValues.getPrice());
            auxProduct.setWeight(newProductValues.getWeight());
            auxProduct.setImageUrl(newProductValues.getImageUrl());
            auxProduct.setCategory(productCategoryRepository.findByNameEquals(newProductValues.getProductCategoryName()));
            auxProduct.setSupplier(supplierRepository.findByNameEquals(newProductValues.getProductSupplierName()));

            // And save the new data to the database
            productDTO = productMapper.mapProductToProductDTO(productRepository.save(auxProduct));
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

        // add the new product to the database
        newlyCreatedProduct = productMapper.mapProductToProductDTO(productRepository.save(toBeCreatedProduct));

        return newlyCreatedProduct;
    }
}


