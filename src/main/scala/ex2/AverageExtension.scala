package ex2
    
implicit class ListExtensions(list: List[Option[Int]]):
  def average: Option[Double] = 
    val validScores = list.flatten
    if (validScores.isEmpty) None
    else Some(validScores.sum.toDouble / validScores.length)

object ListExtensions:
  implicit class ListOptionExtensions(list: List[Option[Int]]) {
    def average: Option[Double] = 
      val validScores = list.flatten
      if (validScores.isEmpty) None
      else Some(validScores.sum.toDouble / validScores.length)
  }