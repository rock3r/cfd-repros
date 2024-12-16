import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater { swingMain() }
}

private fun swingMain() {
    val frame = JFrame("CfD repro")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.minimumSize = Dimension(500, 400)

    val mainPanel =
        JPanel(BorderLayout()).apply {
            val composePanel = ComposePanel()

            composePanel.setContent {
                Box(Modifier
                  .border(1.dp, Color.Red)
                  .fillMaxSize()) {
                    var popupVisible by remember { mutableStateOf(false) }
                    if (popupVisible) {
                        Popup(Alignment.Center, IntOffset.Zero, { popupVisible = false }) {
                            BasicText(
                                "POPUP!",
                                Modifier
                                  .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                  .background(Color.White, RoundedCornerShape(4.dp))
                                  .padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                        }
                    }

                    Box(
                        Modifier
                          .padding(8.dp)
                          .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                          .background(Color.White, RoundedCornerShape(4.dp))
                          .clip(RoundedCornerShape(4.dp))
                          .clickable { popupVisible = true }
                          .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        BasicText("Show Popup")
                    }
                }
            }

            composePanel.preferredSize = Dimension(600, 100)
            add(composePanel, BorderLayout.NORTH)

            val otherPanel =
                JPanel(BorderLayout()).apply {
                    border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    val label =
                        JLabel("Move focus to the text area below:").apply {
                            border = BorderFactory.createEmptyBorder(0, 0, 10, 0)
                        }
                    add(label, BorderLayout.NORTH)

                    val textArea = JTextArea().apply { preferredSize = Dimension(600, 100) }
                    add(textArea, BorderLayout.SOUTH)
                }
            add(otherPanel, BorderLayout.SOUTH)
        }

    frame.contentPane.add(mainPanel, BorderLayout.CENTER)
    frame.isVisible = true
}
