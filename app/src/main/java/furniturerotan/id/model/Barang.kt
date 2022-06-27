package furniturerotan.id.model

import com.google.gson.annotations.SerializedName

data class Barang(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)