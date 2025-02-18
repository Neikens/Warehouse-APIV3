package com.warehouse.api.repository

import com.warehouse.api.model.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryItemRepository : JpaRepository<InventoryItem, Long> {
    fun findByProductIdAndWarehouseId(productId: Long, warehouseId: Long): InventoryItem?
}
