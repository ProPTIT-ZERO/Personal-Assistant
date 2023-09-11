package club.mobile.d21.personalassistant.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [Task::class, Note::class, User::class], version = 1)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
    abstract fun noteDAO(): NoteDAO
    abstract fun userDAO(): UserDAO
}