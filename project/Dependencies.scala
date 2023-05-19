import sbt._

object Dependencies {

  object V {
    val cats          = "2.9.0"
    val catsEffect    = "3.4.9"
    val catsRetry     = "3.1.0"
    val circe         = "0.15.0-M1"
    val ciris         = "2.3.2"
    val comCast       = "3.3.0"
    val derevo        = "0.13.0"
    val javaxCrypto   = "1.0.1"
    val fs2           = "3.6.1"
    val http4s        = "0.23.18"
    val http4sJwtAuth = "1.2.0"
    val log4cats      = "2.5.0"
    val monocle       = "3.2.0"
    val newtype       = "0.4.4"
    val refined       = "0.10.3"
    val redis4cats    = "1.4.1"
    val skunk         = "0.3.1"
    val squants       = "1.8.3"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.13.2"
    val organizeImports  = "0.6.0"
    val semanticDB       = "4.5.8"

    val weaver = "0.8.3"
    val munitVersion = "0.7.29"
    val logback = "1.3.6"
    val munitCatsEffectVersion = "1.0.7"
    val svmSubs = "20.2.0"
    val scalaMock = "5.2.0"
    val scalaTest = "3.2.15"
    val scalaCheck = "1.17.0"
    val scalaTestPlus = "3.2.10.0"
  }

  object Libraries {
    def circe(artifact: String): ModuleID  = "io.circe"   %% s"circe-$artifact"  % V.circe
    def ciris(artifact: String): ModuleID  = "is.cir"     %% artifact            % V.ciris
    def derevo(artifact: String): ModuleID = "tf.tofu"    %% s"derevo-$artifact" % V.derevo
    def http4s(artifact: String): ModuleID = "org.http4s" %% s"http4s-$artifact" % V.http4s excludeAll(
      ExclusionRule(organization = "org.slf4j", name = "slf4j-api"))

    val cats       = "org.typelevel"    %% "cats-core"   % V.cats
    val catsEffect = "org.typelevel"    %% "cats-effect" % V.catsEffect
    val catsRetry  = "com.github.cb372" %% "cats-retry"  % V.catsRetry
    val squants    = "org.typelevel"    %% "squants"     % V.squants
    val fs2        = "co.fs2"           %% "fs2-core"    % V.fs2

    val circeCore    = circe("core")
    val circeGeneric = circe("generic")
    val circeParser  = circe("parser")
    val circeRefined = circe("refined")

    val cirisCore    = ciris("ciris")
    val cirisEnum    = ciris("ciris-enumeratum")
    val cirisRefined = ciris("ciris-refined")

    val comCast = "com.comcast" %% "ip4s-core" % V.comCast

    val derevoCore  = derevo("core")
    val derevoCats  = derevo("cats")
    val derevoCirce = derevo("circe-magnolia")

    val http4sDsl    = http4s("dsl")
    val http4sServer = http4s("ember-server")
    val http4sClient = http4s("ember-client")
    val http4sCirce  = http4s("circe")

    val http4sJwtAuth = "dev.profunktor" %% "http4s-jwt-auth" % V.http4sJwtAuth

    val monocleCore = "dev.optics" %% "monocle-core" % V.monocle

    val refinedCore = "eu.timepit" %% "refined"      % V.refined
    val refinedCats = "eu.timepit" %% "refined-cats" % V.refined
    val log4cats = "org.typelevel" %% "log4cats-slf4j" % V.log4cats
    val newtype  = "io.estatico"   %% "newtype"        % V.newtype

    val javaxCrypto = "javax.xml.crypto" % "jsr105-api" % V.javaxCrypto

    val redis4catsEffects  = "dev.profunktor" %% "redis4cats-effects"  % V.redis4cats
    val redis4catsLog4cats = "dev.profunktor" %% "redis4cats-log4cats" % V.redis4cats

//    val skunkCore  = "org.tpolecat" %% "skunk-core"  % V.skunk
//    val skunkCirce = "org.tpolecat" %% "skunk-circe" % V.skunk

    // Runtime
    val logback = "ch.qos.logback" % "logback-classic" % V.logback

    // Test
    val catsLaws          = "org.typelevel"       %% "cats-laws"          % V.cats
    val log4catsNoOp      = "org.typelevel"       %% "log4cats-noop"      % V.log4cats
    val monocleLaw        = "dev.optics"          %% "monocle-core"        % V.monocle
    val refinedScalacheck = "eu.timepit"          %% "refined-scalacheck" % V.refined
    val weaverCats        = "com.disneystreaming" %% "weaver-cats"        % V.weaver
    val weaverDiscipline  = "com.disneystreaming" %% "weaver-discipline"  % V.weaver
    val weaverScalaCheck  = "com.disneystreaming" %% "weaver-scalacheck"  % V.weaver
    val munit             = "org.scalameta"       %% "munit"              % V.munitVersion
    val munitCats         = "org.typelevel"       %% "munit-cats-effect-3"% V.munitCatsEffectVersion
    val svmSubs           = "org.scalameta"       %% "svm-subs"           % V.svmSubs
    val scalaTest =       "org.scalatest"         %% "scalatest"          % V.scalaTest
    val scalaCheck =      "org.scalacheck"        %% "scalacheck"         % V.scalaCheck
    val mockitoScalaTestPlus =   "org.scalatestplus"     %% "mockito-3-4"    % V.scalaTestPlus
    // Scalafix rules
    val organizeImports = "com.github.liancheng" %% "organize-imports" % V.organizeImports

  }

  object CompilerPlugin {
    val betterMonadicFor = compilerPlugin(
      "com.olegpy" %% "better-monadic-for" % V.betterMonadicFor
    )
    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % V.kindProjector cross CrossVersion.full
    )
    val semanticDB = compilerPlugin(
      "org.scalameta" % "semanticdb-scalac" % V.semanticDB cross CrossVersion.full
    )
  }

}
