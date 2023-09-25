package club.mobile.d21.personalassistant.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Note::class, User::class], version = 7)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
    abstract fun noteDAO(): NoteDAO
    abstract fun userDAO(): UserDAO
    companion object{
        private val instantConverter = InstantConverter()
        @Synchronized
        fun getNoteDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "noteDatabase.db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addTypeConverter(instantConverter)
                .build()
        }
        @Synchronized
        fun getTaskDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "taskDatabase.db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addTypeConverter(instantConverter)
                .build()
        }
    }
}