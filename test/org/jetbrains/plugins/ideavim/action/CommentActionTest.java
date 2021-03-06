package org.jetbrains.plugins.ideavim.action;

import org.jetbrains.plugins.ideavim.JavaVimTestCase;

import static com.maddyhome.idea.vim.helper.StringHelper.parseKeys;

/**
 * @author dhleong
 */
public class CommentActionTest extends JavaVimTestCase {


  // |gc| |iw|
  public void testBlockCommentInnerWord() {
    doTest(parseKeys("gciw"),
           "<caret>if (condition) {\n" + "}\n",
           "/*if*/ (condition) {\n" + "}\n");
  }

  // |gc| |iw|
  public void testBlockCommentTillForward() {
    doTest(parseKeys("gct{"),
           "<caret>if (condition) {\n" + "}\n",
           "/*if (condition) */{\n" + "}\n");
  }

  // |gc| |ab|
  public void testBlockCommentOuterParens() {
    doTest(parseKeys("gcab"),
           "if (<caret>condition) {\n" + "}\n",
           "if /*(condition)*/ {\n" + "}\n");
  }

  /*
   * NB: linewise motions become linewise comments;
   *  otherwise, they are incredibly difficult to undo
   */

  // |gc| |j|
  public void testLineCommentDown() {
    doTest(parseKeys("gcj"),
           "<caret>if (condition) {\n" + "}\n",
           "//if (condition) {\n" +
           "//}\n");
  }

  // |gc| |ip|
  public void testLineCommentInnerParagraph() {
    doTest(parseKeys("gcip"),
           "<caret>if (condition) {\n" + "}\n",
           "//if (condition) {\n" +
           "//}\n");
  }

  // |gc| |ip|
  public void testLineCommentSingleLineInnerParagraph() {
    doTest(parseKeys("gcip"),
           "<caret>if (condition) {}",
           "//if (condition) {}");
  }

  /* Ensure uncommenting works as well */

  // |gc| |ip|
  public void testLineUncommentInnerParagraph() {
    doTest(parseKeys("gcip"),
           "<caret>//if (condition) {\n" + "//}\n",
           "if (condition) {\n" +
           "}\n");
  }

  // |gc| |ip|
  public void testLineUncommentSingleLineInnerParagraph() {
    doTest(parseKeys("gcip"),
           "<caret>//if (condition) {}",
           "if (condition) {}");
  }

  /* Special shortcut gcc is always linewise */

  // |gcc|
  public void testLineCommentShortcut() {
    doTest(parseKeys("gcc"),
           "<caret>if (condition) {\n" + "}\n",
           "//if (condition) {\n" +
           "}\n");
  }

  // |gcc|
  public void testLineUncommentShortcut() {
    doTest(parseKeys("gcc"),
           "<caret>//if (condition) {\n" + "}\n",
           "if (condition) {\n" + "}\n");
  }



}
