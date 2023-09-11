package club.mobile.d21.personalassistant.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.time.*

enum class Priority{
    CRITICAL,
    IMPORTANT,
    LOW_PRIORITY
}
@Entity(tableName = "task")
data class Task(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "task_name") val taskName: String?,
    @ColumnInfo(name = "priority") val priority: Priority?,
    @ColumnInfo(name = "start_time") val startTime: Instant?,
    @ColumnInfo(name = "deadline") val deadline: Instant?,
)

@Dao
interface TaskDAO{
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM note WHERE id = :hqaId")
    fun getTaskById(hqaId: Int): Task

    @Query("SELECT * FROM task WHERE priority = 'CRITICAL'")
    fun getCriticalTasks(): List<Task>

    @Query("SELECT * FROM task WHERE priority = 'IMPORTANT'")
    fun getImportantTasks(): List<Task>

    @Query("SELECT * FROM task WHERE priority = 'LOW_PRIORITY'")
    fun getLowPriorityTasks(): List<Task>

    @Insert
    fun addTask(newTask: Task)

    @Update
    fun updateTask(vararg wrongTask: Task)
    //vararg là có thể truyền vào nhiều hơn 1 đối tượng Task và khi đó sẽ tìm theo khóa chính
    //và cập nhật thông tin

    @Delete
    fun deleteTask(oldTask : Task)
}