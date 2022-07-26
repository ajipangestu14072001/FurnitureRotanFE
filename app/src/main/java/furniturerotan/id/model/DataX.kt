package furniturerotan.id.model

data class DataX(
    val idBarang: String,
    val idTransaksi: String,
    val idUser: Int,
    val jumlahBarang: Int,
    val pengiriman: String,
    val status: String,
    val totalHarga: Int,
    val typePembayaran: String
)