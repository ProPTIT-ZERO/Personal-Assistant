package club.mobile.d21.personalassistant.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant

@ProvidedTypeConverter
class InstantConverter {
    @TypeConverter
    fun fromInstantToMilli(instant: Instant): Long {
        return instant.toEpochMilli()
    }
    @TypeConverter
    fun fromMilliToInstant(millis: Long): Instant {
        return Instant.ofEpochMilli(millis)
    }
    @TypeConverter
    fun fromStringToInstant(string: String):Instant{
        return Instant.parse(string)
    }
    @TypeConverter
    fun fromInstantToString(instant: Instant):String{
        return instant.toString()
    }
}