package helper.database

import scalikejdbc._, SQLInterpolation._
import scala.util.parsing.json._

object Queries {
  implicit val session = AutoSession
  def toJSONObject(queryListMap: List[Map[String, Any]]) : JSONObject = {
    val x = {
            var xx = List[JSONObject]()
            queryListMap.foreach(q => xx = xx :+ new JSONObject(q))
            xx
        }
    new JSONObject(Map[String,Any](("rows", new JSONArray(x))))
  }

  def test : List[Map[String, Any]] = { 
    NamedDB('default) readOnly { implicit session => {
      sql"SELECT TOPIC, ID FROM INFORMATION_SCHEMA.HELP".map(_.toMap).list.apply()        
    } }
  }
  
  //def testToJSONObject : JSONObject = {
  //  
  //}
}