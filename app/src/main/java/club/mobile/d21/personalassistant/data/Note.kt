package club.mobile.d21.personalassistant.data

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.time.Instant

@Entity(tableName = "note")
data class Note(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "date") val date: Instant
)
@Dao
interface NoteDAO{
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = :hqaId")
    fun getNoteById(hqaId: Int): Note

    @Query("SELECT * FROM note WHERE date(date) = date('now')")
    fun getAllNoteToday(): List<Note>

    @Query("SELECT * FROM note WHERE content = :hqaContent")
    fun findNote(hqaContent: String): Note

    @Query("SELECT COUNT(*) FROM note")
    fun getRowCount(): Int

    @Insert
    fun addNote(newNote: Note)

    @Update
    fun updateNote(vararg wrongNote: Note)
    //vararg là có thể truyền vào nhiều hơn 1 đối tượng Note và khi đó sẽ tìm theo khóa chính
    //và cập nhật thông tin

    @Delete
    fun deleteNote(oldNote: Note)

    @Query("DELETE FROM note")
    fun clearAllNote()
}