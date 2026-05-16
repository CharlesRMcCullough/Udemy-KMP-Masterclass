package com.charlesmccullough.dailypulse.articles

import com.charlesmccullough.dailypulse.BaseViewModel
import com.charlesmccullough.dailypulse.CancellableHandle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ArticlesViewModel: BaseViewModel()  {

    private val mockArticles = listOf(
        Article(
            "Stock market today: Live updates - CNBC",
            "Futures were higher in premarket trading as Wall Street tried to regain its footing.",
            "2023-11-09",
            "https://image.cnbcfm.com/api/v1/image/107326078-1698758530118-gettyimages-1765623456-wall26362_igj6ehhp.jpeg?v=1698758587&w=1920&h=1080"
        ),
        Article(
            "Best iPhone Deals (2023): Carrier Deals, Unlocked iPhones",
            "Apple's smartphones rarely go on sale, but if you’re looking to upgrade (or you're gift shopping), here are a few cost-saving options.",
            "2023-11-09",
            "https://media.wired.com/photos/622aa5c8cca6acf55fb70b57/191:100/w_1280,c_limit/iPhone-13-Pro-Colors-SOURCE-Apple-Gear.jpg",
        ),
        Article(
            "Samsung Galaxy S23 Ultra review: The best Android phone",
            "The Galaxy S23 Ultra is a massive phone with a massive price tag, but it's also the most powerful Android phone you can buy.",
            "2023-11-09",
            "https://media.wired.com/photos/63e69f8d5e165c71b6d0d9f4/191:100/w_1280,c_limit/Samsung-Galaxy-S23-Ultra-Gear.jpg",
        ),
    )

    private val _articlesState: MutableStateFlow<ArticlesState> = MutableStateFlow(ArticlesState(loading = true))

    val articlesState: StateFlow<ArticlesState>
        get() = _articlesState

    fun observeArticlesState(onChange: (ArticlesState) -> Unit): CancellableHandle {
        val job = scope.launch {
            articlesState.collect { state ->
                onChange(state)
            }
        }

        return CancellableHandle {
            job.cancel()
        }
    }

    init {
        loadArticles()
    }

    fun loadArticles() {
        scope.launch {
            _articlesState.emit(ArticlesState(loading = true))
            try {
                val fetchedArticles = fetchArticles()
                delay(500.milliseconds)
                _articlesState.emit(ArticlesState(articles = fetchedArticles))
            } catch (e: Exception) {
                _articlesState.emit(ArticlesState(error = e.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun fetchArticles(): List<Article> = mockArticles
}
