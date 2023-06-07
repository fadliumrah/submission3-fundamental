package com.fadli.submission3.data

import com.squareup.moshi.Json

data class SearchResponse(@field:Json(name = "items") val items: List<DataUser>)
