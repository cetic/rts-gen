/*
 * Copyright 2106 Cetic ASBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.cetic.rtsgen.generators.binary

import be.cetic.rtsgen.config.{GeneratorFormat, Model}
import be.cetic.rtsgen.generators.Generator
import be.cetic.rtsgen.timeseries.TimeSeries
import be.cetic.rtsgen.timeseries.binary.GreaterThanTimeSeries
import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, RootJsonFormat, _}


/**
  * A generator for [[be.cetic.rtsgen.timeseries.binary.GreaterThanTimeSeries]].
  */
class GreaterThanGenerator( name: Option[String],
                            val a: Either[String, Generator[Any]],
                            val b: Either[String, Generator[Any]],
                            val strict: Option[Boolean]) extends Generator[Any](name, "GT")
{
   override def timeseries(generators: (String) => Generator[Any]) =
   {
      val first = Model.generator(generators)(a).timeseries(generators) match {
         case t: TimeSeries[Double] => t
      }

      val second = Model.generator(generators)(b).timeseries(generators) match {
         case t: TimeSeries[Double] => t
      }

      new GreaterThanTimeSeries(first, second, strict match {
         case None => true
         case Some(x) => x
      })
   }

   override def toString = "GreaterThan(" + name + "," + a + "," + b + ")"

   override def equals(o: Any) = o match {
      case that: GreaterThanGenerator => that.name == this.name &&
         that.a == this.a &&
         that.b == this.b &&
         that.strict == this.strict
      case _ => false
   }
}

object GreaterThanFormat extends RootJsonFormat[GreaterThanGenerator] with DefaultJsonProtocol
{
   def write(obj: GreaterThanGenerator) =
   {
      val a = obj.a match
      {
         case Left(s) => s.toJson
         case Right(g) => GeneratorFormat.write(g)
      }

      val b = obj.b match
      {
         case Left(s) => s.toJson
         case Right(g) => GeneratorFormat.write(g)
      }

      val name = obj.name
      val strict = obj.strict

      var t = Map(
         "a" -> a,
         "b" -> b
      )

      if(name.isDefined) t = t.updated("name", name.toJson)
      if(strict.isDefined) t = t.updated("strict", strict.toJson)

      new JsObject(t)
   }

   def read(value: JsValue) =
   {
      val fields = value.asJsObject.fields

      val name = fields.get("name").map(_.convertTo[String])
      val strict = fields.get("strict").map(_.convertTo[Boolean])

      val a = fields("a") match {
         case JsString(s) => Left(s)
         case g => Right(GeneratorFormat.read(g))
      }

      val b = fields("b") match {
         case JsString(s) => Left(s)
         case g => Right(GeneratorFormat.read(g))
      }

      new GreaterThanGenerator(name, a, b, strict)
   }
}