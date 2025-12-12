package com.pascal.movie.domain.mapper

import AuthorDetailsResponse
import ReviewResponse
import ReviewsCatalogResponse
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.remote.dtos.dashboard.DashboardResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesCatalogResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesResponse
import com.pascal.movie.data.remote.dtos.video.VideosCatalogResponse
import com.pascal.movie.data.remote.dtos.video.VideosResponse
import com.pascal.movie.domain.model.AuthorDetails
import com.pascal.movie.domain.model.Dashboard
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.model.MoviesCatalog
import com.pascal.movie.domain.model.Review
import com.pascal.movie.domain.model.ReviewsCatalog
import com.pascal.movie.domain.model.Video
import com.pascal.movie.domain.model.VideosCatalog

fun DashboardResponse.toDomain(): Dashboard {
    return Dashboard(
        code = this.code ?: 0,
        success = this.success ?: false,
        message = this.message.orEmpty()
    )
}

fun MoviesResponse.toDomain(): Movie {
    return Movie(
        backdropPath = backdropPath.orEmpty(),
        id = id ?: 0,
        title = title.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        mediaType = mediaType.orEmpty(),
        adult = adult ?: false,
        originalLanguage = originalLanguage.orEmpty(),
        genreIds = genreIds ?: emptyList(),
        popularity = popularity ?: 0.0,
        releaseDate = releaseDate.orEmpty(),
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}

fun MoviesCatalogResponse.toDomain(): MoviesCatalog {
    return MoviesCatalog(
        page = page ?: 0,
        results = results?.map { it.toDomain() } ?: emptyList(),
        totalPages = totalPages ?: 0,
        totalResults = totalResults ?: 0
    )
}

fun AuthorDetailsResponse.toDomain(): AuthorDetails {
    return AuthorDetails(
        avatarPath = avatarPath.orEmpty(),
        name = name.orEmpty(),
        rating = rating ?: 0.0,
        username = username.orEmpty()
    )
}

fun ReviewResponse.toDomain(): Review {
    return Review(
        author = author.orEmpty(),
        authorDetails = authorDetails?.toDomain() ?: AuthorDetails("", "", 0.0, ""),
        content = content.orEmpty(),
        createdAt = createdAt.orEmpty(),
        id = id.orEmpty(),
        updatedAt = updatedAt.orEmpty(),
        url = url.orEmpty()
    )
}

fun ReviewsCatalogResponse.toDomain(): ReviewsCatalog {
    return ReviewsCatalog(
        id = id ?: 0,
        page = page ?: 0,
        results = results?.map { it.toDomain() } ?: emptyList(),
        totalPages = totalPages ?: 0,
        totalResults = totalResults ?: 0
    )
}

fun VideosResponse.toDomain(): Video {
    return Video(
        id = id.orEmpty(),
        iso3166_1 = iso3166_1.orEmpty(),
        iso639_1 = iso639_1.orEmpty(),
        key = key.orEmpty(),
        name = name.orEmpty(),
        official = official ?: false,
        publishedAt = publishedAt.orEmpty(),
        site = site.orEmpty(),
        size = size ?: 0,
        type = type.orEmpty()
    )
}

fun VideosCatalogResponse.toDomain(): VideosCatalog {
    return VideosCatalog(
        id = id ?: 0,
        results = results?.map { it.toDomain() } ?: emptyList()
    )
}

fun Movie.toEntity() = FavoritesEntity(
    id = id,
    backdropPath = backdropPath,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    adult = adult,
    originalLanguage = originalLanguage,
    genreIds = genreIds,
    popularity = popularity,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun FavoritesEntity.toDomain() = Movie(
    id = id,
    backdropPath = backdropPath.orEmpty(),
    title = title.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = posterPath.orEmpty(),
    mediaType = mediaType.orEmpty(),
    adult = adult ?: false,
    originalLanguage = originalLanguage.orEmpty(),
    genreIds = genreIds.orEmpty(),
    popularity = popularity ?: 0.0,
    releaseDate = releaseDate.orEmpty(),
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)





