package org.bets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.bets.exceptions.DateTooLongException;
import org.bets.exceptions.TooLongException;

public class App {
    static final String fileName = "amounts.dat";
    static final File file = new File(fileName);

    public static void main(String[] args) throws TooLongException, IOException, DateTooLongException {
        if (args.length > 0 && args[0].trim().equalsIgnoreCase("clean")) {
            file.delete();
            return;
        }

        var builder = new BetBuilder()
                .number(2000)
                .eventName("Bordello")
                .date("2024", "03", "22")
                .caseFirst(111.6f)
                .caseEven(220f)
                .caseSecond(225f);

        var bet = builder.build();

        try (final var outputStream = new PrintWriter(new FileOutputStream(file, true))) {
            outputStream.println(bet.serialized());
        }

        try (final var scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                var read = scan.nextLine();
                var newBet = new Bet(read);
                System.out.println(newBet);
            }
        }
    }
}
