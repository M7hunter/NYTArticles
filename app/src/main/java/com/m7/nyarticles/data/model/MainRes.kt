package com.m7.nyarticles.data.model

data class MainRes<T>(
    val status: String,
    val results: List<T>,
)
