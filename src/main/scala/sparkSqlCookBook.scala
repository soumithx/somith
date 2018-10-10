import org.apache.spark.{SparkConf, sql}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.github.nscala_time.time.Imports._

import scala.collection.mutable.ArrayBuffer._
import util.control.Breaks._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{current_date, current_timestamp}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.{lit, udf}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
object  sparkSqlCookBook {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
    val spark = SparkSession.builder().appName("soumith").config(conf).getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)

    val rand = udf({() => Random.nextInt(1000)})
    val randChar = udf({() => Random.nextString(10)})

    println("____________Spark Configuration is setuped Successfully ______________")
    val r = new Random()
    case class WikipediaArticle(title:String,text:String){
      def MentionedLanguage(lang:String):Boolean = text.split(" ").contains(lang)
    }
    val wikiRdd = sc.parallelize(Seq(WikipediaArticle("a1","Scala cookBook").MentionedLanguage("Scal"),WikipediaArticle("a2","python cookbook").MentionedLanguage("python"),WikipediaArticle("R","R data Analytics").MentionedLanguage("R")))
    wikiRdd.foreach(println)
    val df = spark.createDataFrame(Seq(("a1","Scala CookBook","100"),("a2","Python CookBook","500"),("a3","R CookBook","600"),("a4","Spark CookBook","900"))).toDF("Book Id","Book Name","Price Value")
    df.withColumn("Sale Code",lit(randChar())).withColumn("market",lit(rand())).withColumn("Stocks",lit(r.nextInt(200))).withColumn("Date",lit(current_date())).withColumn("time",lit(current_timestamp())).show(false)
    println("______________  Showing Statistics ________________-")
    df.describe("Price Value").show()
    println(DateTime.now() + 2.months)


    println("___________ Control Structures __________________")
       ///Zip With Index
    val a = Array("Soumith","sai","Challa")
    for((e,count) <- a.zipWithIndex){
      println(s"$count is $e" )
    }

    var numPs = 0
    val passage = "peter parker is powerfull programmmer"
    for (i <- 0 until  passage.length){
      breakable{
       if(passage.charAt(i) != 'p') { break } else {numPs += 1}
    }}
    println(s"""there are $numPs in the string""")
    println("_________ Guards _________________")
    for(i <- 0 to 10 if i%2 == 0 )println(i)    //Guards --> if condition in for loop
    val Names = Array("Soumith","Sai","Challa")
    val CapNames = for (i <-  Names) yield i.toUpperCase()   // writing one collection (array) to other collection (Vector)
    CapNames.foreach(println)
    var ABuff = ArrayBuffer[String]()
    ABuff += "John"
    ABuff += "Nash"
    ABuff += "Park"
    println(ABuff)
    val i = 5
    i match {
      case 1 => println("Jan")
      case 2 => println("Feb")
      case 3 => println("Mar")
      case 4 => println("Apr")
      case 5 => println("May")
      case 6 => println("Jun")
      case 7 => println("Jul")
      case 8 => println("Aug")
      case 9 => println("Sep")
      case 10 => println("Oct")
      case 11 => println("Nov")
      case 12 => println("Dec")
    }


    println("_______________Mulitple Matching Conditions _______")
    println("\n")
    val q = 5
    q match{ case 1| 3 | 5| 7|9| 11 => println("Odd") case 2 | 4 | 6| 8| 10 => println("Even")}
    val p = new Person("dd","dd")
     p.firstName = "Soumith"
     p.SecondName = "Binnu"
      println(p)

    spark.close()
  }
}
