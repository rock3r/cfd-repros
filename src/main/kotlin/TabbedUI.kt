import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        swingMain()
    }
}

private fun swingMain() {
    val frame = JFrame("CfD repro")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.minimumSize = Dimension(500, 400)

    val mainPanel = JPanel(BorderLayout())

    val firstComposePanel = ComposePanel().apply {
        setContent {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                var firstFocused by remember { mutableStateOf(false) }
                BasicText(
                    "First button",
                    Modifier.clickable {}.onFocusChanged { firstFocused = it.isFocused }.padding(8.dp, 4.dp),
                )
                var secondFocused by remember { mutableStateOf(false) }
                BasicText(
                    "Second button",
                    Modifier.clickable {}.onFocusChanged { secondFocused = it.isFocused }.padding(8.dp, 4.dp),
                )
                var thirdFocused by remember { mutableStateOf(false) }
                BasicText(
                    "Third button",
                    Modifier.clickable {}.onFocusChanged { thirdFocused = it.isFocused }.padding(8.dp, 4.dp),
                )
            }
        }
        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                println("First tab is focused")
            }

            override fun focusLost(e: FocusEvent?) {
                println("First tab is not focused")
            }
        })
    }

    val secondPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        add(JButton("First button"))
        add(JButton("Second button"))
        add(JButton("Third button"))

        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                println("Second tab is focused")
            }

            override fun focusLost(e: FocusEvent?) {
                println("Second tab is not focused")
            }
        })
    }

    val jTabbedPane = JTabbedPane().apply {
        add("Compose", firstComposePanel)
        add("Swing", secondPanel)
    }
    mainPanel.add(jTabbedPane)

    frame.add(mainPanel, BorderLayout.CENTER)
    frame.isVisible = true
}
