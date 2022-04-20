fun main() {
    val main = Main()
    main.test()
}

// 1. You are given a value that represents length in meters. Implement converter that would convert this value into feet, inches and kilometеrs
// 1a. Modify the code to add the reverse conversion from feet, inches and kilometеrs into meters
// 1b. Modify the code so it could convert values between all supported units
//ft = m * 3.281
//inch = m * 39.3701
//inch = ft * 12
//km = m * 1000

class Main {
    fun test() {
        val lengthInMeters = 12.0
        val lengthInFeet = 0.0
        val lengthInInches = 0.0
        val lengthInKm = 0.0
        val mFromInches = 0.0
        val inchesFromKm = 0.0

        //?

        println("Original length in meters is $lengthInMeters");
        println("length in feet is  $lengthInFeet");
        println("length in inches is $lengthInInches");
        println("length in kilometers is $lengthInKm");
        println("length in inches from m is $mFromInches");
        println("length in inches from km is $inchesFromKm");
    }
}