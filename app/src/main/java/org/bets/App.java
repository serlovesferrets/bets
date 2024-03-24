package org.bets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bets.exceptions.*;
import org.bets.interlocutor.StatefulArgsInterlocutor;

public class App {
    static final String fileName = "betting.dat";
    static final File file = new File(fileName);

    public static ArrayList<Bet> loadBets() throws DateFormatException, TimeFormatException, IOException {
        var list = new ArrayList<Bet>();
        try (final var scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                var read = scan.nextLine();
                var bet = new Bet(read);
                list.add(bet);
            }
        }

        return list;
    }

    public static void addBet(String[] args) throws NumberFormatException, AmountTooLongException,
            EventNameTooLongException, NumberTooLongException, TimeFormatException, DateFormatException,
            FileNotFoundException {
        var builder = new StatefulArgsInterlocutor(args)
                .askNumber()
                .askName()
                .askDate()
                .askTime()
                .askFirstCase()
                .askEvenCase()
                .askSecondCase();

        Bet bet = null;

        try {
            bet = builder.build();
        } catch (MissingBuilderFieldException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try (final var outputStream = new PrintWriter(new FileOutputStream(file, true))) {
            outputStream.println(bet.serialized());
        }
    }

    public static void main(String[] args)
            throws TooLongException, IOException, DateFormatException, TimeFormatException {
        file.createNewFile();
        if (args.length < 1) {
            System.out.println("Arg needed!");
            System.exit(1);
        }

        var opt = args[0].trim();

        if (opt.equals("clean")) {
            file.delete();
            return;
        }

        if (opt.equals("add")) {
            addBet(args);
            return;
        }

        if (opt.equals("show-bets")) {
            var bets = loadBets();
            bets.forEach(System.out::println);
            return;
        }
    }
}
