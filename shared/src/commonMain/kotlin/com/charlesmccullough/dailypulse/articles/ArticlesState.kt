package com.charlesmccullough.dailypulse.articles

data class ArticlesState (
    val articles: List<Article> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
