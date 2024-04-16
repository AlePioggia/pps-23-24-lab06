package ex1

// List as a pure interface
enum List[A]:
  case ::(h: A, t: List[A])
  case Nil()
  def ::(h: A): List[A] = List.::(h, this)

  def head: Option[A] = this match
    case h :: t => Some(h)  // pattern for scala.Option
    case _ => None          // pattern for scala.Option

  def tail: Option[List[A]] = this match
    case h :: t => Some(t)
    case _ => None
  def foreach(consumer: A => Unit): Unit = this match
    case h :: t => consumer(h); t.foreach(consumer)
    case _ =>

  def get(pos: Int): Option[A] = this match
    case h :: t if pos == 0 => Some(h)
    case h :: t if pos > 0 => t.get(pos - 1)
    case _ => None

  /**
   * Funzione che, data una lista di elementi calcola qualcosa partendo dal primo elemento, 
   * arrivando alla coda, costruendo un qualcosa, che diventa all'ultima iterazione è il risultato. 
   * Da sinistra verso destra calcolo il risultato. 
   * 
  */
  def foldLeft[B](init: B)(op: (B, A) => B): B = this match
    case h :: t => t.foldLeft(op(init, h))(op)
    case _ => init

  def foldRight[B](init: B)(op: (A, B) => B): B = this match
    case h :: t => op(h, t.foldRight(init)(op))
    case _ => init

  /**
   * Per via dell'immutabilità è necessario partire dal fondo e costruire. E' non tail, e le ricorsioni non tail
   * si costruiscono partendo dall'ultimo fino al prima.
   * 
   * Map, filter, ecc. richiedono la foldRight perché devo ricreare una nuova lista a partire da una in ingresso
  */
  def append(list: List[A]): List[A] =
    foldRight(list)(_ :: _)
    
  def flatMap[B](f: A => List[B]): List[B] =
    foldRight(Nil())(f(_) append _)

  def filter(predicate: A => Boolean): List[A] = flatMap(a => if predicate(a) then a :: Nil() else Nil())

  def map[B](fun: A => B): List[B] = flatMap(a => fun(a) :: Nil())

  def leftSpan(predicate: A => Boolean): List[A] = foldRight(Nil())((a, b) => if predicate(a) then a :: b else Nil())

  def reduce(op: (A, A) => A): A = this match
    case Nil() => throw new IllegalStateException()
    case h :: t => t.foldLeft(h)(op)

  def reverse(): List[A] = foldLeft(Nil(): List[A])((b, a) => a :: b)

  def take(n: Int): List[A] = this match
    case h :: t if n > 0 => h :: t.take(n - 1)
    case _ => Nil()

  // Exercise: implement the following methods
  def zipWithValue[B](v: B): List[(A, B)] = foldRight(Nil())((_, v) :: _)
  def length(): Int = foldLeft(0)((b, _) => b + 1)
  def zipWithIndex: List[(A, Int)] = foldRight(Nil())((a, b) => (a, this.length() - b.length() - 1) :: b)
  def partition(predicate: A => Boolean): (List[A], List[A]) = (this.filter(predicate), this.filter(!predicate(_)))
  def span(predicate: A => Boolean): (List[A], List[A]) = {val l = leftSpan(predicate); (l, takeRight(this.length() - l.length()))}
  def takeRight(n: Int) = this.reverse().take(n).reverse()
  def collect(predicate: PartialFunction[A, A]): List[A] = flatMap((a) => if predicate.isDefinedAt(a) then predicate(a) :: Nil() else Nil())
// Factories
object List:

  def apply[A](elems: A*): List[A] =
    var list: List[A] = Nil()
    for e <- elems.reverse do list = e :: list
    list

  def of[A](elem: A, n: Int): List[A] =
    if n == 0 then Nil() else elem :: of(elem, n - 1)

object Test extends App:

  import List.*
  val reference = List(1, 2, 3, 4)
  println(reference.length())
  println(reference.zipWithValue(10)) // List((1, 10), (2, 10), (3, 10), (4, 10))
  println(reference.zipWithIndex) // List((1, 0), (2, 1), (3, 2), (4, 3))
  println(reference.partition(_ % 2 == 0)) // (List(2, 4), List(1, 3))
  println(reference.get(3))
  println(reference.leftSpan(_ % 2 != 0))
  println(reference.takeRight(3))
  println(reference.span(_ % 2 != 0)) // (List(1), List(2, 3, 4))
  println(reference.span(_ < 3)) // (List(1, 2), List(3, 4))
  println(reference.reduce(_ + _)) // 10
  println(List(10).reduce(_ + _)) // 10
  println(reference.collect { case x if x % 2 == 0 => x + 1 }) // List(3, 5)