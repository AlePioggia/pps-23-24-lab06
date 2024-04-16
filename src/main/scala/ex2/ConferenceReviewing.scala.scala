package ex2
import scala.collection.*
import ex2.ListExtensions.ListOptionExtensions

enum Question:
    case RELEVANCE
    case SIGNIFICANCE
    case CONFIDENCE
    case FINAL

trait ConferenceReviewing:
    @scala.annotation.targetName("loadReview")
    def :+(article: Int, scores: Map[Question, Int]): Unit
    def orderedScores(article: Int, question: Question): List[Int]
    def averageFinalScore(article: Int): Double
    def acceptedArticles(): Set[Int]
    def sortedAcceptedArticles(): List[(Int, Double)]
    def averageWeightedFinalScoreMap(): Map[Int, Double]

object ConferenceReviewing:
    def apply(): ConferenceReviewing = new ConferenceReviewingImpl()
    private case class ConferenceReviewingImpl() extends ConferenceReviewing:
        import ImplementationHelpers.*

        private var revs: List[(Int, Map[Question, Int])] = List()

        @scala.annotation.targetName("loadReview")
        override def :+(a: Int, s: Map[Question, Int]): Unit = revs = revs :+ (a, s)
        override def orderedScores(a: Int, q: Question): List[Int] = revs.collect({case p if p._1 == a => p._2.get(q)}).flatten.sorted
        override def sortedAcceptedArticles(): List[(Int, Double)] = acceptedArticles().map((e) => (e, averageFinalScore(e))).toList.sortBy(_._2)
        override def averageFinalScore(a: Int): Double = revs.collect({case p if p._1 == a => p._2.get(Question.FINAL)}).average.get
        override def acceptedArticles(): Set[Int] = revs.map(_._1).collect({case x if accepted(x) => x}).distinct.toSet
        override def averageWeightedFinalScoreMap(): Map[Int, Double] = revs.map({ case (a, _) => a -> averageWeightedFinalScore(revs, a)}).toMap

        private object ImplementationHelpers:
            import ListExtensions.*

            def accepted(a: Int): Boolean = revs.collect({
                case (x, y) if x == a =>
                    y.collect({
                case (q, v) if q == Question.RELEVANCE && v >= 8 => v
            })}).flatten.nonEmpty
            def averageWeightedFinalScore(revs: List[(Int, Map[Question, Int])], a: Int): Double = 
                revs.collect({case p if p._1 == a => p._2.get(Question.FINAL).get * p._2.get(Question.CONFIDENCE).get / 10.0}).map((e) => Option(e)).average.get