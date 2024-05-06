import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.ThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.ui.component.Text
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.LineBorder

fun main() {
    SwingUtilities.invokeLater {
        swingMain()
    }
}

private val loremIpsum =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore " +
            "magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
            "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
            "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
            "deserunt mollit anim id est laborum."

private fun swingMain() {
    val frame = JFrame("CfD repro")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.minimumSize = Dimension(500, 400)

    val mainPanel = JPanel(BorderLayout()).apply {
        border = LineBorder(java.awt.Color.MAGENTA)
        isOpaque = false

        val composePanel = ComposePanel()
        composePanel.border = LineBorder(java.awt.Color.RED)

        composePanel.setContent {
          JewelTheme(JewelTheme.lightThemeDefinition()) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
              repeat(10) {
                Text("Item ${it + 1}")
              }

              SwingPanel(Color.Cyan, {
                JLabel("<html><body>$loremIpsum</body></html>")
              }, Modifier.padding(horizontal = 8.dp).fillMaxWidth())

              repeat(10) {
                Text("Item ${it + 12}")
              }
            }
          }
        }
        add(composePanel, BorderLayout.CENTER)
    }

    frame.contentPane.add(mainPanel, BorderLayout.CENTER)
    frame.isVisible = true
}
