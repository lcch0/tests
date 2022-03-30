package another.solution

import java.lang.reflect.Type

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
        val lengthInFeet = lengthInMeters.create(Foot(0.0))
        val lengthInInches = lengthInFeet.create(Inch(0.0))
        val lengthInKm = lengthInInches.create(Kilometer(0.0))
        val mFromInches = lengthInInches.create(Meter(0.0))
        val inchesFromKm = lengthInKm.create(Inch(0.0))

        //?

        println("Original length in meters is ${lengthInMeters.value}")
        println("length in feet is  ${lengthInFeet.value}")
        println("length in inches is ${lengthInInches.value}")
        println("length in kilometers is ${lengthInKm.value}")
        println("length in meters from inches is ${mFromInches.value}")
        println("length in inches from km is ${inchesFromKm.value}")
    }
}

abstract class Measurement(var value: Double)
{
    protected val multipliers = mutableMapOf<Type, Double>()
    fun create(target: Measurement): Measurement
    {
        val multiplier = multipliers[target::class.java]?:1.0
        target.value = value*multiplier
        return target
    }
}

class Meter(value: Double): Measurement(value)
{
    init {
        multipliers[Foot::class.java] = 3.281
        multipliers[Inch::class.java] = 39.3701
        multipliers[Kilometer::class.java] = 1/1000.0
    }
}

class Foot(value: Double): Measurement(value)
{
    init {
        multipliers[Meter::class.java] = 1/3.281
        multipliers[Inch::class.java] = 12.0
        multipliers[Kilometer::class.java] = 1/3281.0
    }
}

class Inch(value: Double): Measurement(value)
{
    init {
        multipliers[Foot::class.java] = 1/12.0
        multipliers[Meter::class.java] = 1/39.3701
        multipliers[Kilometer::class.java] = 1/39370.1
    }
}

class Kilometer(value: Double): Measurement(value)
{
    init {
        multipliers[Foot::class.java] = 3281.0
        multipliers[Meter::class.java] = 1000.0
        multipliers[Inch::class.java] = 39370.1
    }
}