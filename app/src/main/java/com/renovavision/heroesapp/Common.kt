package com.renovavision.heroesapp

const val MODEL_NAME = "Model"
const val EMPTY = ""

val goblin = Model(
    "goblin.sfb",
    emptyState = "Character|Idle",
    firstAction = "Character|Flying Kick",
    secondAction = "Character|Jab"
)

val zombie = Model(
    "zombie.sfb",
    emptyState = "Zombie|Idle",
    firstAction = "Zombie|Walk"
)

val fighter = Model(
    "fighter.sfb",
    emptyState = "Character|Idle",
    firstAction = "Character|Flying kick",
    secondAction = "Character|Kick"
)

val dancer = Model(
    "dancer.sfb",
    emptyState = "Character|Idle",
    firstAction = "Character|Wave",
    secondAction = "Character|Breakdance"
)