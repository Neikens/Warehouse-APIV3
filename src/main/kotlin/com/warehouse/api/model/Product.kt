package com.warehouse.api.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true)
    val code: String,
    
    val description: String,
    val barcode: String?,
    val category: String
)
