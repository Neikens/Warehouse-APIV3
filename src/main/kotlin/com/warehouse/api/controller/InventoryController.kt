package com.warehouse.api.controller

import com.warehouse.api.model.InventoryItem
import com.warehouse.api.service.InventoryService
import com.warehouse.api.service.InventoryTrackingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory", description = "Inventory management endpoints")
class InventoryController(
    private val inventoryService: InventoryService,
    private val inventoryTrackingService: InventoryTrackingService
) {

    @GetMapping
    @Operation(summary = "Get all inventory items")
    fun getAllInventory(): ResponseEntity<List<InventoryItem>> =
        ResponseEntity.ok(inventoryService.getAllInventoryItems())

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by ID")
    fun getInventoryItem(@PathVariable id: Long): ResponseEntity<InventoryItem> =
        ResponseEntity.ok(inventoryService.getInventoryItemById(id))

    @PutMapping("/{productId}/{warehouseId}")
    @Operation(summary = "Update inventory quantity")
    fun updateInventoryQuantity(
        @PathVariable productId: Long,
        @PathVariable warehouseId: Long,
        @RequestBody request: Map<String, String>
    ): ResponseEntity<InventoryItem> {
        val quantity = BigDecimal(request["quantity"])
        return ResponseEntity.ok(
            inventoryService.updateInventoryQuantity(productId, warehouseId, quantity)
        )
    }

    @PostMapping("/{productId}/{warehouseId}/adjust")
    @Operation(summary = "Adjust inventory quantity")
    fun adjustInventory(
        @PathVariable productId: Long,
        @PathVariable warehouseId: Long,
        @RequestParam adjustment: BigDecimal
    ): ResponseEntity<InventoryItem> =
        ResponseEntity.ok(inventoryService.adjustInventory(productId, warehouseId, adjustment))

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "Get inventory items by warehouse")
    fun getInventoryByWarehouse(@PathVariable warehouseId: Long): ResponseEntity<List<InventoryItem>> =
        ResponseEntity.ok(inventoryService.getInventoryByWarehouse(warehouseId))

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory items by product")
    fun getInventoryByProduct(@PathVariable productId: Long): ResponseEntity<List<InventoryItem>> =
        ResponseEntity.ok(inventoryService.getInventoryByProduct(productId))

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items")
    fun getLowStockItems(@RequestParam threshold: BigDecimal): ResponseEntity<List<InventoryItem>> =
        ResponseEntity.ok(inventoryTrackingService.checkLowStock(threshold))

    @GetMapping("/report/{warehouseId}")
    @Operation(summary = "Generate inventory report for warehouse")
    fun generateWarehouseReport(@PathVariable warehouseId: Long): ResponseEntity<Map<String, Any>> =
        ResponseEntity.ok(inventoryTrackingService.generateInventoryReport(warehouseId))
}