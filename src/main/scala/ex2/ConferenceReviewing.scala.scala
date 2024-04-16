package ex2
import scala.collection.*
import scala.compiletime.ops.boolean

enum Question:
    case RELEVANCE
    case SIGNIFICANCE
    case CONFIDENCE
    case FINAL

trait ConferenceReviewing:
    def loadReview(article: Int, scores: Map[Question, Int]): Unit
    def orderedScores(article: Int, question: Question): List[Int]
    def averageFinalScore(article: Int): Double
    def acceptedArticles(): Set[Int]
    def sortedAcceptedArticles(): List[(Int, Double)]
    def averageWeightedFinalScoreMap(): Map[Int, Double]

object ConferenceReviewing:
    def apply(): ConferenceReviewing = new ConferenceReviewingImpl()
    private case class ConferenceReviewingImpl() extends ConferenceReviewing:
        import ImplementationHelpers.*

        private var reviews: List[(Int, Map[Question, Int])] = List()
        
        override def loadReview(article: Int, scores: Map[Question, Int]): Unit =
            reviews = reviews :+ (article, scores)
        override def orderedScores(article: Int, question: Question): List[Int] = 
            reviews.collect({case p if p._1 == article => p._2.get(question)}).flatten.sorted
        override def sortedAcceptedArticles(): List[(Int, Double)] = 
            acceptedArticles().map((e) => (e, averageFinalScore(e))).toList.sortBy(_._2)
        override def averageFinalScore(article: Int): Double =
            reviews.collect({case p if p._1 == article => p._2.get(Question.FINAL)}).average.get
        override def acceptedArticles(): Set[Int] = 
            reviews.map(_._1).collect({case x if accepted(x) => x}).distinct.toSet
        override def averageWeightedFinalScoreMap(): Map[Int, Double] = ???

        private object ImplementationHelpers:
            def accepted(article: Int): Boolean = reviews.collect {
                case (x, y) if x == article =>
                    y.collect {
                case (question, value) if question == Question.RELEVANCE && value >= 8 => value
            }}.flatten.nonEmpty

    





