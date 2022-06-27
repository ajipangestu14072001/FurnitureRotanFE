package furniturerotan.id.response

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("username") val username: String?,

    @SerializedName("email") val email: String?,

    @SerializedName("password") val password: String?,

    @SerializedName("nama") val nama: String?,

    @SerializedName("telepon") val telepon: String?
)
