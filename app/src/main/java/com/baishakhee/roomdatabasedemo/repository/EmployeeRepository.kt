package com.baishakhee.roomdatabasedemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.room.DAOAccess
import com.baishakhee.roomdatabasedemo.room.EmployeeDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EmployeeRepository(private val daoAccess: DAOAccess) {

    fun getAllEmp(): LiveData<List<EmployeeModel>> {
        return daoAccess.getAllEmp()
    }
    // A LiveData variable that holds the list of employees
    private val _employees = MutableLiveData<List<EmployeeModel>>()
    val employees: LiveData<List<EmployeeModel>> get() = _employees

    fun refreshEmployeeData() {
        // Observe changes in Room LiveData and update _employees
        daoAccess.getAllEmp().observeForever { employeeList ->
            _employees.value = employeeList
        }
    }


    companion object {

        // Singleton pattern for the database instance
        private var employeeDataBase: EmployeeDataBase? = null

        // Singleton pattern for the LiveData instance
        private var employeeModel: LiveData<EmployeeModel>? = null

        fun initializeDB(context: Context): EmployeeDataBase {
            return EmployeeDataBase.getDatabase(context)
        }

        fun insertData(
            context: Context,
            empName: String,
            empDepartment: String,
            empDOJ: String,
            empSalary: String,
            empContactNumber: String,
            empAddress: String
        ) {
            employeeDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val empDetails = EmployeeModel(
                    empName,
                    empDepartment,
                    empDOJ,
                    empSalary,
                    empContactNumber,
                    empAddress
                )
                employeeDataBase!!.empDao().insertData(empDetails)
            }
        }

        fun getEmpDetails(context: Context, empName: String): LiveData<EmployeeModel>? {
            employeeDataBase = initializeDB(context)

            // Use a background thread or coroutine for database operations
            employeeModel = employeeDataBase!!.empDao().getEmpDetails(empName)

            return employeeModel
        }
    }

    // ... other repository methods ...

    fun deleteEmployee(employee: EmployeeModel) {
        CoroutineScope(IO).launch {
            daoAccess.deleteEmployee(employee)
        }
    }

    // If you have a delete by ID method
    fun deleteEmployeeById(employeeId: Int) {
        CoroutineScope(IO).launch {
            daoAccess.deleteEmployeeById(employeeId)
        }
    }

    fun deleteAllEmployees() {
        CoroutineScope(IO).launch {
            daoAccess.deleteAllEmployees()
        }
    }

    suspend fun searchEmployees(query: String): List<EmployeeModel> {
        return daoAccess.searchEmployees(query)
    }

}
