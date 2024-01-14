package com.baishakhee.roomdatabasedemo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.repository.EmployeeRepository

class EmpViewModel:ViewModel() {

    var liveDataEmp: LiveData<EmployeeModel>? = null
    private val _fromDateText = MutableLiveData<String>()
    val fromDateText: LiveData<String> get() = _fromDateText

    private val _saveBtnClicked = MutableLiveData<Unit>()
    val saveBtnClicked: LiveData<Unit> get() = _saveBtnClicked

    private val _backBtnClicked = MutableLiveData<Unit>()
    val backBtnClicked: LiveData<Unit> get() = _backBtnClicked

    fun insertData(context: Context, empName: String, empDepartment: String, empDOJ: String, empSalary: String, empContactNumber: String, empAddresss: String) {
        EmployeeRepository.insertData(context,empName, empDepartment,empDOJ,empSalary,empContactNumber,empAddresss)
    }


    fun setFromDateText(date: String) {
        _fromDateText.value = date
    }

    fun onSaveBtnClick() {
        _saveBtnClicked.value = Unit
    }
    fun onBackBtnClick() {
        _backBtnClicked.value = Unit
    }
}