object Modules {

    object Shared {
        private const val SHARED = ":shared"
        const val GAME_LOGIC = "$SHARED:gamelogic"

        object Record {
            private const val RECORD = "$SHARED:record"
            const val DATA_STORE = "$RECORD:recordDS"
            const val DATA_STORE_IMPL = "$RECORD:recordDSImpl"
        }
    }

}