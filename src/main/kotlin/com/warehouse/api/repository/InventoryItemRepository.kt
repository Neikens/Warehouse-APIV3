package com.warehouse.api.repository

import com.warehouse.api.model.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface InventoryItemRepository : JpaRepository<InventoryItem, Long> {
    fun findByProductIdAndWarehouseId(productId: Long, warehouseId: Long): InventoryItem?

    fun findByWarehouseId(warehouseId: Long): List<InventoryItem>

    fun findByProductId(productId: Long): List<InventoryItem>

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= :threshold")
    fun findItemsBelowThreshold(threshold: BigDecimal): List<InventoryItem>
}