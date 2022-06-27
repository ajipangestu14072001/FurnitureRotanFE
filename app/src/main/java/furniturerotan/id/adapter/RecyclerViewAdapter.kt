package furniturerotan.id.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import furniturerotan.id.R
import furniturerotan.id.model.Data
import furniturerotan.id.view.DetailActivity

class RecyclerViewAdapter(
    private val courseDataArrayList: List<Data>,
    private val mcontext: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val modal = courseDataArrayList[position]
        holder.title.text = modal.namaBarang
        holder.harga.text = modal.harga.toString()
        holder.lokasi.text = modal.lokasi
        Glide.with(mcontext)
            .asBitmap()
            .load(modal.pathPhoto.replace("\\\\".toRegex(), ""))
            .into(holder.img)
        holder.cardItems.setOnClickListener {
            val intent = Intent(mcontext, DetailActivity::class.java)
            intent.putExtra("id", modal.id)
            mcontext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courseDataArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleProduct)
        val harga: TextView = itemView.findViewById(R.id.hargaProduct)
        val lokasi: TextView = itemView.findViewById(R.id.lokasiProduct)
        val img: ImageView = itemView.findViewById(R.id.imgProduct)
        val cardItems : CardView = itemView.findViewById(R.id.cardItem)

    }
}
