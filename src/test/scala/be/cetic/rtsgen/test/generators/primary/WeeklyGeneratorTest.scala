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

package be.cetic.rtsgen.test.generators.primary

import be.cetic.rtsgen.config.GeneratorFormat
import org.scalatest.{FlatSpec, Matchers}
import spray.json._
import be.cetic.rtsgen.generators.primary.WeeklyGenerator

class WeeklyGeneratorTest extends FlatSpec with Matchers
{
   val source =
      """
        |{
        |   "name": "weekly-generator",
        |   "type": "weekly",
        |   "points": {"monday": 8.7, "friday": -3.6, "sunday" : 10.9}
        |}
      """.stripMargin

   "A weekly generator" should "be correctly read from a json document" in {
      val generator = WeeklyGenerator(source.parseJson)

      generator.name shouldBe Some("weekly-generator")
      generator.`type` shouldBe "weekly"
      generator.points shouldBe Map(
         "monday" -> 8.7,
         "friday" -> -3.6,
         "sunday" -> 10.9)
   }

   it should "be correctly extracted from the global extractor" in {
      noException should be thrownBy GeneratorFormat.read(source.parseJson)
   }

   it should "be correctly exported to a json document" in {
      val generator = new WeeklyGenerator(Some("weekly-generator"), Map(
         "monday" -> 8.7,
         "friday" -> -3.6,
         "sunday" -> 10.9))

      generator shouldBe WeeklyGenerator(generator.toJson)
   }

}
