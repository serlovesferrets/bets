package org.bets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bets.exceptions.*;
import org.bets.functionality.ProgramDataSingleton;
import org.bets.types.Bet;
import org.bets.types.BetResult;
import org.bets.types.Event;

import org.bets.interlocutors.*;
import org.bets.phase2.Simulator;

public class App {
    static final String eventsFileName = "betting.dat";
    static final File eventsFile = new File(eventsFileName);

    static final String betsFileName = "amounts.dat";
    static final File betsFile = new File(betsFileName);

    static ProgramDataSingleton data;

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

        if (Event.numberExists(data.getEvents(), bet.getNumber())) {
            throw new DuplicateEventException("L'evento on id #%d esiste già!".formatted(bet.getNumber()));
        }

        try (final var outputStream = new PrintWriter(new FileOutputStream(eventsFile, true))) {
            outputStream.println(bet.serialized());
        }
    }

    public static void addBet(String[] args)
            throws NumberFormatException, BettorNameTooLongException, ResultFormatException, BetNotInRangeException,
            BetNotFoundException, FileNotFoundException, MissingBuilderFieldException {
        var builder = new StatefulArgsBetInterlocutor(args, data.getEvents())
                .askBetNumber()
                .askBetAmount()
                .askBettorName()
                .askBetResult();

        Bet bet = builder.build();

        try (final var outputStream = new PrintWriter(new FileOutputStream(betsFile, true))) {
            outputStream.println(bet.serialized());
        }
    }

    public static ArrayList<Integer> getAllEventIds() {
        var res = data.getEvents().stream().map(Event::getNumber).toList();
        return new ArrayList<>(res);
    }

    public static void showBetData(String[] args) throws EventDoesNotExistException {
        if (args.length < 3) {
            System.out.println("Need a number and one of '1', '2' or 'X'!");
            System.out.println("Arg 1: '" + args[1] + "' (should be a number)");
            System.out.println("Arg 2: '" + args[2] + "' (should be '1', '2' or 'X')");
            System.exit(1);
        }

        var id = Integer.parseInt(args[1]);
        var resultChar = args[2].charAt(0);

        var result = BetResult.fromChar(resultChar);

        var pair = data.getDataById(id);
        var event = pair.getLeft();
        var bets = pair.getRight();

        System.out.println(event);

        var quote = switch (result) {
            case ONE -> event.getCaseFirst();
            case TWO -> event.getCaseSecond();
            case EVEN -> event.getCaseEven();
        };

        var total = 0.0f;
        for (var bet : bets) {
            System.out.print(bet);
            var betResult = bet.getResult();
            var betAmount = bet.getBetAmount();

            if (betResult != result) {
                total += betAmount;
                System.out.println(", -%d".formatted(betAmount));
            } else {
                var gained = betAmount * quote - betAmount;
                total = total + gained;
                System.out.println(", +%.2f".formatted(gained));
            }
        }

        System.out.println("Budget: %.2f".formatted(total));
    }

    public static Simulator createSimulator(String[] args) {
        var times = Integer.parseInt(args[1]);
        var id = Integer.parseInt(args[2]);
        return new Simulator(times, id);
    }

    public static void main(String[] args)
            throws TooLongException, IOException, DateFormatException, TimeFormatException, NumberFormatException,
            DuplicateEventException, MissingBuilderFieldException, ResultFormatException, BetNotInRangeException,
            BetNotFoundException, EventDoesNotExistException {
        eventsFile.createNewFile();
        betsFile.createNewFile();

        if (args.length < 1) {
            System.out.println("Arg needed!");
            System.exit(1);
        }

        data = new ProgramDataSingleton(loadEvents(), loadBets());

        var opt = args[0];

        switch (opt) {
            case "clean-events":
                eventsFile.delete();
                break;

            case "clean-bets":
                betsFile.delete();
                break;

            case "add-event":
                addEvent(args);
                break;

            case "add-bet":
                addBet(args);
                break;

            case "show-events":
                data.getEvents().forEach(System.out::println);
                break;

            case "show-bets":
                data.getBets().forEach(System.out::println);
                break;

            case "get-bet":
                showBetData(args);
                break;

            case "get-ids":
                getAllEventIds().forEach(System.out::println);
                break;

            case "simulation":
                var simulator = createSimulator(args);
                System.out.println("created with succ(ess)");
                break;

            default:
                System.out.println("Comando invalido! Ti direi di leggere la manpage, ma non ne ho fatta una. Oops.");
        }
    }
}
