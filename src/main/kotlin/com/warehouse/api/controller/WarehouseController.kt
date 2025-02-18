package com.warehouse.api.controller

import com.warehouse.api.model.Warehouse
import com.warehouse.api.service.WarehouseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/warehouses")
class WarehouseController(private val warehouseService: WarehouseService) {
    
    @GetMapping
    fun getAllWarehouses(): List<Warehouse> = warehouseService.getAllWarehouses()
    
    @GetMapping("/{id}")
    fun getWarehouse(@PathVariable id: Long): Warehouse = warehouseService.getWarehouseById(id)
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWarehouse(@RequestBody warehouse: Warehouse): Warehouse = 
        warehouseService.createWarehouse(warehouse)
}
