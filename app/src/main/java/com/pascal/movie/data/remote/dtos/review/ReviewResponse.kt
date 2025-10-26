import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetailsResponse(
    @SerialName("avatar_path")
    val avatarPath: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("rating")
    val rating: Double? = null,

    @SerialName("username")
    val username: String? = null
)

@Serializable
data class ReviewResponse(
    @SerialName("author")
    val author: String? = null,

    @SerialName("author_details")
    val authorDetails: AuthorDetailsResponse? = null,

    @SerialName("content")
    val content: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class ReviewsCatalogResponse(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<ReviewResponse>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)
