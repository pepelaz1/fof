package ru.pepelaz.fof.database

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField



@DatabaseTable(tableName = "locations")
data class Location (

    @DatabaseField(generatedId = true)
    var Id: Int? = null,

    @DatabaseField
    var Name: String = "",

    @DatabaseField
    var Notes: String = "",

    @DatabaseField
    var Latitude: Double? = 0.toDouble(),

    @DatabaseField
    var Longitude: Double? = 0.toDouble()
)

class LocationDao {

    companion object {
        lateinit var dao: Dao<Location, Int>
    }

    init {
        dao = DatabaseHelper.getDao(Location::class.java)
    }

    fun add(location: Location) = dao.createOrUpdate(location)

    fun update(location: Location) = dao.update(location)

    fun delete(location: Location) = dao.delete(location)

    fun queryForAll() = dao.queryForAll()

    fun getById(id: Int) : Location {
        return dao.queryForId(id)
    }

    fun removeAll() {
        for (location in queryForAll()) {
            dao.delete(location)
        }
    }

}