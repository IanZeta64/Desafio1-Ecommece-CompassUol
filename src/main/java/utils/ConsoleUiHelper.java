package utils;
import Entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUiHelper<T> {

    public static LocalDate askLocalDate(String message, String localDatePattern) {
        System.out.printf("%s (%s): %n", message, localDatePattern);
        Scanner sc = new Scanner(System.in);
        LocalDate localDate;
        do {
            try {
                localDate = LocalDate.parse(sc.next().trim(), DateTimeFormatter.ofPattern(localDatePattern));
            } catch (Exception e) {
                localDate = null;
            }
        } while (localDate == null);
        return localDate;
    }

    public static String askSimpleInput(String message) {
        System.out.printf("%s: %n", message);
        return new Scanner(System.in).nextLine().trim();
    }

    public static char askCharacterInput(String message) {
        System.out.printf("%s: %n", message);
        return new Scanner(System.in).next().trim().charAt(0);
    }

    public static int askChooseOption(String message, String... options) {

        System.out.printf("%s %n", message);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d - %s %n", i + 1, options[i]);
        }
        Scanner sc = new Scanner(System.in);
        int choose;
        do {
            try {
                choose = sc.nextInt();
                if (choose < 1 || choose > options.length) {
                    System.out.println("Enter an integer numeric value:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer numeric value:");
                choose = -9;
                sc.nextLine();
            }

        } while (choose < 1 || choose > options.length);
        return choose;
    }

    public static boolean askConfirm(String message, String yes, String no) {
        return askChooseOption(message, yes, no) == 1;
    }

    public static Integer askNumber(String message) {
        System.out.printf("%s: %n", message);
        Scanner sc = new Scanner(System.in);
        Integer number;
        do {
            try {
                number = sc.nextInt();
            } catch (InputMismatchException e) {
                number = null;
            }
        } while (number == null);
        return number;
    }

    public static void drawWithPadding(String text, int width, char character) {
        do {
            int limit = Math.min(text.length(), width - 4);
            String row = text.substring(0, limit);
            text = text.substring(row.length());
            int padding = (width - row.length()) / 2;
            row = character + " ".repeat(padding - 1) + row;
            row = row + " ".repeat(width - row.length() - 1) + character;
            System.out.println(row);
        } while (!text.isEmpty());
    }

    public static void drawHeader(String title, int width, char character) {
        drawLine(width, character);
        drawWithPadding(title, width, character);
        drawLine(width, character);
    }

    public static void drawLine(int width, char character) {
        System.out.println(String.valueOf(character).repeat(width));
    }

    public static void listProductPages(List<Product> list, int limitPerPg) {
        new ConsoleUiHelper<Product>().listWithPages(list, limitPerPg);
    }

    public static void listOrderLinesPages(List<OrderLine> list, int limitPerPg) {
        new ConsoleUiHelper<OrderLine>().listWithPages(list, limitPerPg);
    }

    public static void listOrdersPages(List<Order> list, int limitPerPg) {
        new ConsoleUiHelper<Order>().listWithPages(list, limitPerPg);
    }
    public static void listCustomersPages(List<Customer> list, int limitPerPg) {
        new ConsoleUiHelper<Customer>().listWithPages(list, limitPerPg);
    }
    public static void listEmployeesPages(List<Employee> list, int limitPerPg) {
        new ConsoleUiHelper<Employee>().listWithPages(list, limitPerPg);
    }

    private void listWithPages(List<T> list, int limitPerPg) {
        int pg;
        int skip = 0;
        int numPg = 1;
        boolean continues = true;
        if (list.size() > limitPerPg) {

            do {
                list.stream().skip(skip).limit(limitPerPg).forEach(System.out::println);

                System.out.printf("%75s %d %n", "PAGE", numPg);

                if (skip < limitPerPg) {

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Next page");
                    if (pg == 1) continues = false;
                    else {
                        skip = limitPerPg;
                        numPg++;
                    }
                } else if (list.size() - skip > limitPerPg) {
                    pg = askChooseOption("\"Do you want to leave or go to the next page?", "Exit", "Next page", "Previous page");
                    if (pg == 1) continues = false;
                    else if (pg == 2) {
                        skip += limitPerPg;
                        numPg++;
                    } else {
                        skip -= limitPerPg;
                        numPg--;
                    }
                } else {

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Previous page");

                    if (pg == 1) continues = false;
                    else {
                        skip -= limitPerPg;
                        numPg--;
                    }
                }
            } while (continues);
        } else if (!list.isEmpty()) list.forEach(System.out::println);
        else System.out.println("List is empty.");
    }
}