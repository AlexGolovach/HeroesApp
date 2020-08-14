package com.renovavision.heroesapp

import java.io.Serializable

data class Model(
    val model: String,
    val emptyState: String,
    val firstAction: String,
    val secondAction: String = EMPTY
): Serializable