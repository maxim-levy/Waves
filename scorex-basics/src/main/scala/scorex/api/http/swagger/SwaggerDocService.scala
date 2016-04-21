package scorex.api.http.swagger

import akka.actor.ActorSystem
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.{Contact, Info, License}
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}
import spray.json._

import scala.reflect.runtime.universe.Type


class SwaggerDocService(system: ActorSystem, val apiTypes: Seq[Type]) extends SwaggerHttpService with HasActorSystem {
  override implicit val actorSystem: ActorSystem = system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()

//  override val host = "localhost:9085"

  override val info: Info = Info("The Web Interface to the Scorex API",
    "1.2.4",
    "Scorex API",
    "License: Creative Commons CC0",
    Some(Contact("Alex", "https://scorex-dev.groups.io/g/main", "alex.chepurnoy@iohk.io")),
    Some(License("License: Creative Commons CC0", "https://github.com/ScorexProject/Scorex/blob/master/COPYING"))
  )

  override val routes: Route = get {
    path(removeInitialSlashIfNecessary(apiDocsPath)) {
      complete(toJsonString(swagger).parseJson.asJsObject)
    }
  }
}