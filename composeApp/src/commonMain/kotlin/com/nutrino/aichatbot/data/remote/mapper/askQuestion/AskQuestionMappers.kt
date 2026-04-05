@file:Suppress("unused")

package com.nutrino.aichatbot.data.remote.mapper.askQuestion

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionRequest as RemoteAskQuestionRequest
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse as RemoteAskQuestionResponse
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.Candidate as RemoteCandidate
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.Content as RemoteContent
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.ContentX as RemoteContentX
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.Part as RemotePart
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.PartX as RemotePartX
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.PromptTokensDetail as RemotePromptTokensDetail
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.UsageMetadata as RemoteUsageMetadata
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.model.askQuestion.Candidate
import com.nutrino.aichatbot.domain.model.askQuestion.Content
import com.nutrino.aichatbot.domain.model.askQuestion.ContentX
import com.nutrino.aichatbot.domain.model.askQuestion.Part
import com.nutrino.aichatbot.domain.model.askQuestion.PartX
import com.nutrino.aichatbot.domain.model.askQuestion.PromptTokensDetail
import com.nutrino.aichatbot.domain.model.askQuestion.UsageMetadata

/**
 * Converts the domain request model into the remote Gemini request DTO.
 *
 * @return A request object ready to be serialized and sent to the API.
 */
fun AskQuestionRequest.toRemoteModel(): RemoteAskQuestionRequest {
    return RemoteAskQuestionRequest(
        contents = contents?.map { it?.toRemoteModel() }
    )
}

/**
 * Converts the remote Gemini request DTO back into the domain request model.
 *
 * This is mainly useful for tests or future inbound request handling where the domain layer
 * should remain isolated from serialization types.
 *
 * @return A domain request equivalent to the remote DTO.
 */
fun RemoteAskQuestionRequest.toDomainModel(): AskQuestionRequest {
    return AskQuestionRequest(
        contents = contents?.map { it?.toDomainModel() }
    )
}

/**
 * Converts the domain response model into the remote Gemini response DTO.
 *
 * In practice this is used for symmetry and testing; the normal runtime path maps the remote
 * DTO back into the domain response after the network call completes.
 *
 * @return A remote response DTO containing the same information.
 */
fun AskQuestionResponse.toRemoteModel(): RemoteAskQuestionResponse {
    return RemoteAskQuestionResponse(
        candidates = candidates?.map { it.toRemoteModel() },
        modelVersion = modelVersion,
        responseId = responseId,
        usageMetadata = usageMetadata?.toRemoteModel()
    )
}

/**
 * Converts the remote Gemini response DTO into the domain response model.
 *
 * This keeps the rest of the app insulated from the API schema and serialization concerns.
 *
 * @return A domain response ready for ViewModel and UI consumption.
 */
fun RemoteAskQuestionResponse.toDomainModel(): AskQuestionResponse {
    return AskQuestionResponse(
        candidates = candidates?.map { it.toDomainModel() },
        modelVersion = modelVersion,
        responseId = responseId,
        usageMetadata = usageMetadata?.toDomainModel()
    )
}

/**
 * Converts a domain content block into the remote request content block.
 */
private fun Content.toRemoteModel(): RemoteContent {
    return RemoteContent(
        parts = parts?.map { it?.toRemoteModel() }
    )
}

/**
 * Converts a remote request content block into the domain content block.
 */
private fun RemoteContent.toDomainModel(): Content {
    return Content(
        parts = parts?.map { it?.toDomainModel() }
    )
}

/**
 * Converts a domain candidate into the remote candidate DTO.
 */
private fun Candidate.toRemoteModel(): RemoteCandidate {
    return RemoteCandidate(
        content = content?.toRemoteModel(),
        finishReason = finishReason,
        index = index
    )
}

/**
 * Converts a remote candidate DTO into the domain candidate model.
 */
private fun RemoteCandidate.toDomainModel(): Candidate {
    return Candidate(
        content = content?.toDomainModel(),
        finishReason = finishReason,
        index = index
    )
}

/**
 * Converts a domain response content object into the remote generated content DTO.
 */
private fun ContentX.toRemoteModel(): RemoteContentX {
    return RemoteContentX(
        parts = parts?.map { it.toRemoteModel() },
        role = role
    )
}

/**
 * Converts the remote generated content DTO into the domain response content model.
 */
private fun RemoteContentX.toDomainModel(): ContentX {
    return ContentX(
        parts = parts?.map { it.toDomainModel() },
        role = role
    )
}

/**
 * Converts a simple domain text part into the remote request part DTO.
 */
private fun Part.toRemoteModel(): RemotePart {
    return RemotePart(text = text)
}

/**
 * Converts a remote request part DTO into the domain text part model.
 */
private fun RemotePart.toDomainModel(): Part {
    return Part(text = text)
}

/**
 * Converts a domain response part into the remote response part DTO.
 */
private fun PartX.toRemoteModel(): RemotePartX {
    return RemotePartX(
        text = text,
        thoughtSignature = thoughtSignature
    )
}

/**
 * Converts a remote response part DTO into the domain response part model.
 */
private fun RemotePartX.toDomainModel(): PartX {
    return PartX(
        text = text,
        thoughtSignature = thoughtSignature
    )
}

/**
 * Converts domain token usage metadata into the remote metadata DTO.
 */
private fun UsageMetadata.toRemoteModel(): RemoteUsageMetadata {
    return RemoteUsageMetadata(
        candidatesTokenCount = candidatesTokenCount,
        promptTokenCount = promptTokenCount,
        promptTokensDetails = promptTokensDetails?.map { it?.toRemoteModel() },
        thoughtsTokenCount = thoughtsTokenCount,
        totalTokenCount = totalTokenCount
    )
}

/**
 * Converts remote token usage metadata into the domain metadata model.
 */
private fun RemoteUsageMetadata.toDomainModel(): UsageMetadata {
    return UsageMetadata(
        candidatesTokenCount = candidatesTokenCount,
        promptTokenCount = promptTokenCount,
        promptTokensDetails = promptTokensDetails?.map { it?.toDomainModel() },
        thoughtsTokenCount = thoughtsTokenCount,
        totalTokenCount = totalTokenCount
    )
}

/**
 * Converts a domain prompt-token detail into the remote DTO.
 */
private fun PromptTokensDetail.toRemoteModel(): RemotePromptTokensDetail {
    return RemotePromptTokensDetail(
        modality = modality,
        tokenCount = tokenCount
    )
}

/**
 * Converts a remote prompt-token detail DTO into the domain model.
 */
private fun RemotePromptTokensDetail.toDomainModel(): PromptTokensDetail {
    return PromptTokensDetail(
        modality = modality,
        tokenCount = tokenCount
    )
}
