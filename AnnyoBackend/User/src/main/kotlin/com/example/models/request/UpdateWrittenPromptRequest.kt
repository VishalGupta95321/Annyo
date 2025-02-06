package com.example.models.request

import com.example.models.entity.WrittenPrompt
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWrittenPromptRequest(
    val promptCount: Int,
    val prompt: String,
    val answer: String,
) {
    fun toWrittenPrompt() = WrittenPrompt(
        prompt = prompt,
        answer = answer
    )
}