package another.solution2

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
        val lengthInMeters = Length(12.0, Unit.m) //meters

        val lengthInFeet = lengthInMeters.convertTo(Unit.ft)
        val lengthInInches = lengthInFeet.convertTo(Unit.inch)
        val lengthInKm = lengthInInches.convertTo(Unit.km)
        val mFromInches = lengthInInches.convertTo(Unit.m)
        val inchesFromKm = lengthInKm.convertTo(Unit.inch)

        //?

        println("Original length in meters is ${lengthInMeters.value}")
        println("length in feet is  ${lengthInFeet.value}")
        println("length in inches is ${lengthInInches.value}")
        println("length in kilometers is ${lengthInKm.value}")
        println("length in meters from inches is ${mFromInches.value}")
        println("length in inches from km is ${inchesFromKm.value}")
    }
}

enum class Unit(val multiplier: Double)
{
    mm(1.0),
    m(1000.0),
    ft(328.1),
    inch(25.4),
    km(100000.0)
}
class Length(var value: Double, val unit: Unit)
{
    fun convertTo(target: Unit): Length
    {
        return Length(this.unit.multiplier / target.multiplier * value, target)
    }
}