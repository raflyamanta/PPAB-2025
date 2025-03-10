fun main(){
    val carName : String? = null
    val carNameLength = carName?.length ?: 5
    print("car length is $carNameLength \n")
}