package com.warehouse.api.controller

import com.warehouse.api.model.InventoryItem
import com.warehouse.api.service.InventoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory", description = "Inventory management endpoints")
class InventoryController(private val inventoryService: InventoryService) {
    
    @GetMapping
    @Operation(summary = "Get all inventory items")
    fun getAllInventory(): List<InventoryItem> = inventoryService.getAllInventoryItems()
    
    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by ID")
    fun getInventoryItem(@PathVariable id: Long): InventoryItem = 
        inventoryService.getInventoryItemById(id)
    
    @PutMapping("/{productId}/{warehouseId}")
    @Operation(summary = "Update inventory quantity")
    fun updateInventory(
        @PathVariable productId: Long,
        @PathVariable warehouseId: Long,
        @RequestParam quantity: BigDecimal
    ): InventoryItem = inventoryService.updateInventoryQuantity(productId, warehouseId, quantity)

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "Get inventory items by warehouse")
    fun getInventoryByWarehouse(@PathVariable warehouseId: Long): List<InventoryItem> =
        inventoryService.getInventoryByWarehouse(warehouseId)

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory items by product")
    fun getInventoryByProduct(@PathVariable productId: Long): List<InventoryItem> =
        inventoryService.getInventoryByProduct(productId)
}
