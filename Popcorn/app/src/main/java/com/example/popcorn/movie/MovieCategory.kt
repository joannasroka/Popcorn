package com.example.popcorn.movie

import android.graphics.Color

enum class MovieCategory(val displayName: String, val borderColor: Int) {
    ACTION("Action", Color.argb(100, 152, 37, 61)),
    COMEDY("Comedy", Color.argb(100, 230, 227, 57)),
    HORROR("Horror", Color.argb(100, 66, 245, 144)),
    THRILLER("Thriller", Color.argb(100, 180, 41, 227)),
    DRAMA("Drama", Color.argb(100, 63, 86, 170))
}