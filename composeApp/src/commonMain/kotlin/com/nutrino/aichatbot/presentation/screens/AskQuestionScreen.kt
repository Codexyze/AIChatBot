package com.nutrino.aichatbot.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nutrino.aichatbot.isDesktop
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.model.askQuestion.Content
import com.nutrino.aichatbot.domain.model.askQuestion.Part
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import com.nutrino.aichatbot.domain.useCases.AskQuestionWithPromptUseCase
import com.nutrino.aichatbot.presentation.states.AskQuestionsUIState
import com.nutrino.aichatbot.presentation.viewmodel.GeminiViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.viewmodel.koinViewModel

/**
 * Renders the main Ask Question screen.
 *
 * This composable wires together the prompt input, loading state, message history, and
 * response handling. It relies on the shared `GeminiViewModel` to send the user prompt and
 * collect state updates. The screen also adapts its layout width for desktop targets.
 *
 * @param viewModel The ViewModel that owns the Ask Question state and network action.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AskQuestionScreen(
    viewModel: GeminiViewModel = koinViewModel()
) {
    MaterialTheme(colorScheme = geminiDarkColorScheme) {
        val contentMaxWidth = if (isDesktop) 980.dp else 560.dp
        var prompt by remember { mutableStateOf("") }
        val uiState by viewModel.askQuestionUIState.collectAsStateWithLifecycle()
        val chatMessages = remember { mutableStateListOf<ChatMessage>() }
        val listState = rememberLazyListState()
        var nextMessageId by remember { mutableStateOf(0L) }
        var lastSuccessMessage by remember { mutableStateOf<String?>(null) }
        var lastErrorMessage by remember { mutableStateOf<String?>(null) }

        val isLoading = uiState is AskQuestionsUIState.isLoading

        LaunchedEffect(uiState) {
            when (val state = uiState) {
                is AskQuestionsUIState.Success -> {
                    val answerText = state.data.candidates
                        ?.firstOrNull()
                        ?.content
                        ?.parts
                        ?.firstOrNull()
                        ?.text
                        ?.takeIf { it.isNotBlank() }
                        ?: "No response text returned by API."

                    if (answerText != lastSuccessMessage) {
                        chatMessages.add(
                            ChatMessage(
                                id = nextMessageId++,
                                role = ChatRole.ASSISTANT,
                                text = answerText
                            )
                        )
                        lastSuccessMessage = answerText
                    }
                }

                is AskQuestionsUIState.Error -> {
                    if (state.message != lastErrorMessage) {
                        chatMessages.add(
                            ChatMessage(
                                id = nextMessageId++,
                                role = ChatRole.ERROR,
                                text = state.message
                            )
                        )
                        lastErrorMessage = state.message
                    }
                }

                AskQuestionsUIState.Idle,
                AskQuestionsUIState.isLoading -> Unit
            }
        }

        LaunchedEffect(chatMessages.size, isLoading) {
            val extraItemCount = if (isLoading) 1 else 0
            val lastIndex = chatMessages.lastIndex + extraItemCount
            if (lastIndex >= 0) {
                listState.animateScrollToItem(lastIndex)
            }
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    title = {
                        Text("Gemini Chat")
                    }
                )
            },
            bottomBar = {
                ChatComposer(
                    prompt = prompt,
                    onPromptChange = { prompt = it },
                    isLoading = isLoading,
                    isDesktop = isDesktop,
                    contentMaxWidth = contentMaxWidth,
                    onSend = {
                        val cleanedPrompt = prompt.trim()
                        if (cleanedPrompt.isNotEmpty()) {
                            chatMessages.add(
                                ChatMessage(
                                    id = nextMessageId++,
                                    role = ChatRole.USER,
                                    text = cleanedPrompt
                                )
                            )
                            viewModel.askQuestionUsingPrompt(buildAskQuestionRequest(cleanedPrompt))
                            prompt = ""
                        }
                    }
                )
            }
        ) { innerPadding ->
            if (chatMessages.isEmpty() && !isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    CenteredContent(contentMaxWidth = contentMaxWidth) {
                        Text(
                            text = "Ask anything. I will answer like Gemini.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp)
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    CenteredContent(contentMaxWidth = contentMaxWidth) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = chatMessages, key = { it.id }) { message ->
                                ChatBubble(
                                    message = message,
                                    maxBubbleWidth = if (isDesktop) 720.dp else 320.dp
                                )
                            }

                            if (isLoading) {
                                item {
                                    LoadingBubble(maxBubbleWidth = if (isDesktop) 720.dp else 320.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Renders the bottom composer used for entering and sending a prompt.
 *
 * @param prompt The current text entered by the user.
 * @param onPromptChange Callback invoked whenever the prompt text changes.
 * @param isLoading Whether the screen is currently waiting for a network response.
 * @param isDesktop Whether the current target should use desktop-style spacing.
 * @param contentMaxWidth The maximum width allowed for the inner content area.
 * @param onSend Callback invoked when the user taps the send button.
 */
@Composable
private fun ChatComposer(
    prompt: String,
    onPromptChange: (String) -> Unit,
    isLoading: Boolean,
    isDesktop: Boolean,
    contentMaxWidth: Dp,
    onSend: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 6.dp
    ) {
        CenteredContent(contentMaxWidth = contentMaxWidth) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isDesktop) 8.dp else 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = onPromptChange,
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    label = { Text("Message") },
                    enabled = !isLoading
                )

                Button(
                    onClick = onSend,
                    enabled = !isLoading && prompt.trim().isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color(0xFF050505)
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Send")
                }
            }
        }
    }
}

/**
 * Displays a single chat bubble for a user, assistant, or error message.
 *
 * @param message The message data to render.
 * @param maxBubbleWidth The maximum width permitted for the bubble content.
 */
@Composable
private fun ChatBubble(message: ChatMessage, maxBubbleWidth: Dp) {
    val isUser = message.role == ChatRole.USER
    val backgroundColor = when (message.role) {
        ChatRole.USER -> MaterialTheme.colorScheme.primaryContainer
        ChatRole.ASSISTANT -> MaterialTheme.colorScheme.secondaryContainer
        ChatRole.ERROR -> MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = when (message.role) {
        ChatRole.USER -> MaterialTheme.colorScheme.onPrimaryContainer
        ChatRole.ASSISTANT -> MaterialTheme.colorScheme.onSecondaryContainer
        ChatRole.ERROR -> MaterialTheme.colorScheme.onErrorContainer
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = backgroundColor,
            contentColor = contentColor,
            shape = MaterialTheme.shapes.large,
            tonalElevation = if (isUser) 2.dp else 1.dp
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .chatBubbleMaxWidth(maxBubbleWidth)
            )
        }
    }
}

/**
 * Displays a loading bubble while the app waits for Gemini to respond.
 *
 * @param maxBubbleWidth The maximum width permitted for the loading bubble content.
 */
@Composable
private fun LoadingBubble(maxBubbleWidth: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = MaterialTheme.shapes.large
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .chatBubbleMaxWidth(maxBubbleWidth),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text("Thinking...")
            }
        }
    }
}

private val geminiDarkColorScheme = darkColorScheme(
    primary = Color(0xFF8AB4F8),
    onPrimary = Color(0xFF050505),
    secondary = Color(0xFF9AA0A6),
    onSecondary = Color(0xFFE8EAED),
    background = Color(0xFF050505),
    onBackground = Color(0xFFE8EAED),
    surface = Color(0xFF0B0B0C),
    onSurface = Color(0xFFE8EAED),
    surfaceVariant = Color(0xFF171717),
    onSurfaceVariant = Color(0xFFBDC1C6),
    outline = Color(0xFF3C4043),
    error = Color(0xFFF28B82),
    onError = Color(0xFF2A0C0A),
    errorContainer = Color(0xFF3A1210),
    onErrorContainer = Color(0xFFF28B82),
    primaryContainer = Color(0xFF12263F),
    onPrimaryContainer = Color(0xFFD2E3FC),
    secondaryContainer = Color(0xFF1E1F20),
    onSecondaryContainer = Color(0xFFE8EAED)
)

/**
 * Centers content within the available width and constrains it to a comfortable maximum size.
 *
 * @param contentMaxWidth The widest size the content should occupy.
 * @param content The composable content to display inside the centered container.
 */
@Composable
private fun CenteredContent(contentMaxWidth: Dp, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .widthIn(max = contentMaxWidth)
        ) {
            content()
        }
    }
}

/**
 * Constrains a message bubble to a readable maximum width.
 *
 * @param maxWidth The maximum width the bubble should occupy.
 * @return A modifier that applies the width constraints.
 */
private fun Modifier.chatBubbleMaxWidth(maxWidth: Dp): Modifier {
    return this
        .fillMaxWidth(fraction = 0.9f)
        .widthIn(max = maxWidth)
}

/**
 * Stores the minimal data required to render a chat entry in the UI.
 *
 * @property id Stable identifier used for list keys and scrolling logic.
 * @property role Identifies whether the message came from the user, assistant, or an error path.
 * @property text The message content shown inside the bubble.
 */
private data class ChatMessage(
    val id: Long,
    val role: ChatRole,
    val text: String
)

/**
 * Enumerates the message roles that the chat screen can render.
 */
private enum class ChatRole {
    USER,
    ASSISTANT,
    ERROR
}

/**
 * Builds the domain request object from raw prompt text.
 *
 * The function wraps the prompt in the request structure expected by the domain layer and the
 * repository mapper.
 *
 * @param prompt The raw text entered by the user.
 * @return A domain request object containing the prompt text.
 */
private fun buildAskQuestionRequest(prompt: String): AskQuestionRequest {
    return AskQuestionRequest(
        contents = listOf(
            Content(
                parts = listOf(
                    Part(text = prompt)
                )
            )
        )
    )
}

/**
 * Preview entry for the Ask Question screen.
 *
 * The preview uses a lightweight fake repository so the screen can render without making
 * a network call.
 */
@Preview
@Composable
private fun AskQuestionScreenPreview() {
    val previewViewModel = remember {
        val previewRepository = object : GeminiRepository {
            override suspend fun askQuestionWithPrompt(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>> {
                return emptyFlow()
            }
        }
        GeminiViewModel(AskQuestionWithPromptUseCase(previewRepository))
    }

    AskQuestionScreen(viewModel = previewViewModel)
}

