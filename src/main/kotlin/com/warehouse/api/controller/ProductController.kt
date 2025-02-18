package com.warehouse.api.controller

import com.warehouse.api.model.Product
import com.warehouse.api.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {
    
    @GetMapping
    fun getAllProducts(): List<Product> = productService.getAllProducts()
    
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): Product = productService.getProductById(id)
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody product: Product): Product = productService.createProduct(product)
}
