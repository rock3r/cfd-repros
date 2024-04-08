import androidx.compose.foundation.Image
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.res.painterResource
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

fun main() {
  SwingUtilities.invokeLater {
    swingMain()
  }
}

private fun swingMain() {
  val frame = JFrame("CfD repro")
  frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
  frame.minimumSize = Dimension(500, 400)
  System.setProperty("compose.swing.render.on.graphics", "true")

  val mainPanel = JPanel(BorderLayout()).apply {
    val container = JPanel().apply {
      layout = BoxLayout(this, BoxLayout.Y_AXIS)
      addChildren()
    }
    val viewport = JScrollPane(container)
    add(viewport, BorderLayout.CENTER)
  }

  frame.contentPane.add(mainPanel, BorderLayout.CENTER)
  frame.isVisible = true
}

private fun JPanel.addChildren() {
  for (i in 0 until 10) {
    for (j in 0 until 5) {
      add(JLabel("<html>" + "Very long text here ".repeat(10) + "</html>"))
      add(Box.createVerticalStrut(10))
    }

    add(
      ComposePanel().apply {
        setContent {
          Image(painterResource("cat.jpg"), contentDescription = null)
        }
      }
    )
    add(Box.createVerticalStrut(10))
  }
}
