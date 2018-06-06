package co.androidbaseappkotlinmvvm.entities

data class Review(

        var id: String,
        var author: String,
        var content: String? = null
)