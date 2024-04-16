package ex2
import scala.collection.*

enum Question:
    case RELEVANCE
    case SIGNIFICANCE
    case CONFIDENCE
    case FINAL

trait ConferenceReviewing:
    def loadReview(article: Int, scores: Map[Question, Int]): Unit
    def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int): Unit 
    def orderedScores(article: Int, question: Question): List[Int]
    def averageFinalScore(article: Int): Double
    def acceptedArticles(): Set[Int]
    def sortedAcceptedArticles(): List[(Int, Double)]
    def averageWeightedFinalScoreMap(): Map[Int, Double]

object ConferenceReviewing:
    def apply(): ConferenceReviewing = new ConferenceReviewingImpl()
    private case class ConferenceReviewingImpl() extends ConferenceReviewing:
        private val reviews: List[(Int, Map[Question, Int])] = List()

        override def loadReview(article: Int, scores: Map[Question, Int]): Unit = ??? 
        override def orderedScores(article: Int, question: Question): List[Int] = ???
        override def sortedAcceptedArticles(): List[(Int, Double)] = ???
        override def averageFinalScore(article: Int): Double = ???
        override def acceptedArticles(): Set[Int] = ???
        override def averageWeightedFinalScoreMap(): Map[Int, Double] = ???
        override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int): Unit = ???

    





