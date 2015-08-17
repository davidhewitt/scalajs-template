package simple

case class FileData(name: String, size: Long)
case class TransactionData(
  id: Option[Long],
  accountId: Int,
  date: Long,
  description: String,
  amount: Float,
  balance: Float,
  var tag: String
)

trait Api{
  def title(): String
}
