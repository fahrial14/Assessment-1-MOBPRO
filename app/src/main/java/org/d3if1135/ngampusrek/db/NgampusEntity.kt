package org.d3if1135.ngampusrek.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ngampus")
data class NgampusEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var name: String,
    var major_group: String,
    var course_group: String, //course_group
    var time: Float,
    var isStudy: Boolean

)
