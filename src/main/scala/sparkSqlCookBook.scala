import org.apache.spark.{SparkConf, sql}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level
//import com.github.nscala_time.time.Imports._
import com.github.nscala_money.money.Imports._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{current_date,current_timestamp}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.{lit,udf}
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
    //println(DateTime.now() + 2.months)
    val x = 1 to 100
    val y = (1 to 50) toArray // converting to An array
    val z = (1 to 25) toList //Converting to an list
    val ranRange = 0 to Random.nextInt(100)

    println(x)
    println(y)
    println(z)
    println(ranRange)
    spark.close()
  }
}
