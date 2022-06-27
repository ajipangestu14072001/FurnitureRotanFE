package furniturerotan.id.response


data class ResponseChart(
    val `data`: List<Chart>,
    val message: String,
    val status: Int
)