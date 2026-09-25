package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.engine.SkyPromptEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Sky Prompt", appName)
  }

  @Test
  fun `test prompt engine synthesis`() {
    val result = SkyPromptEngine.generatePrompt("Create a YouTube video about space exploration")
    assertNotNull(result)
    assertTrue(result.structure.role.isNotEmpty())
    assertTrue(result.structure.instructions.isNotEmpty())
    assertTrue(result.score.overall in 90..99)
  }

  @Test
  fun `test prompt improvement engine`() {
    val result = SkyPromptEngine.improvePrompt("Write code for my app")
    assertNotNull(result)
    assertTrue(result.improvedScore > result.originalScore)
    assertTrue(result.improvedPrompt.isNotEmpty())
  }
}
