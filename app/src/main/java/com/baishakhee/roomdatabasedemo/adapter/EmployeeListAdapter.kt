package com.baishakhee.roomdatabasedemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baishakhee.roomdatabasedemo.R
import com.baishakhee.roomdatabasedemo.interfaces.OnClickData
import com.baishakhee.roomdatabasedemo.model.EmployeeModel
import com.baishakhee.roomdatabasedemo.view.EmployeeDetailsActivity

class EmployeeListAdapter(contaxt :Context,private var mList: List<EmployeeModel>) : RecyclerView.Adapter<EmployeeListAdapter.EmpViewHolder>(),Filterable {
    private var filteredList: List<EmployeeModel> = mList

    var onItemClickListener: OnClickData? = null

    private var mContext:Context=contaxt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): EmpViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_child, parent, false)

        return EmpViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {

        holder.empName.text=mList[position].empName
        holder.empDepartment.text=mList[position].empDepartment
        holder.delete.setOnClickListener {
            mList[position].id?.let { it1 -> onItemClickListener?.onItemClick(position, it1) }

        }


        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, EmployeeDetailsActivity::class.java)

            // Pass data using putExtra
           /* intent.putExtra("employeeModel", mList[position].empName)
            intent.putExtra("empDepartment", mList[position].empDepartment)
            intent.putExtra("empDOJ", mList[position].empDOJ)
            intent.putExtra("empSalary", mList[position].empSalary)
            intent.putExtra("empContactNumber", mList[position].empContactNumber)
            intent.putExtra("empAddresss", mList[position].empAddresss)*/
            // Add more data as needed
            intent.putExtra("employeeModel", mList[position])
            mContext.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
      return  mList.size
    }

    fun setData(mainList: List<EmployeeModel>) {
        mList = mainList
        filteredList = mainList
        notifyDataSetChanged()
    }
    fun removeItem(position: Int) {
        val mutableList = mList.toMutableList()
        mutableList.removeAt(position)
        mList = mutableList
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredData: MutableList<EmployeeModel> = mutableListOf()

                if (constraint.isNullOrBlank()) {
                    // If the search query is null or blank, show the original list
                    filteredData.addAll(mList)
                } else {
                    // Filter the list based on the search query
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    for (item in mList) {
                        if (item.empName.toLowerCase().contains(filterPattern) ||
                            item.empDepartment.toLowerCase().contains(filterPattern)
                        ) {
                            filteredData.add(item)
                        }
                    }
                }

                results.values = filteredData
                results.count = filteredData.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<EmployeeModel> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    // Holds the views for adding it to image and text
    class EmpViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val empName: TextView = itemView.findViewById(R.id.empName)
        val empDepartment: TextView = itemView.findViewById(R.id.empDepartment)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }

}

