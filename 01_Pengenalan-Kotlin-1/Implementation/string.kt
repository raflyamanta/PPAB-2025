fun main() {
    val kalimat : String = "Ini adalah contoh kalimat."
    val statement : String = "Nisa \"cantik\" hari ini!"

    println("$kalimat")
    println("$statement")

    val rawString = """
        Ini adalah contoh raw string dalam Kotlin.
        Dalam raw string, Anda dapat menuliskan teks sebagaimana adanya,
        tanpa perlu melakukan escape terhadap karakter khusus seperti \n (baris baru) atau \t (tab).
        Raw string sangat berguna saat Anda perlu menuliskan teks panjang atau teks yang mengandung banyak karakter escape.
    """
println(rawString)
}