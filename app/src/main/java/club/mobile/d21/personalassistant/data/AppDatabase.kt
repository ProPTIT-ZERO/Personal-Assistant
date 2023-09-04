package club.mobile.d21.personalassistant.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class, Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
    abstract fun noteDAO(): NoteDAO
    abstract fun userDAO(): UserDAO
}