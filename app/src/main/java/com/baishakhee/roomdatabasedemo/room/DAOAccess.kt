package com.baishakhee.roomdatabasedemo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baishakhee.roomdatabasedemo.model.EmployeeModel

@Dao
interface  DAOAccess{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(employeeModel: EmployeeModel)

    @Query("SELECT * FROM Employees WHERE empName =:empName")
    fun getEmpDetails(empName: String?) : LiveData<EmployeeModel>

    @Query("SELECT * FROM Employees")
    fun getAllEmp(): LiveData<List<EmployeeModel>>
    // If you want to delete by ID
    @Query("DELETE FROM employees WHERE id = :employeeId")
    fun deleteEmployeeById(employeeId: Int)

    @Delete
    fun deleteEmployee(employee: EmployeeModel)

    @Query("DELETE FROM Employees")
    suspend fun deleteAllEmployees()


    @Query("SELECT * FROM Employees WHERE empName LIKE '%' || :query || '%' OR empDepartment LIKE '%' || :query || '%'")
    suspend fun searchEmployees(query: String): List<EmployeeModel>
}