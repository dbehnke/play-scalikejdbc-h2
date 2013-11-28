package controllers

import scala.concurrent._

import play.api._
import play.api.mvc._

import helper.database._
import helper.authentication.basic._

object Remote extends Controller {
  Authenticated.setUserPw("admin","cheesefinger")

  def test = Authenticated {
    Ok(Queries.toJSONObject(Queries.test).toString()).as("application/json")
  }

}
