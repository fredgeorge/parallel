/*
 * Copyright (c) 2021 by Fred George
 * May be used freely except for training; license required for training.
 * @author Fred George  fredgeorge@acm.org
 */

package step

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import step.LeafStep.FailureStep
import step.LeafStep.SuccessStep

interface Step {
    fun execute(): Boolean
    fun undo() = Unit
}

class LeafStep(vararg steps: Step) : Step {
    private val steps = steps.toList()

    override fun execute(): Boolean {
        return runBlocking {
            steps
                .map { step -> async { execute(step) } }
                .map { job -> job.await() }
                .let { results ->
                    results
                        .all { it }
                        .also { finalResult ->
                            if (!finalResult) steps.zip(results).forEach { (step, stepResult) ->
                                if (stepResult) step.undo()
                            }
                        }
                }
        }
    }

    private suspend fun execute(step: Step) = step.execute().also { result ->
        delay(if (result) 2000 else 3000) // Sleep interrupts all threads!
    }

    internal class SuccessStep : Step {
        override fun execute() = true
    }

    internal class FailureStep : Step {
        override fun execute() = false
    }
}

internal class TestLeafStep {
    // Tests may be run in parallel threads, so time could be 3-5 seconds depending

    @Test
    fun `functional success`() {
        assertTrue(
            LeafStep(
                SuccessStep(),
                SuccessStep(),
                SuccessStep(),
                SuccessStep()
            ).execute(),
            "This test should only take a bit more than 2 seconds"
        )
    }

    @Test
    fun `functional failure`() {
        assertFalse(
            LeafStep(
                SuccessStep(),
                SuccessStep(),
                SuccessStep(),
                FailureStep(),
                SuccessStep()
            ).execute(),
            "This test should only take a bit more than 3 seconds"
        )
    }
}