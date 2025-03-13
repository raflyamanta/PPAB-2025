fun main(){
    greet("Budi") // Hanya memberikan nilai untuk parameter `name`
}

fun greet(name: String, age: Int = 18) {
    println("Halo, $name! Kamu berusia $age tahun.")
}
  
  