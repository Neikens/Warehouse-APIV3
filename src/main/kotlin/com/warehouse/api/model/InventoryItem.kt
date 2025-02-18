package com.warehouse.api.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "inventory_items")
data class InventoryItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    val warehouse: Warehouse,
    
    var quantity: BigDecimal
)
