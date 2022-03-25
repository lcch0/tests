//you have a value that represents a length of, let's say, 12 meters
//implement a class/classes to convert it to feet, inches, kilometres and vice versa. 
//the user of your class/classes should be able to convert length in any units to a value of any units
//ft = m * 3.281
//inch = m * 39.3701
//inch = ft * 12
//km = m * 1000
namespace CsharpTest
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var lengthInMeters = new Meter(){Value = 12.0};//meters
            var lengthInFeet = lengthInMeters.Create<Foot>();
            var lengthInInches = lengthInFeet.Create<Inch>();
            var lengthInKm = lengthInInches.Create<Kilometer>();
            var mFromInches = lengthInInches.Create<Meter>();
            var inchesFromKm = lengthInKm.Create<Inch>();

		    //?
            
            Console.WriteLine($"Original length in meters is {lengthInMeters.Value}");
            Console.WriteLine($"length in feet is  {lengthInFeet.Value}");
            Console.WriteLine($"length in inches is {lengthInInches.Value}");
            Console.WriteLine($"length in kilometers is  {lengthInKm.Value}");
            Console.WriteLine($"length in meters from inches is {mFromInches.Value}");
            Console.WriteLine($"length in inches from km is  {inchesFromKm.Value}");
        }
    }

    abstract class Measurement
    {
        public double Value {get; set;}
        protected Dictionary<Type, double> multiplierDict = new Dictionary<Type, double>();
        public Measurement Create<T>()  where T : Measurement, new()
        {
            var type = typeof(T);
            var multiplier = multiplierDict.ContainsKey(type)?multiplierDict[type]:1d;
            return new T(){Value = Value*multiplier};
        }
    }

    class Meter : Measurement
    {
        public Meter()
        {
            multiplierDict.Add(typeof(Foot), 3.281);
            multiplierDict.Add(typeof(Inch), 39.3701);
            multiplierDict.Add(typeof(Kilometer), 1/1000d);
        }    
    }

    class Foot: Measurement
    {    
        public Foot()
        {
            multiplierDict.Add(typeof(Meter), 1/3.281);
            multiplierDict.Add(typeof(Inch), 12d);
            multiplierDict.Add(typeof(Kilometer), 1/3281d);
        }    
    }

    class Inch: Measurement
    {    
        public Inch()
        {
            multiplierDict.Add(typeof(Foot), 1/12d);
            multiplierDict.Add(typeof(Meter), 1/39.3701);
            multiplierDict.Add(typeof(Kilometer), 1/39370.1);
        }   
    }

    class Kilometer: Measurement
    {    
        public Kilometer()
        {
            multiplierDict.Add(typeof(Meter), 1000d);
            multiplierDict.Add(typeof(Inch), 39370.1);
            multiplierDict.Add(typeof(Foot), 3281d);
        }    
    }
}
