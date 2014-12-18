package org.jetbrains.plugins.ideavim.action;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.maddyhome.idea.vim.KeyHandler;
import com.maddyhome.idea.vim.helper.EditorDataContext;
import com.maddyhome.idea.vim.helper.RunnableHelper;
import org.jetbrains.plugins.ideavim.VimTestCase;

import javax.swing.*;
import java.util.List;

import static com.maddyhome.idea.vim.helper.StringHelper.parseKeys;

public class InsertNewLineActionTest extends VimTestCase {
  public void testInsertAfterFold() {
    doTest(parseKeys("O"),
           "\n" +
           "/* I should not be folded\n" +
           " * a little more text\n" +
           " * and final fold */\n" +
           "/*and some <caret>text after*/",
           "\n" +
           "/* I should not be folded\n" +
           " * a little more text\n" +
           " * and final fold */\n" +
           "\n" +
           " /*and some text after*/"
    );
  }

  @SuppressWarnings("UnusedDeclaration")
  public void failingtestInsertBeforeFold() {
    doTest(parseKeys("zco"),
           "\n" +
           "/**\n" +
           " * I should be folded<caret>\n" +
           " * a little more text\n" +
           " * and final fold */\n" +
           "/*and some text after*/",

           "\n" +
           "/**\n" +
           " * I should be folded\n" +
           " * a little more text\n" +
           " * and final fold */\n" +
           "\n" +
           "/*and some text after*/"
    );
  }

  /**
   * comment 1
   */
/* foo */
  private void doTest(final List<KeyStroke> keys, String before, String after) {
    myFixture.configureByText("a.java", before);
    final Editor editor = myFixture.getEditor();
    final KeyHandler keyHandler = KeyHandler.getInstance();
    final EditorDataContext dataContext = new EditorDataContext(editor);
    final Project project = myFixture.getProject();
    RunnableHelper.runWriteCommand(project, new Runnable() {
      @Override
      public void run() {
        for (KeyStroke key : keys) {
          keyHandler.handleKey(editor, key, dataContext);
        }
      }
    }, null, null);
    myFixture.checkResult(after);
  }
}
