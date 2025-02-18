package com.warehouse.api.service

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service

@Service
class MetricsService(private val registry: MeterRegistry) {
    
    fun recordTransaction(type: String) {
        registry.counter("warehouse.transactions", "type", type).increment()
    }

    fun recordProductOperation(operation: String) {
        registry.counter("warehouse.products", "operation", operation).increment()
    }

    fun updateInventoryLevel(warehouseId: Long, level: Double) {
        registry.gauge("warehouse.inventory.level", level)
    }
}
