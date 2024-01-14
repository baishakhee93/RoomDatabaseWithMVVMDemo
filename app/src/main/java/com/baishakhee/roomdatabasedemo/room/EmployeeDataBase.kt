package com.baishakhee.roomdatabasedemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baishakhee.roomdatabasedemo.model.EmployeeModel

@Database(entities = [EmployeeModel::class], version = 1, exportSchema = false)
abstract class EmployeeDataBase : RoomDatabase() {

    abstract fun empDao(): DAOAccess

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDataBase? = null

        // Method to get the database instance
        fun getDatabase(context: Context): EmployeeDataBase {
            // If INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDataBase::class.java,
                    "EMP_DATABASE"
                )
                    .fallbackToDestructiveMigration() // Handle migrations
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}