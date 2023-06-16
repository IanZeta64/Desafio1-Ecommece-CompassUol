package utils;

import Entities.Order;
import Entities.OrderLine;
import Entities.Product;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUiHelper<T> {

    public static String askSimpleInput(String message) {
        System.out.printf("%s: %n", message);
        return new Scanner(System.in).nextLine().trim();

    }

    public static String askNoEmptyInput(String message, int retries) {
        System.out.printf("%s%n: ", message);
        Scanner sc = new Scanner(System.in);
        String input;
        int tries = 0;
        do {
            input = sc.nextLine().trim();
            tries++;
        } while (input.isBlank() && retries > 0 && tries < retries);
        return input;
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
                if (choose < 1 || choose > options.length){
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
        String[] op = new String[2];
        op[0] = yes;
        op[1] = no;
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

    public static int drawWithRightPadding(String text, int width, char pad) {
        int count = 0;
        do {
            int limit = Math.min(text.length(), width - 4);
            String row = text.substring(0, limit);
            text = text.substring(row.length());
            row = "# " + row + String.valueOf(pad).repeat(width - row.length() - 4) + " #";
            System.out.println(row);
            count++;
        } while (!text.isEmpty());
        return count;
    }

    public static int drawWithPadding(String text, int width) {
        int count = 0;
        do {
            int limit = Math.min(text.length(), width - 4);
            String row = text.substring(0, limit);
            text = text.substring(row.length());
            int padding = (width - row.length()) / 2;
            row = "#" + " ".repeat(padding-1) + row;
            row = row + " ".repeat(width - row.length() - 1) + "#";
            System.out.println(row);
            count++;
        } while (!text.isEmpty());
        return count;
    }

    public static void drawHeader(String title, int width) {
        drawLine(width);
        drawWithPadding(title, width);
        drawLine(width);
    }

    public static void drawLine(int width) {
        System.out.println("#".repeat(width));
    }

    public static void fillVSpace(int lines, int width) {
        drawWithPadding(" ".repeat(lines * width), width);
    }

    public static String columnPaddingLeft(String text, int width, char pad) {
        while(text.length() < width){
            text = String.valueOf(pad).concat(text);
        }
        return text;
    }

    public static String columnPaddingRight(String text, int width, char pad) {
        while(text.length() < width){
            text = text.concat(String.valueOf(pad));
        }
        return text;
    }

    public static void listProductsWithPages(List<Product> list) {
        int pg = 1;
        int skip = 0;
        int numPg = 1;
        boolean continues;
        if (list.size() > 5) {

            do {
                list.stream().skip(skip).limit(5).forEach(System.out::println);

                System.out.printf("%75s %d %n","PAGE", numPg);

                if(skip < 5){

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Next page");
                    if(pg == 1)continues = false;
                    else {
                        continues = true;
                        skip = 5;
                        numPg++;
                    }
                }else if(list.size() - skip > 5 ){
                    pg = askChooseOption("\"Do you want to leave or go to the next page?", "Exit", "Next page", "Previous page");
                    if(pg ==1) continues = false;
                    else if (pg == 2) {
                        continues = true;
                        skip += 5;
                        numPg++;
                    }
                    else {
                        continues = true;
                        skip -=5;
                        numPg--;
                    }
                }else {

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Previous page");

                    if(pg ==1)continues = false;
                    else {
                        continues = true;
                        skip -= 5;
                        numPg--;
                    }
                }
            }while (continues);
        } else if (!list.isEmpty()) list.forEach(System.out::println);
        else System.out.println("List is empty.");
    }

    public static void listOrderLinesWithPages(List<OrderLine> list) {
        int pg = 1;
        int skip = 0;
        int numPg = 1;
        boolean continues;
        if (list.size() > 5) {

            do {
                list.stream().skip(skip).limit(5).forEach(System.out::println);

                System.out.printf("%75s %d %n","PAGE", numPg);

                if(skip < 5){

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Next page");
                    if(pg == 1)continues = false;
                    else {
                        continues = true;
                        skip = 5;
                        numPg++;
                    }
                }else if(list.size() - skip > 5 ){
                    pg = askChooseOption("\"Do you want to leave or go to the next page?", "Exit", "Next page", "Previous page");
                    if(pg ==1) continues = false;
                    else if (pg == 2) {
                        continues = true;
                        skip += 5;
                        numPg++;
                    }
                    else {
                        continues = true;
                        skip -=5;
                        numPg--;
                    }
                }else {

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Previous page");

                    if(pg ==1)continues = false;
                    else {
                        continues = true;
                        skip -= 5;
                        numPg--;
                    }
                }
            }while (continues);
        } else if (!list.isEmpty()) list.forEach(System.out::println);
        else System.out.println("List is empty.");
    }

    public static void listOrderWithPages(List<Order> list) {
        int pg = 1;
        int skip = 0;
        int numPg = 1;
        boolean continues;
        if (list.size() > 5) {

            do {
                list.stream().skip(skip).limit(5).forEach(System.out::println);

                System.out.printf("%75s %d %n","PAGE", numPg);

                if(skip < 5){

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Next page");
                    if(pg == 1)continues = false;
                    else {
                        continues = true;
                        skip = 5;
                        numPg++;
                    }
                }else if(list.size() - skip > 5 ){
                    pg = askChooseOption("\"Do you want to leave or go to the next page?", "Exit", "Next page", "Previous page");
                    if(pg ==1) continues = false;
                    else if (pg == 2) {
                        continues = true;
                        skip += 5;
                        numPg++;
                    }
                    else {
                        continues = true;
                        skip -=5;
                        numPg--;
                    }
                }else {

                    pg = askChooseOption("Do you want to leave or go to the next page?", "Exit", "Previous page");

                    if(pg ==1)continues = false;
                    else {
                        continues = true;
                        skip -= 5;
                        numPg--;
                    }
                }
            }while (continues);
        } else if (!list.isEmpty()) list.forEach(System.out::println);
        else System.out.println("List is empty.");

    }

}