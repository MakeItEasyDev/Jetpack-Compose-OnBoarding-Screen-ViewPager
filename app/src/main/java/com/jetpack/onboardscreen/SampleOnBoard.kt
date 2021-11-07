package com.jetpack.onboardscreen

data class SampleOnBoard(
    val image: Int,
    val title: String,
    val desc: String
)

val onBoardItem = listOf(
    SampleOnBoard(
        R.drawable.onboard1,
        "Make it Easy One",
        "Lorem Ipsum is simply dummy text of the printing and typesetting Industry."
    ),
    SampleOnBoard(
        R.drawable.onboard2,
        "Make it Easy Two",
        "Lorem Ipsum is simply dummy text of the printing and typesetting Industry."
    ),
    SampleOnBoard(
        R.drawable.onboard3,
        "Make it Easy Three",
        "Lorem Ipsum is simply dummy text of the printing and typesetting Industry."
    )
)