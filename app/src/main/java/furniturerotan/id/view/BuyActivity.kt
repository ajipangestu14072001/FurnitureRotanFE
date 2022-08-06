package furniturerotan.id.view

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import furniturerotan.id.databinding.ActivityBuyBinding
import furniturerotan.id.helper.SharedPrefManager
import furniturerotan.id.model.Transaksi
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BuyActivity : AppCompatActivity() {
    private var TOTAL_AMOUNT = 0
    private val TRANSACTION_ID = "FurnitureRotan-${System.currentTimeMillis().toShort()}"
    private var binding: ActivityBuyBinding? = null
    private val pengiriman = arrayOf("", "MyKurir")
    var sharedPrefManager: SharedPrefManager? = null
    var id: String = ""
    var username: String = ""
    var nama: String = ""
    var email: String = ""
    var telepon: String = ""
    var idBarang: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        setSupportActionBar(binding!!.toolbar)
        sharedPrefManager = SharedPrefManager(this)
        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("BARANG", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        id = sharedPreferences.getString("userId", "").toString()
        username = sharedPreferences.getString("username", "").toString()
        idBarang = sharedPreferences2.getString("idBarang", "").toString()
        nama = sharedPreferences.getString("nama", "").toString()
        email = sharedPreferences.getString("email", "").toString()
        telepon = sharedPreferences.getString("telepon", "").toString()
        val harga = intent.getStringExtra("harga").toString()
        val photo = intent.getStringExtra("photo").toString()
        val namaBarang = intent.getStringExtra("namaBarang").toString()
        Glide.with(applicationContext)
            .asBitmap()
            .load(photo)
            .into(binding!!.imgview)
        binding!!.judul.text = namaBarang
        binding!!.price.text = harga
        val kategoriRotan =
            ArrayAdapter(this@BuyActivity, R.layout.simple_list_item_1, pengiriman)
        binding!!.pengiriman.adapter = kategoriRotan
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-2nfwJhoKrlCwou6q")
            .setContext(applicationContext)
            .setTransactionFinishedCallback {

            }
            .setMerchantBaseUrl("https://furniture-rotan.000webhostapp.com/index.php/")
            .enableLog(true)
            .setColorTheme(
                CustomColorTheme(
                    "#FFE51255",
                    "#B61548",
                    "#FFE51255"
                )
            )
            .setLanguage("en")
            .buildSDK()

        binding!!.promotionButton.setOnClickListener {
            TOTAL_AMOUNT = harga.toInt() * binding!!.jumlah.text.toString().toInt()
            val transactionRequest = TransactionRequest(TRANSACTION_ID, TOTAL_AMOUNT.toDouble())
            val itemDetails1 = ItemDetails("111112234", TOTAL_AMOUNT.toDouble(), 1, "Kursi Rotan")
            val itemDetailsList: ArrayList<ItemDetails> = ArrayList()
            itemDetailsList.add(itemDetails1)
            val barang = Transaksi(
                idBarang,
                id.toInt(),
                binding!!.jumlah.text.toString().toInt(),
                binding!!.pengiriman.selectedItem.toString(),
                "Berhasil",
                TOTAL_AMOUNT,
                "Midtrans"
            )
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)
            apiInterface.buy("Bearer $token", barang)!!.enqueue(object : Callback<Transaksi?> {
                override fun onResponse(call: Call<Transaksi?>, response: Response<Transaksi?>) {
                    if (response.body() != null) {
                        Toast.makeText(
                            this@BuyActivity,
                            "Lakukan Pembayaran",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Transaksi?>, t: Throwable) {
                    Log.i("Ini Log", t.message!!)
                }
            })
            uiKitDetail(transactionRequest)
            transactionRequest.itemDetails = itemDetailsList
            MidtransSDK.getInstance().transactionRequest = transactionRequest;
            MidtransSDK.getInstance().startPaymentUiFlow(this@BuyActivity)

        }
    }

    private fun uiKitDetail(transactionRequest: TransactionRequest) {
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = id
        customerDetails.phone = telepon
        customerDetails.firstName = nama
        customerDetails.lastName = ""
        customerDetails.email = email

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Jalan Andalas Gang Sebelah No. 1"
        shippingAddress.city = "Jakarta"
        shippingAddress.postalCode = "10220"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "Jalan Andalas Gang Sebelah No. 1"
        billingAddress.city = "Jakarta"
        billingAddress.postalCode = "10220"
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}