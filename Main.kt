import java.lang.reflect.Type

fun main() {
    val main = Main()
    main.test()
}

//you have a value that represents a length of, let's say, 12 meters
//implement a class to convert it to feet and vice versa
//the user of your class should be able to pass any length value in meters or feet and get appropriate result in feet or meters
//m = ft / 3.281
//ft = m * 3.281
class Main {
    fun test() {
        val lengthInMeters: Measurement = Meter(12.0)
        val lengthInFeet = lengthInMeters.create { Foot(it) }
        var lengthBackInMeters = lengthInMeters.create { Meter(it) }

        //?

        println("Converted length: ${lengthInFeet.value}")
        println("Original length: ${lengthBackInMeters.value}")

        val lengthInInches = lengthInMeters.create { Inch(it) }
        lengthBackInMeters = lengthInInches.create { Meter(it) }

        println("Converted length: ${lengthInInches.value}")
        println("Original length: ${lengthBackInMeters.value}")
    }
}

abstract class Measurement(var value: Double)
{
    protected val multipliers = mutableMapOf<Type, Double>()
    fun <T: Measurement> create(createFunc: (Double)->T): T
    {
        val obj = createFunc(0.0)
        val multiplier = multipliers[obj::class.java]?:1.0
        return obj.also { it.value = value*multiplier }
    }
}

class Meter(value: Double): Measurement(value)
{
    init {
        multipliers[Foot::class.java] = 3.281
        multipliers[Inch::class.java] = 39.3701
    }
}

class Foot(value: Double): Measurement(value)
{
    init {
        multipliers[Meter::class.java] = 1/3.281
        multipliers[Inch::class.java] = 12.0
    }
}

class Inch(value: Double): Measurement(value)
{
    init {
        multipliers[Foot::class.java] = 1/12.0
        multipliers[Meter::class.java] = 1/39.3701
    }
}