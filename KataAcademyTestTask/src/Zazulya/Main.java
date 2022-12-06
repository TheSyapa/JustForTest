package Zazulya;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        int i=0;
        do {
            Scanner scanner = new Scanner(System.in);
            String InputString = (scanner.nextLine());
            try {
                System.out.println(calc(InputString));
            }
            catch (IOException e){
                throw new IOException();
            }
        }while(i == 0);
    }

    public static String calc(String input) throws IOException {
        Pattern pattern = Pattern.compile("[+\\-*/]");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()){
            throw new IOException();
        };

        char operand = input.charAt(matcher.start());
        String value1 = input.substring(0, matcher.start()).trim();
        String value2 = input.substring(matcher.end()).trim();

        if (value1.matches("^10|[1-9]$")  & value2.matches("^10|[1-9]$")){
            return calculate(Integer.parseInt(value1),Integer.parseInt(value2), operand) + "";
        }
        else if(value1.matches("^X|IX|IV|V?I{0,3}$") & value2.matches("^X|IX|IV|V?I{0,3}$") &
                                !value1.equals("") & !value2.equals("")){
            return arabicToRoman(calculate(romanToArabic(value1), romanToArabic(value2), operand));
        }
        else{
            throw new IOException();
        }
    }

    public static int calculate(int value1, int value2, char operand) throws IllegalArgumentException {
        return switch (operand) {
            case '+' -> value1 + value2;
            case '-' -> value1 - value2;
            case '*' -> value1 * value2;
            case '/' -> value1 / value2;
            default -> throw new IllegalArgumentException();
        };
    }

    public static int romanToArabic(String input) {
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((input.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (input.startsWith(symbol.name())) {
                result += symbol.getValue();
                input = input.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (input.length() > 0) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number < 1) || (number > 100)) {
            throw new IllegalArgumentException();
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
