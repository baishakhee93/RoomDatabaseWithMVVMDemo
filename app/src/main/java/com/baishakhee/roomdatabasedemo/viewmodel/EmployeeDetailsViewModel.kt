package com.baishakhee.roomdatabasedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeDetailsViewModel : ViewModel(){

    private  val  _backBtnClicked=MutableLiveData<Unit>()
    val backBtnClicked:LiveData<Unit> get() = _backBtnClicked

    fun  onBackBtnClick(){
        _backBtnClicked.value=Unit
    }
}