package another.solution4

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
        val lengthInMeters = Meter(12.0) //meters

        val lengthInFeet = lengthInMeters.convertTo<Foot>()
        val lengthInInches = lengthInFeet.convertTo<Inch>()
        val lengthInKm = lengthInInches.convertTo<Kilometer>()
        val mFromInches = lengthInInches.convertTo<Meter>()
        val inchesFromKm = lengthInKm.convertTo<Inch>()

        //?

        println("Original length in meters is ${lengthInMeters.value}")
        println("length in feet is  ${lengthInFeet.value}")
        println("length in inches is ${lengthInInches.value}")
        println("length in kilometers is ${lengthInKm.value}")
        println("length in meters from inches is ${mFromInches.value}")
        println("length in inches from km is ${inchesFromKm.value}")
    }
}

abstract class Length(var value: Double)
{
    open val multiplier = 1.0
    inline fun <reified T : Length> convertTo(): T
    {
        val t = T::class.java.constructors[0].newInstance(0.0) as T
        t.value = multiplier/t.multiplier * value
        return t
    }
}

class Meter(value: Double): Length(value)
{
    override val multiplier = 1000.0
}

class Foot(value: Double): Length(value)
{
    override val multiplier = 328.1
}

class Inch(value: Double): Length(value)
{
    override val multiplier = 25.4
}

class Kilometer(value: Double): Length(value)
{
    override val multiplier = 100000.0
}
