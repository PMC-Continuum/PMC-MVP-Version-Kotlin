package com.continuum.domain.usecase

import com.continuum.agent.TrendAnalysisAgent
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.domain.model.TrendAnalysis
import com.continuum.domain.repository.ClinicalRepository

data class TrendsResult(
    val entries: List<ClinicalEntry>,
    val analysis: TrendAnalysis
)

class GetHealthTrendsUseCase(
    private val repository: ClinicalRepository,
    private val agent: TrendAnalysisAgent
) {
    suspend operator fun invoke(userId: String): Result<TrendsResult> {
        val entriesResult = repository.getEntries(userId)
        if (entriesResult is Result.Error) return entriesResult

        val entries = (entriesResult as Result.Success).data
        val analysisResult = agent.analyze(entries)
        if (analysisResult is Result.Error) return analysisResult

        return Result.Success(
            TrendsResult(
                entries = entries,
                analysis = (analysisResult as Result.Success).data
            )
        )
    }
}
