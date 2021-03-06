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

package be.cetic.tsimulus.timeseries.composite

import be.cetic.tsimulus.timeseries.TimeSeries
import org.joda.time.LocalDateTime

/**
  * A time series in which each value is defined as the function of the corresponding value
  * of an other time series.
  *
  * @param generator the underlying generator
  * @param f the function to apply to defined values
  */
case class FunctionTimeSeries[T](generator: TimeSeries[T], f: (LocalDateTime, T) => Option[T]) extends TimeSeries[T]
{
  override def compute(times: Stream[LocalDateTime]) =
       generator.compute(times)
                .map {case (t,v) => (t, v.flatMap(f(t, _)))}

   override def compute(time: LocalDateTime): Option[T] = generator.compute(time).flatMap(x => f(time, x))
}
