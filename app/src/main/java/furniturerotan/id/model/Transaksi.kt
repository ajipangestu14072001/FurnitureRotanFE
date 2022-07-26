package furniturerotan.id.model

data class Transaksi(
    val idBarang: String,
    val idUser: Int,
    val jumlahBarang: Int,
    val pengiriman: String,
    val status: String,
    val totalHarga: Int,
    val typePembayaran: String
)