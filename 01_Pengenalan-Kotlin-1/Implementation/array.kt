fun main() {
    val array = arrayOf(1,3,5,7)
    val mixArray = arrayOf(1,3,5,7, "Informatika", true)
    val intArray = intArrayOf(1,3,5,7)
    val intSpecificArray : IntArray = intArrayOf(1,3,5,7)
    val intCastedArray = arrayOf<Int>(1,3,5,7) // Wrapped Integer Array - not primitive - slower
    val intCastedArray2 = arrayOf(1,3,5,7).toIntArray()

    // print data type of array
    println(array::class.simpleName)
    println(mixArray::class.simpleName)
    println(intArray::class.simpleName)
    println(intSpecificArray::class.simpleName)
    println(intCastedArray::class.simpleName)
    println(intCastedArray2::class.simpleName)
}