package me.taste2plate.app.customer

import me.taste2plate.app.models.order.updates.Updates
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*


fun String.toDate(format: String = "dd-MM-yy HH:mm"): String {
    val converter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    converter.timeZone = TimeZone.getTimeZone("+5:30")
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(converter.parse(this)!!)
}


fun String.toDateObject(format: String = "dd-MM-yy HH:mm"): Date {
    val converter = SimpleDateFormat(format, Locale.getDefault())
    return converter.parse(this)!!
}

fun Updates.toSummary() = buildString {
    appendln(note)
    append(createdDate.toDate())
}