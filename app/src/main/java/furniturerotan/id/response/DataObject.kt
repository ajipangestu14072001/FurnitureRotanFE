package furniturerotan.id.response

import com.google.gson.annotations.SerializedName

data class DataObject(
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("harga")
    val harga: Int,
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