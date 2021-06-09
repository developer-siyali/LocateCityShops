package com.example.locatecityshops.model

data class Mall (
    val id: Int,
    val name: String,
    val shops: List<Shop>
)