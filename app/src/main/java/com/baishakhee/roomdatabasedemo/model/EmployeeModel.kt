package com.baishakhee.roomdatabasedemo.model
/*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Employees")
data class EmployeeModel(

    @ColumnInfo(name = "empName")
    var empName: String,

    @ColumnInfo(name = "empDepartment")
    var empDepartment: String,

    @ColumnInfo(name = "empDOJ")
    var empDOJ: String,

    @ColumnInfo(name = "empSalary")
    var empSalary: String,

    @ColumnInfo(name = "empContactNumber")
    var empContactNumber: String,

    @ColumnInfo(name = "empAddresss")
    var empAddresss: String,


    ){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}*/

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employees")
data class EmployeeModel(
    @ColumnInfo(name = "empName")
    var empName: String,

    @ColumnInfo(name = "empDepartment")
    var empDepartment: String,

    @ColumnInfo(name = "empDOJ")
    var empDOJ: String,

    @ColumnInfo(name = "empSalary")
    var empSalary: String,

    @ColumnInfo(name = "empContactNumber")
    var empContactNumber: String,

    @ColumnInfo(name = "empAddresss")
    var empAddresss: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(empName)
        parcel.writeString(empDepartment)
        parcel.writeString(empDOJ)
        parcel.writeString(empSalary)
        parcel.writeString(empContactNumber)
        parcel.writeString(empAddresss)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeeModel> {
        override fun createFromParcel(parcel: Parcel): EmployeeModel {
            return EmployeeModel(parcel)
        }

        override fun newArray(size: Int): Array<EmployeeModel?> {
            return arrayOfNulls(size)
        }
    }
}

