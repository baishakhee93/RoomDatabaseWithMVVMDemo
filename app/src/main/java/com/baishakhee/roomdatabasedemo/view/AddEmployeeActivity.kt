package com.baishakhee.roomdatabasedemo.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.baishakhee.roomdatabasedemo.Constant
import com.baishakhee.roomdatabasedemo.R
import com.baishakhee.roomdatabasedemo.databinding.ActivityAddEmployeeBinding
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.viewmodel.EmpViewModel

class AddEmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEmployeeBinding
    private lateinit var employeeModel: EmployeeModel
    private lateinit var empViewModel: EmpViewModel

    var empDpt = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        empViewModel = ViewModelProvider(this).get(EmpViewModel::class.java)

        setUI()
        observeViewModel()

    }

    private fun setUI() {
        binding.etJoiningDate.setOnClickListener {
            Constant.showDatePicker(empViewModel::setFromDateText, this)
        }
        binding.save.setOnClickListener {
            empViewModel.onSaveBtnClick()
        }
        binding.back.setOnClickListener {
            empViewModel.onBackBtnClick()
        }
        val departments = resources.getStringArray(R.array.Departmant)
        if (binding.department != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, departments
            )
            binding.department.adapter = adapter

            binding.department.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@AddEmployeeActivity,
                        "" + departments[position],
                        Toast.LENGTH_SHORT
                    ).show()
                    empDpt = departments[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }


    private fun observeViewModel() {
        empViewModel.fromDateText.observe(this, { fromDate ->
            binding.etJoiningDate.text = Editable.Factory.getInstance().newEditable(fromDate)
        })

        empViewModel.saveBtnClicked.observe(this, {
            empViewModel.insertData(
                this,
                binding.etName.text.toString(),
                empDpt,
                binding.etJoiningDate.text.toString(),
                binding.etSalary.text.toString(),
                binding.etContactNumber.text.toString(),
                binding.etAddresss.text.toString()
            )
            Toast.makeText(this@AddEmployeeActivity, "Inserted", Toast.LENGTH_SHORT).show()
            finish()
        })

        empViewModel.backBtnClicked.observe(this,{
            finish()
        })
    }

}