package co.androidbaseappkotlinmvvm.domain.entities

data class ReviewEntity (
        var id: String,
        var author: String,
        var content: String? = null
)