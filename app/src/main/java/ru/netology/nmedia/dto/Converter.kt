package ru.netology.nmedia.dto

class Converter {
    object converter {
        fun converter(value: Int): String {
            val convertedValue = when(value) {
                in 1 .. 999 -> "$value"
                in 1000 .. 1099 -> "${(value/1000)}K"
                in 1100 .. 9999 -> "${String.format("%.1f", value.toDouble()/1000)}K"
                in 10000 .. 999999 -> "${(value/1000)}K"
                else -> "${String.format("%.1f", value.toDouble()/1_000_000)}М"
            }
            return convertedValue
        }
    }
}
