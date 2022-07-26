package furniturerotan.id.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import furniturerotan.id.view.DetailHistoryActivity
import furniturerotan.id.R
import furniturerotan.id.model.DataX

class MyListAdapter(val context: Context, val listModelArrayList: List<DataX>, private var onItemClicked: ((datax: DataX) -> Unit)) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.custom_list, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.name.text = listModelArrayList[position].idBarang
        vh.email.text = listModelArrayList[position].pengiriman
        vh.gender.text = listModelArrayList[position].totalHarga.toString()
        vh.status.text = listModelArrayList[position].status
        vh.userId.text = listModelArrayList[position].idUser.toString()
        vh.trigger.setOnClickListener{
            val intent = Intent(context, DetailHistoryActivity::class.java)
            context.startActivity(intent)
            onItemClicked(listModelArrayList[position])
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return listModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listModelArrayList.size
    }
}

private class ViewHolder(view: View?) {
    val name: TextView = view?.findViewById(R.id.name) as TextView
    val email: TextView = view?.findViewById(R.id.email) as TextView
    val gender: TextView = view?.findViewById(R.id.gender) as TextView
    val status: TextView = view?.findViewById(R.id.status) as TextView
    val userId: TextView = view?.findViewById(R.id.userId) as TextView
    val trigger: LinearLayout = view?.findViewById(R.id.linearLayout) as LinearLayout
}