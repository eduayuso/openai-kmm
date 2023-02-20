package dev.eduayuso.openaikmm.domain.entities

object OpenAIModels {

    interface IOpenAIModel {
        val name: String
        val id: String
    }

    object Ada: IOpenAIModel {
        override val name = "Ada"
        override val id = "text-ada-001"
    }

    object Babbage: IOpenAIModel {
        override val name = "Babbage"
        override val id = "text-babbage-001"
    }

    object Courie: IOpenAIModel {
        override val name = "Courie"
        override val id = "text-curie-001"
    }

    object DaVinci: IOpenAIModel {
        override val name = "DaVinci"
        override val id = "text-davinci-003"
    }
}