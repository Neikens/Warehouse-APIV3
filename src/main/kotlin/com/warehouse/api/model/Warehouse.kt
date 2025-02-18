package com.warehouse.api.model

import jakarta.persistence.*

@Entity
@Table(name = "warehouses")
data class Warehouse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true)
    val code: String,
    
    val name: String
)
