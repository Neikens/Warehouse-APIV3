package com.warehouse.api.service

import com.warehouse.api.model.Product
import com.warehouse.api.model.Warehouse
import com.warehouse.api.repository.ProductRepository
import com.warehouse.api.repository.WarehouseRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class InventoryTrackingService(
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
    private val metricsService: MetricsService
) {
    fun getInventoryLevels(warehouseId: Long): Map<String, BigDecimal> {
        val warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow { RuntimeException("Warehouse not found") }
            
        // Use warehouse to get inventory levels
        return warehouse.incomingTransactions.groupBy { it.product.code }
            .mapValues { (_, transactions) ->
                transactions.fold(BigDecimal.ZERO) { acc, transaction ->
                    acc.add(transaction.quantity)
                }
            }
    }

    fun checkLowStock(threshold: BigDecimal): List<Map<String, Any>> {
        return warehouseRepository.findAll().flatMap { warehouse ->
            getInventoryLevels(warehouse.id!!)
                .filter { it.value <= threshold }
                .map { (productCode, quantity) ->
                    mapOf(
                        "warehouseId" to warehouse.id,
                        "productCode" to productCode,
                        "quantity" to quantity
                    )
                }
        }
    }

    fun generateInventoryReport(warehouseId: Long): Map<String, Any> {
        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "warehouseId" to warehouseId,
            "inventoryLevels" to getInventoryLevels(warehouseId)
        )
    }
}
