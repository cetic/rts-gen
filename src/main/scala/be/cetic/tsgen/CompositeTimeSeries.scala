package be.cetic.tsgen

import org.joda.time.LocalDateTime

/**
  * This time series generator is a composition of other generators.
  * The values provided by each generator are summed.
  */
class CompositeTimeSeries(val generators: Seq[TimeSeriesGenerator]) extends TimeSeriesGenerator
{
   override def compute(times: Stream[LocalDateTime]): Stream[Double] = generators.map(c => c.compute(times))
                                                                                  .reduce((a,b) => a.zip(b).map(e => e._1 + e._2))
}


object CompositeTimeSeries
{
   def apply(generators: TimeSeriesGenerator*): CompositeTimeSeries = new CompositeTimeSeries(generators)
}