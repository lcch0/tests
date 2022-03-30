package another.solution1

fun main() {
    val main = Main()
    main.test()
}

//you have a value that represents a length of, let's say, 12 meters
//implement a class/classes to convert it to feet, inches, kilometres and vice versa.
//the user of your class/classes should be able to convert length in any units to a value of any units
//ft = m * 3.281
//inch = m * 39.3701
//inch = ft * 12
//km = m * 1000
class Main {
    fun test() {
        val lengthInMeters = 12.0 //meters
        val converter = Converter()

        val lengthInFeet = converter.convert(lengthInMeters, Unit.m, Unit.ft)
        val lengthInInches = converter.convert(lengthInFeet, Unit.ft, Unit.inch)
        val lengthInKm = converter.convert(lengthInInches, Unit.inch, Unit.km)
        val mFromInches = converter.convert(lengthInInches, Unit.inch, Unit.m)
        val inchesFromKm = converter.convert(lengthInKm, Unit.km, Unit.inch)

        //?

        println("Original length in meters is $lengthInMeters")
        println("length in feet is  $lengthInFeet")
        println("length in inches is $lengthInInches")
        println("length in kilometers is $lengthInKm")
        println("length in meters from inches is $mFromInches")
        println("length in inches from km is $inchesFromKm")
    }
}

enum class Unit{m, ft, inch, km, yard}
class Converter
{
    private val multipliers = mapOf(
        Unit.m to 1.0, Unit.ft to 3.281, Unit.inch to 39.3701, Unit.km to 1/1000.0, Unit.yard to 0.9144
    )

    fun convert(value: Double, source: Unit, target: Unit): Double
    {
        val convToBaseMeter = value/(multipliers[source]?:1.0)
        return convToBaseMeter*(multipliers[target]?:1.0)
    }
}