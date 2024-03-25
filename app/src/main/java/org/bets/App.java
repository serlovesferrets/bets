package org.bets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bets.exceptions.*;
import org.bets.types.Bet;
import org.bets.types.Event;

import org.bets.interlocutors.*;

public class App {
    static final String eventsFileName = "betting.dat";
    static final File eventsFile = new File(eventsFileName);

    static final String betsFileName = "amounts.dat";
    static final File betsFile = new File(betsFileName);

    static ArrayList<Event> events = null;
    static ArrayList<Bet> bets = null;

    public static ArrayList<Event> loadEvents() throws DateFormatException, TimeFormatException, IOException {
        var list = new ArrayList<Event>();
        try (final var scan = new Scanner(eventsFile)) {
            while (scan.hasNextLine()) {
                var read = scan.nextLine();
                var bet = new Event(read);
                list.add(bet);
            }
        }

        return list;
    }

    public static ArrayList<Bet> loadBets() throws FileNotFoundException, IllegalArgumentException {
        var list = new ArrayList<Bet>();
        try (final var scan = new Scanner(betsFile)) {
            while (scan.hasNextLine()) {
                var read = scan.nextLine();
                var bet = new Bet(read);
                list.add(bet);
            }
        }

        return list;
    }

    public static void addEvent(String[] args) throws NumberFormatException, AmountTooLongException,
            EventNameTooLongException, NumberTooLongException, TimeFormatException, DateFormatException,
            FileNotFoundException, DuplicateEventException, MissingBuilderFieldException {
        var builder = new StatefulArgsEventInterlocutor(args)
                .askNumber()
                .askName()
                .askDate()
                .askTime()
                .askFirstCase()
                .askEvenCase()
                .askSecondCase();

        var bet = builder.build();

        if (Event.numberExists(events, bet.getNumber())) {
            throw new DuplicateEventException("L'evento on id #%d esiste già!".formatted(bet.getNumber()));
        }

        try (final var outputStream = new PrintWriter(new FileOutputStream(eventsFile, true))) {
            outputStream.println(bet.serialized());
        }
    }

    public static void addBet(String[] args)
            throws NumberFormatException, BettorNameTooLongException, ResultFormatException, BetNotInRangeException,
            BetNotFoundException, FileNotFoundException, MissingBuilderFieldException {
        var builder = new StatefulArgsBetInterlocutor(args, events)
                .askBetNumber()
                .askBetAmount()
                .askBettorName()
                .askBetResult();

        Bet bet = builder.build();

        try (final var outputStream = new PrintWriter(new FileOutputStream(betsFile, true))) {
            outputStream.println(bet.serialized());
        }
    }

    public static void main(String[] args)
            throws TooLongException, IOException, DateFormatException, TimeFormatException, NumberFormatException,
            DuplicateEventException, MissingBuilderFieldException, ResultFormatException, BetNotInRangeException,
            BetNotFoundException {
        eventsFile.createNewFile();
        betsFile.createNewFile();

        if (args.length < 1) {
            System.out.println("Arg needed!");
            System.exit(1);
        }

        events = loadEvents();
        bets = loadBets();

        var opt = args[0];

        if (opt.equals("clean-events")) {
            eventsFile.delete();
            return;
        }

        if (opt.equals("clean-bets")) {
            betsFile.delete();
            return;
        }

        if (opt.equals("add-event")) {
            addEvent(args);
            return;
        }

        if (opt.equals("add-bet")) {
            addBet(args);
            return;
        }

        if (opt.equals("show-events")) {
            var bets = loadEvents();
            for (var bet : bets) {
                System.out.println(bet);
            }
            return;
        }

        System.out.println("Command not found!");
    }
}
