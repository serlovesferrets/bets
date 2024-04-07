package org.bets.functionality;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bets.exceptions.EventDoesNotExistException;
import org.bets.types.Bet;
import org.bets.types.Event;

public class ProgramDataSingleton {
    private ArrayList<Event> events;
    private ArrayList<Bet> bets;

    public ProgramDataSingleton(ArrayList<Event> events, ArrayList<Bet> bets) {
        this.events = events;
        this.bets = bets;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    public Pair<Event, ArrayList<Bet>> getDataById(int id) throws EventDoesNotExistException {
        var event = events.stream()
                .filter(e -> e.getNumber() == id)
                .findFirst();

        if (event.isEmpty()) {
            throw new EventDoesNotExistException("L'evento #%d non esiste!".formatted(id));
        }

        var betsForEvent = bets.stream()
                .filter(b -> b.getBetNumber() == id)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Pair<>(event.get(), betsForEvent);
    }
}
