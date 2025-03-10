fun main(){
    // Contoh penggunaan if expression dengan else if
    val score = 75
    val grade = if (score >= 90) {
        "A"
    } else if (score >= 80) {
        "B"
    } else if (score >= 70) {
        "C"
    } else if (score >= 60) {
        "D"
    } else {
        "E"
    }
    println("Nilai anda: $grade")
}