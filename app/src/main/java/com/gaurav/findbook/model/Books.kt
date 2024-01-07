package com.gaurav.findbook.model

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)

data class Item(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)