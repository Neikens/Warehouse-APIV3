package com.warehouse.api.service

import com.warehouse.api.model.InventoryItem
import com.warehouse.api.repository.InventoryItemRepository
import com.warehouse.api.repository.ProductRepository
import com.warehouse.api.repository.WarehouseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class InventoryService(
    private val inventoryItemRepository: InventoryItemRepository,
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository
) {

    fun getAllInventoryItems(): List<InventoryItem> =
        inventoryItemRepository.findAll()

    fun getInventoryItemById(id: Long): InventoryItem =
        inventoryItemRepository.findById(id)
            .orElseThrow { NoSuchElementException("Inventory item not found with id: $id") }

    fun getInventoryByWarehouse(warehouseId: Long): List<InventoryItem> =
        inventoryItemRepository.findByWarehouseId(warehouseId)

    fun getInventoryByProduct(productId: Long): List<InventoryItem> =
        inventoryItemRepository.findByProductId(productId)

    @Transactional
    fun updateInventoryQuantity(
        productId: Long,
        warehouseId: Long,
        quantity: BigDecimal
    ): InventoryItem {
        val product = productRepository.findById(productId)
            .orElseThrow { NoSuchElementException("Product not found with id: $productId") }

        val warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow { NoSuchElementException("Warehouse not found with id: $warehouseId") }

        val inventoryItem = inventoryItemRepository
            .findByProductIdAndWarehouseId(productId, warehouseId)
            ?: InventoryItem(
                product = product,
                warehouse = warehouse,
                quantity = BigDecimal.ZERO
            )

        inventoryItem.quantity = quantity
        return inventoryItemRepository.save(inventoryItem)
    }

    @Transactional
    fun adjustInventory(
        productId: Long,
        warehouseId: Long,
        adjustment: BigDecimal
    ): InventoryItem {
        val inventoryItem = inventoryItemRepository
            .findByProductIdAndWarehouseId(productId, warehouseId)
            ?: throw NoSuchElementException("Inventory item not found")

        inventoryItem.quantity = inventoryItem.quantity.add(adjustment)
        if (inventoryItem.quantity < BigDecimal.ZERO) {
            throw IllegalStateException("Inventory cannot be negative")
        }

        return inventoryItemRepository.save(inventoryItem)
    }
}