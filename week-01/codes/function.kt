fun main(){
    val product = setProduct("Laptop", 15_000_000)
    println(product)
}

fun setProduct(name:String, price:Int):String{
    return "Product is $name and price is $price"
}

/*
    output:
    Product is Laptop and price is 15000000
*/