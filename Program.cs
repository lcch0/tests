//you have a value that represents a length of, let's say, 12 meters
//implement a class to convert it to feet and vice versa
//the user of your class should be able to pass any length value in meters or feet and get appropriate result in feet or meters
//m = ft / 3.281
//ft = m * 3.281
namespace CsharpTest
{
    internal class Program
    {
        static void Main(string[] args)
        {
            double lengthInMeters = 10.0;//meters
            var meter = new Meter(){
                Value = lengthInMeters
            };

            var foot = meter.Create<Foot>();
            var backMeter = foot.Create<Meter>();
            
            Console.WriteLine($"Converted length is {foot.Value}");
            Console.WriteLine($"Converted back length is  {backMeter.Value}");

            lengthInMeters = 10.0;//miles
            var mile = new Mile(){
                Value = lengthInMeters
            };

            foot = mile.Create<Foot>();
            backMeter = mile.Create<Meter>();
            
            Console.WriteLine($"Converted length is {foot.Value}");
            Console.WriteLine($"Converted back length is  {backMeter.Value}");
        }
    }

    abstract class Measurement
    {
        public double Value {get;set;}
        protected Dictionary<Type, double> multiplierDict = new Dictionary<Type, double>();
        public Measurement Create<T>()  where T : Measurement, new()
        {
            var target = new T();
            var type = target.GetType();
            var multiplier = multiplierDict.ContainsKey(target.GetType())?multiplierDict[type]:1d;
            target.Value = Value*multiplier;
            return target;
        }
    }

    class Meter : Measurement
    {
        public Meter()
        {
            multiplierDict.Add(typeof(Foot), 3.281);
            multiplierDict.Add(typeof(Mile), 1/1609.34);
        }    
    }

    class Foot: Measurement
    {    
        public Foot()
        {
            multiplierDict.Add(typeof(Meter), 1/3.281);
            multiplierDict.Add(typeof(Mile), 1/5280d);
        }    
    }

    class Mile: Measurement
    {    
        public Mile()
        {
            multiplierDict.Add(typeof(Foot), 5280d);
            multiplierDict.Add(typeof(Meter), 1609.34);
        }   
    }
}