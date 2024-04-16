package ex2

import org.junit.*
import org.junit.Assert.*
import scala.collection.mutable.HashMap

class ConferenceReviewingTest:
  var cr: ConferenceReviewing = _

  @Before
  def init(): Unit =
    cr = ConferenceReviewing()
    cr.loadReview(1, Map(Question.RELEVANCE -> 8, Question.SIGNIFICANCE -> 8, Question.CONFIDENCE -> 6, Question.FINAL -> 8))
    cr.loadReview(1, Map(Question.RELEVANCE -> 9, Question.SIGNIFICANCE -> 9, Question.CONFIDENCE -> 6, Question.FINAL -> 9))
    cr.loadReview(2, Map(Question.RELEVANCE -> 9, Question.SIGNIFICANCE -> 9, Question.CONFIDENCE -> 10, Question.FINAL -> 9))
    cr.loadReview(2, Map(Question.RELEVANCE -> 4, Question.SIGNIFICANCE -> 6, Question.CONFIDENCE -> 10, Question.FINAL -> 6))
    cr.loadReview(3, Map(Question.RELEVANCE -> 3, Question.SIGNIFICANCE -> 3, Question.CONFIDENCE -> 3, Question.FINAL -> 3))
    cr.loadReview(3, Map(Question.RELEVANCE -> 4, Question.SIGNIFICANCE -> 4, Question.CONFIDENCE -> 4, Question.FINAL -> 4))
    cr.loadReview(4, Map(Question.RELEVANCE -> 6, Question.SIGNIFICANCE -> 6, Question.CONFIDENCE -> 6, Question.FINAL -> 6))
    cr.loadReview(4, Map(Question.RELEVANCE -> 7, Question.SIGNIFICANCE -> 7, Question.CONFIDENCE -> 8, Question.FINAL -> 7))
    cr.loadReview(4, Map(Question.RELEVANCE -> 8, Question.SIGNIFICANCE -> 8, Question.CONFIDENCE -> 7, Question.FINAL -> 8))
    cr.loadReview(5, Map(Question.RELEVANCE -> 6, Question.SIGNIFICANCE -> 6, Question.CONFIDENCE -> 6, Question.FINAL -> 10))
    cr.loadReview(5, Map(Question.RELEVANCE -> 7, Question.SIGNIFICANCE -> 7, Question.CONFIDENCE -> 7, Question.FINAL -> 10))

  @Test
  def testOrderedScores(): Unit =
    assertEquals(cr.orderedScores(2, Question.RELEVANCE), List(4, 9))
    assertEquals(cr.orderedScores(4, Question.CONFIDENCE), List(6, 7, 8))
    assertEquals(cr.orderedScores(5, Question.FINAL), List(10, 10))

  @Test
  def testAverageFinalScore(): Unit =
    assertEquals(cr.averageFinalScore(1), 8.5, 0.01)
    assertEquals(cr.averageFinalScore(2), 7.5, 0.01)
    assertEquals(cr.averageFinalScore(3), 3.5, 0.01)
    assertEquals(cr.averageFinalScore(4), 7.0, 0.01)
    assertEquals(cr.averageFinalScore(5), 10.0, 0.01)

  @Test
  def testAcceptedArticles(): Unit =
    assertEquals(cr.acceptedArticles(), Set(1, 2, 4))

  @Test
  def testSortedAcceptedArticles(): Unit =
    assertEquals(cr.sortedAcceptedArticles(), List((4, 7.0), (2, 7.5), (1, 8.5)))

  @Test
  def optionalTestAverageWeightedFinalScore(): Unit =
    assertEquals(cr.averageWeightedFinalScoreMap()(1), (4.8 + 5.4) / 2, 0.01)
    assertEquals(cr.averageWeightedFinalScoreMap()(2), (9.0 + 6.0) / 2, 0.01)
    assertEquals(cr.averageWeightedFinalScoreMap()(3), (0.9 + 1.6) / 2, 0.01)
    assertEquals(cr.averageWeightedFinalScoreMap()(4), (3.6 + 5.6 + 5.6) / 3, 0.01)
    assertEquals(cr.averageWeightedFinalScoreMap()(5), (6.0 + 7.0) / 2, 0.01)
    assertEquals(cr.averageWeightedFinalScoreMap().size, 5)
