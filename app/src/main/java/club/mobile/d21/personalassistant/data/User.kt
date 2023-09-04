package club.mobile.d21.personalassistant.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.time.Instant

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: Instant?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "email") val email: String?
)

@Dao
interface UserDAO{
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id = :hqaId")
    fun getUserById(hqaId: Int)

    @Query("SELECT * FROM user WHERE name = :hqaName")
    fun getUserByName(hqaName: String)

    @Query("SELECT * FROM user WHERE phone = :hqaPhone")
    fun getUserByPhone(hqaPhone: String)

    @Query("SELECT * FROM user WHERE email = :hqaEmail")
    fun getUserByEmail(hqaEmail: String)

    @Insert
    fun addUser(newUser: User)

    @Update
    fun updateUser(vararg wrongUser: User)
    //vararg là có thể truyền vào nhiều hơn 1 đối tượng User và khi đó sẽ tìm theo khóa chính
    //và cập nhật thông tin

    @Delete
    fun deleteUser(oldUser: User)
}
