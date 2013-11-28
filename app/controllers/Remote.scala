package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import scala.concurrent._

import play.api._
import play.api.mvc._

import helper.database._
import helper.authentication.basic._

object Remote extends Controller {
  val auth_user = "admin"
  val auth_pw = "cheesefinger"

  AuthenticatedAsync.setUserPw("admin","cheesefinger")

  def test = AuthenticatedAsync.Authenticated { user => implicit request => { 
      val futureString = scala.concurrent.Future {
            Queries.toJSONObject(Queries.test).toString()
      }
      val timeoutFuture = play.api.libs.concurrent.Promise.timeout(throw new TimeoutException(), 60.second)
      Future.firstCompletedOf(Seq(futureString, timeoutFuture)).map {
        case s : String => Ok(s).as("application/json")
      } recover {
        case _: TimeoutException => InternalServerError("Timeout")
      }  }
  }
}
