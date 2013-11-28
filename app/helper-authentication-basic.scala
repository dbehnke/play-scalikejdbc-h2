package helper.authentication.basic

import scala.concurrent._

import play.api._
import play.api.mvc._

import helper.database._

class AuthenticatedRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

object Authenticated extends ActionBuilder[AuthenticatedRequest] {
  var auth_user = "admin"
  var auth_pw = "temp"

  def setUserPw(user: String, password: String) {
    auth_user = user
    auth_pw = password
  }

  def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[SimpleResult]) = {
    //Logger.debug("Authentication")
    if (request.headers.get("Authorization") == None) {
      //Logger.debug("Unauthorized: No Authorization Header")
      Future.successful(Results.Unauthorized.withHeaders("WWW-Authenticate" -> "Basic realm=\"Secured\""))
    } else {
      val auth = request.headers.get("Authorization")
      auth match {
        case Some(s) => {
          Logger.debug("Authorization Header="+s)
          val sArray = s.split(" ")
          if (sArray.length != 2) {
            //Logger.debug("Authorization Header split not length 2")
            Future.successful(Results.Unauthorized("Unauthorized\n"))
          }
          val authDecoded = new String(org.apache.commons.codec.binary.Base64.decodeBase64(sArray(1).getBytes))
          Logger.debug("authDecoded="+authDecoded)
          val tArray = authDecoded.split(":")
          Logger.debug("length of tArray = "+ sArray.length)
          if(tArray.length != 2) {
            //Logger.debug("authDecoded split not length 2")
            Future.successful(Results.Unauthorized("Unauthorized\n"))  
          }
          val username = tArray(0)
          val password = tArray(1)
          //Logger.debug("user="+username+" pw="+password)
          if (username == auth_user && password == auth_pw) {
            block(new AuthenticatedRequest("admin", request))
          } else {
            Future.successful(Results.Unauthorized("Unauthorized\n"))
          } 
        }
        case None => {
          Future.successful(Results.Unauthorized("Unauthorized\n"))
        }
      }
    } 
  }
}