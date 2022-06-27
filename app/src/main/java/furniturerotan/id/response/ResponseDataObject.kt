package furniturerotan.id.response

import com.google.gson.annotations.SerializedName

data class ResponseDataObject(
    @SerializedName("data")
    val `data`: DataObject,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)