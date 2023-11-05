import org.scalajs.linker.interface.ModuleInitializer
import org.scalajs.linker.interface.ModuleSplitStyle
import org.scalajs.linker.interface.unstable.ModuleInitializerImpl

val scala3Version = "3.3.1"
val AIRFRAME_VERSION = "23.11.1"

// Common build settings
val buildSettings = Seq(
  organization := "io.github.windymelt",
  scalaVersion := "3.3.1"
  // Add your own settings here
)

// RPC API definition. This project should contain only RPC interfaces
lazy val api =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .in(file("api"))
    .settings(
      buildSettings,
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http" % AIRFRAME_VERSION
      )
    )

// RPC server project (JVM)
lazy val server =
  project
    .in(file("server"))
    .settings(
      buildSettings,
      libraryDependencies ++= Seq(
        // Add Netty backend
        "org.wvlet.airframe" %% "airframe-http-netty" % AIRFRAME_VERSION
      )
    )
    .dependsOn(api.jvm)

// RPC client project (JVM and Scala.js)
lazy val client =
  crossProject(JSPlatform, JVMPlatform)
    // CAVEAT: By default, crossProject selects CrossType.Full; you should create client/{js,jvm,shared} directory
    .in(file("client"))
    .enablePlugins(AirframeHttpPlugin)
    .jsSettings(
      scalaJSUseMainModuleInitializer := true,
      scalaJSLinkerConfig ~= {
        _.withModuleKind(ModuleKind.ESModule)
          .withModuleSplitStyle(
            ModuleSplitStyle.SmallModulesFor(
              List("io.github.windymelt.airframeexercise.client")
            )
          )
      }
    )
    .settings(
      buildSettings,
      airframeHttpClients := Seq(
        "io.github.windymelt.airframeexercise.client.api.v1:rpc"
      )
    )
    .dependsOn(api)
