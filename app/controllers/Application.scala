package controllers

import play.api._
import play.api.mvc._

import helper.database._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action { 
    Ok(Queries.toJSONObject(Queries.test).toString())
  }

}