package club.mobile.d21.personalassistant.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "status") val status: Boolean = true
)
@Dao
interface AlarmDAO{
    @Query("SELECT * FROM alarm ORDER BY time ASC")
    fun getAll(): List<Alarm>

    @Query("UPDATE alarm SET status = 1 WHERE id = :alarmId")
    fun turnOnAlarm(alarmId: Int?)

    @Query("UPDATE alarm SET status = 0 WHERE id = :alarmId")
    fun turnOffAlarm(alarmId: Int?)

    @Query("SELECT * FROM alarm WHERE time = :time AND name = :name")
    fun getAlarmsWithSameTimeAndName(time: String, name: String): Alarm

    @Query("DELETE FROM alarm")
    fun clearAll()

    @Insert
    fun addAlarm(newAlarm: Alarm)

    @Delete
    fun deleteAlarm(oldAlarm: Alarm)

}