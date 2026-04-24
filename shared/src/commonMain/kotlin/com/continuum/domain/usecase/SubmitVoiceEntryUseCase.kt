package com.continuum.domain.usecase

import com.continuum.agent.ClinicalSieveAgent
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.domain.repository.ClinicalRepository

class SubmitVoiceEntryUseCase(
    private val agent: ClinicalSieveAgent,
    private val repository: ClinicalRepository
) {
    suspend operator fun invoke(
        userId: String,
        rawVoice: String
    ): Result<ClinicalEntry> {
        val agentResult = agent.process(userId, rawVoice)
        if (agentResult is Result.Error) return agentResult

        val entry = (agentResult as Result.Success).data
        val saveResult = repository.submitEntry(entry)
        if (saveResult is Result.Error) return saveResult

        return Result.Success(entry)
    }
}
