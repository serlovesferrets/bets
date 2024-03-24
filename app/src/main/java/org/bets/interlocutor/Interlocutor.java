package org.bets.interlocutor;

import org.bets.Bet;
import org.bets.exceptions.*;

/* Using an interface as i ~~can't get gradle run to use stdin for input~~
 * figured out how to change stdin too far into the project: 
 * 
 * ```java
 * run {
 *     standardInput = System.in
 * }
 * ```
 * 
 * One could use this interface to implement a "ScannerInterlocutor" that uses stdin
 * The generic is used so the return type is the type of the builder and not
 * the interface; this also allows the builder to preserve its type on inheritance
 * (see https://www.baeldung.com/java-builder-pattern-inheritance#solution-with-generics)
 */
public interface Interlocutor<T extends Interlocutor<T>> {
    T askNumber() throws NumberTooLongException;

    T askName() throws EventNameTooLongException;

    T askDate() throws DateFormatException;

    T askTime() throws TimeFormatException;

    T askFirstCase() throws AmountTooLongException;

    T askEvenCase() throws AmountTooLongException;

    T askSecondCase() throws AmountTooLongException;

    Bet build() throws MissingBuilderFieldException, DateFormatException, TimeFormatException;
}
