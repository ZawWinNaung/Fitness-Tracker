package com.zawwinnaung.fitnesstracker.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun formatLongToReadableTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return buildString {
        if (hours > 0) {
            append("$hours hour${if (hours > 1) "s" else ""} ")
        }
        if (minutes > 0 || hours > 0) {
            append("$minutes minute${if (minutes > 1) "s" else ""} ")
        }
        append("$remainingSeconds second${if (remainingSeconds > 1) "s" else ""}")
    }.trim()
}

fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}