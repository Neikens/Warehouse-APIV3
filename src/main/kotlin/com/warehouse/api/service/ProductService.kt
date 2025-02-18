package com.warehouse.api.service

import com.warehouse.api.model.Product
import com.warehouse.api.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product = productRepository.findById(id)
        .orElseThrow { NoSuchElementException("Product not found with id: $id") }

    fun getProductByCode(code: String): Product? = productRepository.findByCode(code)

    @Transactional
    fun createProduct(product: Product): Product = productRepository.save(product)

    @Transactional
    fun updateProduct(id: Long, product: Product): Product {
        if (!productRepository.existsById(id)) {
            throw NoSuchElementException("Product not found with id: $id")
        }
        return productRepository.save(product.copy(id = id))
    }

    @Transactional
    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw NoSuchElementException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }
}