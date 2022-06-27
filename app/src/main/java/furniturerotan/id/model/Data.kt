package furniturerotan.id.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("kategori")
    val kategori: String,
    @SerializedName("lokasi")
    val lokasi: String,
    @SerializedName("namaBarang")
    val namaBarang: String,
    @SerializedName("pathPhoto")
    val pathPhoto: String,
    @SerializedName("stock")
    val stock: Int
)