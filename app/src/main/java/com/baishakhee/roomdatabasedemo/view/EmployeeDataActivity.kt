package com.baishakhee.roomdatabasedemo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.baishakhee.roomdatabasedemo.adapter.EmployeeListAdapter
import com.baishakhee.roomdatabasedemo.databinding.ActivityEmployeedataBinding
import com.baishakhee.roomdatabasedemo.interfaces.OnClickData
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.viewmodel.EmpListViewModel


class EmployeeDataActivity : AppCompatActivity(), OnClickData {
    private lateinit var binding: ActivityEmployeedataBinding
    private lateinit var empListAdapter: EmployeeListAdapter
    private var mainList: List<EmployeeModel>? = null
    private lateinit var empListViewModel: EmpListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeedataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        empListViewModel = ViewModelProvider(this).get(EmpListViewModel::class.java)
        setupUI()
        observeViewModel()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {
        empListAdapter = EmployeeListAdapter(this, emptyList())
        empListAdapter.onItemClickListener = this
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = empListAdapter
        }
    }


    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        empListViewModel.addBtnClicked.observe(this, {
            val intent = Intent(this, AddEmployeeActivity::class.java)
            startActivity(intent)
        })
        empListViewModel.deleteBtnClicked.observe(this, {
         showDeleteConfirmationDialog()

        })

        empListViewModel.allEmployees.observe(this, { employees ->
            employees?.let {
                binding.progressBar.visibility = View.GONE
                empListAdapter.setData(it)
            }
        })
        empListViewModel.searchResults.observe(this, { searchResults ->
            searchResults?.let {
                empListAdapter.setData(it)
            }
        })
        empListViewModel.refreshEmployeeData()

    }

    private fun setupUI() {
        binding.add.setOnClickListener {
            empListViewModel.onAddBtnClick()
        }
        binding.delete.setOnClickListener {
            empListViewModel.onDeleteBtnClick()
        }

binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                empListViewModel.searchEmployees(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the list based on the search query
                empListAdapter.filter.filter(newText)

                return false
            }

        })

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Clear the query when losing focus and show all data
                binding.searchView.setQuery("", false)
                empListAdapter.filter.filter(null)
                empListViewModel.refreshEmployeeData()
                empListAdapter.notifyDataSetChanged()
            }
        }
        binding.searchView.setOnCloseListener {
            // When the SearchView is closed, clear the query text and show all data
            binding.searchView.setQuery("", false)
            empListAdapter.filter.filter(null)
            empListViewModel.refreshEmployeeData()
            empListAdapter.notifyDataSetChanged()
            false
        }
    }

    override fun onItemClick(position: Int, id: Int) {
        showDeleteConfirmationDialog(position, id)

    }

    private fun showDeleteConfirmationDialog(position: Int, employeeId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Employee")
        builder.setMessage("Are you sure you want to delete this employee?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            empListViewModel.deleteEmployeeById(employeeId)
            empListAdapter.removeItem(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            empListAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.show()
    }
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Employee")
        builder.setMessage("Are you sure you want to delete this employee?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            empListViewModel.deleteAllEmployees()
            dialog.dismiss()
            empListViewModel.refreshEmployeeData()
            empListAdapter.notifyDataSetChanged()

        }
        builder.setNegativeButton("No") { dialog, _ ->
            empListAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
        binding.progressBar.visibility = View.GONE

    }

    private fun refreshData() {
        binding.progressBar.visibility = View.VISIBLE
        empListViewModel.refreshEmployeeData()
    }
}