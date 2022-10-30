import dmitry.molchanov.commonui.resolvers.StringResolver

object StringRes : StringResolver {
    val appTitle = "Snake"
    override val score: String = "Score"
    override val record: String = "Record"
    override val newRecord: String = "New record"
    override val gameOver: String = "Game over"
}
