package furniturerotan.id.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import furniturerotan.id.R
import furniturerotan.id.response.Chart
import furniturerotan.id.view.DetailActivity


class CartAdapter(context: Context, courseModelArrayList: ArrayList<Chart>) :
    RecyclerView.Adapter<CartAdapter.Viewholder>() {
    private val context: Context
    private val courseModelArrayList: ArrayList<Chart>

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): Viewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_items, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(@NonNull holder: Viewholder, position: Int) {
        val model: Chart = courseModelArrayList[position]
        holder.title.text = model.namaBarang
        holder.harga.text = "RP" + model.harga
        Glide.with(context)
            .asBitmap()
            .load(model.pathPhoto.replace("\\\\".toRegex(), ""))
            .into(holder.img)
        holder.beli.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", model.idBarang)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courseModelArrayList.size
    }
    inner class Viewholder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView
        val title: TextView
        val harga: TextView
        val beli: Button

        init {
            img = itemView.findViewById(R.id.imgchart)
            title = itemView.findViewById(R.id.tittlechart)
            harga = itemView.findViewById(R.id.hargaChart)
            beli = itemView.findViewById(R.id.buyBtn)
        }
    }
    init {
        this.context = context
        this.courseModelArrayList = courseModelArrayList
    }
}