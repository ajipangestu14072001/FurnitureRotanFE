package furniturerotan.id.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import furniturerotan.id.R
import furniturerotan.id.view.KursiActivity
import furniturerotan.id.view.LainActivity
import furniturerotan.id.view.LemariActivity
import furniturerotan.id.view.MejaActivity

class AdapterHome(var context: Context) : RecyclerView.Adapter<AdapterHome.MyAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyAdapter(view)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        if (position == 0) {
            holder.image.setImageResource(R.drawable.toped)
            holder.image1.setImageResource(R.drawable.ic_baseline_data_object_24)
            holder.back.setBackgroundColor(Color.parseColor("#E6E53935"))
            holder.text.text = "Kursi"
            holder.back.setOnClickListener {
                val intent = Intent(context, KursiActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        if (position == 1) {
            holder.image.setImageResource(R.drawable.toped)
            holder.image1.setImageResource(R.drawable.ic_baseline_data_object_24)
            holder.back.setBackgroundColor(Color.parseColor("#F236883A"))
            holder.text.text = "Meja"
            holder.back.setOnClickListener {
                val intent = Intent(context, MejaActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        if (position == 2) {
            holder.image.setImageResource(R.drawable.toped)
            holder.image1.setImageResource(R.drawable.ic_baseline_data_object_24)
            holder.back.setBackgroundColor(Color.parseColor("#F2AF4576"))
            holder.text.text = "Lemari"
            holder.back.setOnClickListener {
                val intent = Intent(context, LemariActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        if (position == 3) {
            holder.image.setImageResource(R.drawable.toped)
            holder.image1.setImageResource(R.drawable.ic_baseline_data_object_24)
            holder.back.setBackgroundColor(Color.parseColor("#F2EEAA45"))
            holder.text.text = "Lain-lain"
            holder.back.setOnClickListener {
                val intent = Intent(context, LainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    inner class MyAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var image1: ImageView
        var text: TextView
        var back: RelativeLayout

        init {
            image = itemView.findViewById(R.id.image)
            image1 = itemView.findViewById(R.id.image1)
            text = itemView.findViewById(R.id.text)
            back = itemView.findViewById(R.id.back)
        }
    }
}