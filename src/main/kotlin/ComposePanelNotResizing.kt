import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.LineBorder
import kotlin.random.Random

fun main() {
  SwingUtilities.invokeLater {
    swingMain()
  }
}

private fun swingMain() {
  val frame = JFrame("CfD repro")
  frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
  frame.minimumSize = Dimension(500, 400)
  frame.isResizable = false

  val mainPanel = JPanel(BorderLayout()).apply {
    border = LineBorder(java.awt.Color.MAGENTA)
    isOpaque = false

    val composePanel = ComposePanel()
    composePanel.border = LineBorder(java.awt.Color.RED)

    val itemHeights = mutableStateListOf<Int>()

    composePanel.setContent {
      Row(
        modifier = Modifier.border(1.dp, Color.DarkGray).background(Color.White),
        verticalAlignment = Alignment.Bottom
      ) {
        for (itemHeight in itemHeights) {
          Box(Modifier.background(Color.Blue).size(10.dp, itemHeight.dp))
        }
      }
    }
    add(composePanel, BorderLayout.CENTER)

    val button = JButton("Add random!")
    button.addActionListener {
      itemHeights.clear()
      repeat(10) {
        itemHeights.add(Random.nextInt(10, 130))
      }
    }

    add(JPanel().apply {
      isOpaque = false
      border = LineBorder(java.awt.Color.CYAN)
      add(button, BorderLayout.SOUTH)
    }, BorderLayout.EAST)
  }

  frame.contentPane.add(mainPanel, BorderLayout.NORTH)
  frame.isVisible = true
}
