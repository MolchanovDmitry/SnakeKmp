object Modules {

    const val APP = ":app"

    const val GAME_LOGIC = ":gamelogic"

    object Record {
        private const val RECORD = ":record"
        const val DATA_STORE = "$RECORD:recordDS"
        const val DATA_STORE_IMPL = "$RECORD:recordDSImpl"
    }

}