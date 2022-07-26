package furniturerotan.id.services

import furniturerotan.id.model.*
import furniturerotan.id.response.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/signin")
    fun getUser(@Body login: Login?): Call<ResponseLogin?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/signup")
    fun addUser(@Body responseRegister: ResponseRegister?): Call<ResponseRegister?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/logout")
    fun logout(@Header("Authorization") token: String?, @Body logOut: LogOut): Call<MessageResponse?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("barang")
    fun getAllProduct(@Header("Authorization") auth: String?): Call<Barang?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("barang/kategori")
    fun getAllProductByKategori(
        @Header("Authorization") auth: String?,
        @Query("kategori") kategori: String?
    ): Call<Barang?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("barang")
    fun addProduct(@Header("Authorization") auth: String?, @Body data: DataObject?): Call<DataObject?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("barang/{id}")
    fun getBarangByID(
        @Header("Authorization") auth: String?,
        @Path("id") itemId: String?
    ): Call<ResponseDataObject?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("cart/barang")
    fun getChartIdUsers(
        @Header("Authorization") auth: String?,
        @Query("idUsers") idUsers: String?
    ): Call<ResponseChart?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("cart/add")
    fun addChart(@Header("Authorization") auth: String?, @Body chart: Chart?): Call<Chart?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("transactions/buy")
    fun buy(@Header("Authorization") auth: String?, @Body chart: Transaksi?): Call<Transaksi?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("transactions/buy/idUser")
    fun getHistory(
        @Header("Authorization") auth: String?,
        @Query("idUser") idUsers: String?
    ): Call<History?>?
}