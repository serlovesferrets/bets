# Part 1

Build an application that allows you to create bets, enter bets and provide
statistics on earnings / losses.

## 1. Creating a bet

Ask the user for data on:

- number integer(5) - event name string(20) - date string(8) - YYYYMMDD format -
time string(4) - HHMM format - quota 1 float(5) - quota X float(5) - quota 2
float(5)

Check the data and add a record to a `betting.dat` file

## 2. Acceptance of a bet

The user enters the number of the event (check that exists in the betting file),
the name of the bettor - string(15), the amount of the bet (from 10 to 100 euros
- integer(3)) and the result (1-X-2– string(1)).

The data is added to the "amounts.dat" file

## 3. Bet Closure

The user enters the event number (check that it exists in the betting file) and
the result; the system reads the amounts file searching for wagers related to
the event and displays them in a list with the name of the bettor, the expected
result and the amount earned / lost by the organization.  At the bottom is
showed the budget (sum of the amounts earned / lost).

# Example

Event 1 is terminated with the result X; bettor 1 made a bet of 80 € on result 1
(earn) while bettor 2 made a bet of 70€ on result X (loss); the X quote was
2.70. The list displays the following data:

| Name    | Amount | Result | Earn / Loss |
| :-----: | :----: | :----: | :---------: |
| bettor1 | 80     | 1      | +80         |
| bettor2 | 70     | X      | -119        |

Budget: -39€

(the organization has a loss of 39€)

The loss is given by: amount * quote - amount
