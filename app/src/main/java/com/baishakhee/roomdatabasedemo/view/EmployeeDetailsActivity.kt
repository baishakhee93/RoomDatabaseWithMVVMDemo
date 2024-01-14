package com.baishakhee.roomdatabasedemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.baishakhee.roomdatabasedemo.R
import com.baishakhee.roomdatabasedemo.databinding.ActivityEmployeeDetailsBinding
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.viewmodel.EmployeeDetailsViewModel

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailsBinding
    private lateinit var viewModel: EmployeeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(EmployeeDetailsViewModel::class.java)

        setupUI()

        observeViewModel()
    }

    private fun setupUI() {
        val intent = getIntent()
        val employeeModel = intent.getParcelableExtra<EmployeeModel>("employeeModel")

// Retrieve data from intent extras
      /*  val empName = intent.getStringExtra("empName")
        val empDepartment = intent.getStringExtra("empDepartment")
        val empDOJ = intent.getStringExtra("empDOJ")
        val empSalary = intent.getStringExtra("empSalary")
        val empContactNumber = intent.getStringExtra("empContactNumber")
        val empAddresss = intent.getStringExtra("empAddresss")*/
// Retrieve more data as needed

// Now, you can use the retrieved data as needed, for example, display it in TextViews or any other UI components.



        binding.tvName.text=employeeModel?.empName.toString()
        binding.tvDepartment.text=employeeModel?.empDepartment.toString()
        binding.tvContactNumber.text=employeeModel?.empContactNumber.toString()
        binding.tvAddresss.text=employeeModel?.empAddresss.toString()
        binding.tvJoiningDate.text=employeeModel?.empDOJ.toString()
        binding.tvSalary.text=employeeModel?.empSalary.toString()
        binding.back.setOnClickListener {
            viewModel.onBackBtnClick()
        }
    }
    private fun observeViewModel() {
        viewModel.backBtnClicked.observe(this, {
          finish()
        })
    }
}