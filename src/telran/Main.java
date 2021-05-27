package telran;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        List<String> dataFromFile = new ArrayList<>();
        readFile(dataFromFile, "exams.txt");

        String dataFromConsole = isThisDateValid();
//        Scanner console = new Scanner(System.in);
//        String dataFromConsole = console.nextLine();

        checkData(dataFromConsole, dataFromFile);
    }


    private static String isThisDateValid() {
        System.out.println("Enter please data of the exam: ");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Scanner console = new Scanner(System.in);
        String dataFromConsole = console.nextLine();

        String[] arr = dataFromConsole.split("\\.");

        try {
            if (arr[0].length() > 2 || arr[1].length() > 2 || arr[2].length() > 4) {
                throw new DateTimeException("Error in date: ");
            }

            LocalDate date = LocalDate.parse(dataFromConsole, formatter1);
            System.out.println(date);

        } catch (DateTimeException e) {

            System.out.println("it is incorrect format of the data === please entre again");
            isThisDateValid();

        }
        return dataFromConsole;

    }

    private static void checkData(String dataFromConsole, List<String> dataFromFile) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("MMM,dd yyyy");
        LocalDate dataConsole = LocalDate.parse(dataFromConsole, formatter2);

        for (int i = 0; i < dataFromFile.size(); i++) {
            LocalDate localDate = LocalDate.parse(dataFromFile.get(i), formatter1);

            if (localDate.format(formatter2).equals(dataFromConsole))
                System.out.println(localDate.format(formatter3) + "     today");

            long days = ChronoUnit.DAYS.between(dataConsole, localDate);
            if (days < 0 && days != -1)
                System.out.println(localDate.format(formatter3) + "     was " + (-1) * days + " ago");

            if (days == -1)
                System.out.println(localDate.format(formatter3) + "     was yesterday ");

            if (days == 1)
                System.out.println(localDate.format(formatter3) + "     tomorrow ");

            if (days > 0 && days != 1)
                System.out.println(localDate.format(formatter3) + "     in " + days + " days");

        }
    }

    private static void readFile(List<String> dataFromFile, String exams) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(exams));
        String line;
        while ((line = br.readLine()) != null) {
            dataFromFile.add(line);
        }
        br.close();
    }
}
