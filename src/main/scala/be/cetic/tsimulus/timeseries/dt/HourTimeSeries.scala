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

package be.cetic.tsimulus.timeseries.dt

import be.cetic.tsimulus.timeseries.TimeSeries
import org.joda.time.LocalDateTime

/**
  * A time series providing the hour of day of the underlying timestamp
  */
class HourTimeSeries(base: TimeSeries[LocalDateTime]) extends DateTimeTimeSeries[Int](base)
{
   def eval(time: LocalDateTime) = time.getHourOfDay
}