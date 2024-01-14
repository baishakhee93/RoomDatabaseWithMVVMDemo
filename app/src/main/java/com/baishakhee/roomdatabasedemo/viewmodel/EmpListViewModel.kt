package com.baishakhee.roomdatabasedemo.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.repository.EmployeeRepository
import com.baishakhee.roomdatabasedemo.room.EmployeeDataBase
import kotlinx.coroutines.launch

class EmpListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmployeeRepository
    val allEmployees: LiveData<List<EmployeeModel>>

    private val _addBtnClicked = MutableLiveData<Unit>()
    val addBtnClicked: LiveData<Unit> get() = _addBtnClicked

    private val _deleteBtnClicked = MutableLiveData<Unit>()
    val deleteBtnClicked: LiveData<Unit> get() = _deleteBtnClicked

    init {
        val dao = EmployeeDataBase.getDatabase(application).empDao()
        repository = EmployeeRepository(dao)
        allEmployees = repository.getAllEmp()
    }

    fun onAddBtnClick() {
        _addBtnClicked.value = Unit
    }


    fun onDeleteBtnClick() {
        _deleteBtnClicked.value = Unit
    }

    fun deleteAllEmployees() {
        repository.deleteAllEmployees()
    }

    // Expose a method to refresh the employee data
    fun refreshEmployeeData() {
        repository.refreshEmployeeData()
    }

    fun deleteEmployee(employee: EmployeeModel) {
        repository.deleteEmployee(employee)
    }

    // If you have a delete by ID method
    fun deleteEmployeeById(employeeId: Int) {
        repository.deleteEmployeeById(employeeId)
    }


    private val _searchResults = MutableLiveData<List<EmployeeModel>>()
    val searchResults: LiveData<List<EmployeeModel>> get() = _searchResults

    fun searchEmployees(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchEmployees(query)
        }
    }
}
