package ex2
    
import scala.math.Numeric.Implicits._

object ListExtensions:
    implicit class ListOptionExtensions[T: Numeric](list: List[Option[T]]):
        def average: Option[Double] =
            val validScores = list.flatten
            if (validScores.isEmpty) None
            else Some(validScores.sum.toDouble / validScores.length)