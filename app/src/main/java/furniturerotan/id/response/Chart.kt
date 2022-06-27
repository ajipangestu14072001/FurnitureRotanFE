package furniturerotan.id.response

import com.google.gson.annotations.SerializedName

data class Chart(
    @SerializedName("idBarang")
    val idBarang: String,
    @SerializedName("idUsers")
    val idUsers: String,
    @SerializedName("namaBarang")
    val namaBarang: String,
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("pathPhoto")
    val pathPhoto: String,
    @SerializedName("kategori")
    val kategori: String,
    @SerializedName("lokasi")
    val lokasi: String,

)