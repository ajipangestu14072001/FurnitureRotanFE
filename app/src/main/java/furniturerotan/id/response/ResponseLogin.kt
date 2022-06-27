package furniturerotan.id.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @SerializedName("id") val id: String?,

    @SerializedName("username") val username: String?,

    @SerializedName("email") val email: String?,

    @SerializedName("roles") val roles: List<String>?,

    @SerializedName("avatar") val avatar: String?,

    @SerializedName("token") val token: String?,

    @SerializedName("nama") val nama: String?,

    @SerializedName("telepon") val telepon: String?
)