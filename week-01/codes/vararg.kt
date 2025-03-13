fun sum(vararg numbers: Int): Int {
    var total = 0
    for (number in numbers) {
      total += number
    }
    return total
  }
  
fun main(){
    val result = sum(1, 2, 3, 4, 5)
    println(result) // Output: 15
}