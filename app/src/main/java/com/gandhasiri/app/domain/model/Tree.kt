package com.gandhasiri.app.domain.model

data class Tree(
    val id: String = "",
    val farmerId: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val girth: Double = 0.0,
    val height: Double = 0.0,
    val age: Int = 0,
    val healthScore: Int = 100,
    val timestamp: Long = System.currentTimeMillis()
) {
    // Simulated Heartwood Estimation Logic
    // If the tree is younger than 10 years, yield is negligible.
    // Otherwise, yield is estimated based on girth and age.
    val heartwoodEstimateKg: Double
        get() = if (age < 10) 0.0 else (girth * age * 0.05)
}
