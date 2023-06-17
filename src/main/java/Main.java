import utils.ConsoleUiHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUiHelper.drawWithPadding("TEXTO", 80, '@');
        ConsoleUiHelper.drawHeader("TITLE", 80, '#');
        ConsoleUiHelper.drawLine(80, '!');
        ConsoleUiHelper.drawWithRightPadding("RIGHT", 80, '*', '+');
        ConsoleUiHelper.fillVSpace(3, 80, '&');
        System.out.println(ConsoleUiHelper.columnPaddingLeft("PADDING <-", 80, '^'));
        System.out.println(ConsoleUiHelper.columnPaddingRight("PADDING ->", 80, '$'));

    }
}
