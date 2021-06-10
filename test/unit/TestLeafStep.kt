/*
 * Copyright (c) 2021 by Fred George
 * May be used freely except for training; license required for training.
 * @author Fred George  fredgeorge@acm.org
 */

package unit

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import step.LeafStep
import step.LeafStep.FailureStep
import step.LeafStep.SuccessStep

internal class TestLeafStep {

    @Test fun `success`() {
        assertTrue(LeafStep(SuccessStep(),SuccessStep(),SuccessStep(),SuccessStep()).execute())
    }

    @Test fun `failure`() {
        assertFalse(LeafStep(SuccessStep(),SuccessStep(),SuccessStep(), FailureStep(), SuccessStep()).execute())
    }
}