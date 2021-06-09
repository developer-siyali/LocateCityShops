package com.example.locatecityshops.model

data class City (
    val id: Int,
    val name: String,
    val malls: List<Mall>
)