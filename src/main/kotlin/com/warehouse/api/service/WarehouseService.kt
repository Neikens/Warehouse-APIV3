package com.warehouse.api.service

import com.warehouse.api.model.Warehouse
import com.warehouse.api.repository.WarehouseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WarehouseService(private val warehouseRepository: WarehouseRepository) {
    
    fun getAllWarehouses(): List<Warehouse> = warehouseRepository.findAll()
    
    fun getWarehouseById(id: Long): Warehouse = warehouseRepository.findById(id)
        .orElseThrow { NoSuchElementException("Warehouse not found with id: $id") }
    
    @Transactional
    fun createWarehouse(warehouse: Warehouse): Warehouse = warehouseRepository.save(warehouse)
}
