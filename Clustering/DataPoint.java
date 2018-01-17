/**
* CS5900-03 Database
* Dr. Zhang
* Water Quality Project
*
* Author: Steve Jia
* Date: 2016-04-12
* Filename: Point.java
**/
import java.util.*;

public class DataPoint
{
  //declares reference variables, and initializes some of them
   private String date;
   private int siteId;
   public double x = 0.0;
   public double y = 0.0;
   public double z = 0.0;
   public double a = 0.0;
   public double b = 0.0;
   public double c = 0.0;
   public int clusterId = 0;

   /**
   * Constructor: DataPoint()
   *   Creates a new DataPoint object and assigns new values to the
   *    reference variables
   **/
   public DataPoint(String newDate, int newSiteId,
                    double newX, double newY, double newZ,
                    double newA, double newB, double newC)
   {
      date = newDate;
      siteId = newSiteId;
      x = newX;
      y = newY;
      z = newZ;
      a = newA;
      b = newB;
      c = newC;
   }

   /**
   * Method (Static): calcDistance()
   *   Use the euclidean method to calculate the distance between two DataPoint
   *   objects
   **/
   public static double calcDistance(DataPoint p, DataPoint centroid)
   {
      //distance between a data point and a cluster centroid is
      // Sqrt((cX - pX)^2 + (cY - pY)^2 + ... + (cn - pn)^2)
      return Math.sqrt(Math.pow((centroid.x - p.x), 2) +
                       Math.pow((centroid.y - p.y), 2) +
                       Math.pow((centroid.z - p.z), 2) +
                       Math.pow((centroid.a - p.a), 2) +
                       Math.pow((centroid.b - p.b), 2) +
                       Math.pow((centroid.c - p.c), 2));
   }

   /**
   * Method (Static): genRandomPoint()
   *   Use Java's Random class to generate random doubles and create a new
   *   DataPoint object
   **/
   public static DataPoint genRandomPoint(int min, int max)
   {
      Random rand = new Random();
      return (new DataPoint("Centroid", 99, rand.nextDouble(), rand.nextDouble(), rand.nextDouble(),
                            rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
   }

   /**
   * Method: toString()
   *   Prints out the data in the DataPoint object
   **/
   public String toString()
   {
      //return (x + "," + y + "," + z + "," + a + "," + b + "," + c + "," + clusterId);
      return (date + "," + siteId + "," +
              denormTemp() + "," + denormDO() + "," +
              denormPercSat() + "," + denormPH() + "," +
              denormCond() + "," + denormEcoli() + "," + clusterId);
   }

   /**
   * Method: toStringShort()
   *   Prints out the data in the DataPoint object, without the date and siteId
   **/
   public String toStringShort()
   {
      return (denormTemp() + "," + denormDO() + "," +
              denormPercSat() + "," + denormPH() + "," +
              denormCond() + "," + denormEcoli() + "," + clusterId);

   }

   /**
   * Method: denormTemp()
   *   De-normalizes the data value. Min and Max values are imported from
   *   the data prepartion phase
   **/
   private String denormTemp()
   {
      final double max = 27.55;
      final double min = 10.55;
      double denormValue = x*(max-min) + min;
      return(String.valueOf(denormValue));
   }

   private String denormDO()
   {
      final double max = 11.91;
      final double min = 2.51;
      return (String.valueOf(y*(max-min) + min));
   }

   private String denormPercSat()
   {
      final double max = 131.3;
      final double min = 31.2;
      return (String.valueOf(z*(max-min) + min));
   }

   private String denormPH()
   {
      final double max = 8.34;
      final double min = 7.18;
      return (String.valueOf(a*(max-min) + min));
   }

   private String denormCond()
   {
      final double max = 992.0;
      final double min = 601.0;
      return (String.valueOf(b*(max-min) + min));
   }

   private String denormEcoli()
   {
      final double max = 1900.0;
      final double min = 0.0;
      return (String.valueOf(c*(max-min) + min));
   }
}
