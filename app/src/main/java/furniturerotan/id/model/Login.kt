package furniturerotan.id.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("username") val username: String?,

    @SerializedName("password") val password: String?
)
