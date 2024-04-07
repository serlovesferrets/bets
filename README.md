# Gambling

Questa è un'applicazione in Java scritta per educazione civica. Permette di salvare delle informazioni su delle scommesse localemente, e ho scritto uno shell script chiamato `entry` per facilitare l'intereazione (immagino dover scrivere `gradle run --args='<args>` per ogni comando possa diventare scomodo in fretta, vero?).

## Ma come dovrei usare `entry`?

Anzitutto, non dimenticarsi di eseguire `chmod +x entry`.

```sh
# Aggiunge UN solo evento predeterminato.
./entry add-event 

# Aggiunge UNA sola scommessa predeterminata.
./entry add-bet 

# Aggiunge vari eventi predeterminati.
./entry add-events 

# Aggiunge varie scommesse predeterminate.
./entry add-bets 

# Mostra gli eventi.
./entry show-events 

# Mostra le scommesse.
./entry show-bets 

# Elimina il file `betting.dat`.
./entry clean-events 

# Elimina il file `amounts.dat`.
./entry clean-bets 

# clean-events && clean-bets.
./entry clean 

# Mostra le informazioni dell'evento $EVENT_ID (numero) dato un $RESULT.
# Il $RESULT deve essere uno dei seguenti: 1, X (case insensitive), 2.
# Mostrerà anche il budget risultante.
./entry bet $EVENT_ID $RESULT 

# Crea $COUNT (numero) false scommesse tramite il simulatore.
# Mostra gli ID degli eventi prima di farli scegliere!
./entry fake $COUNT

# Restituisce i vari dati richiesti dalla consegna.
# Chiede prima (in maniera interattiva) il codice dell'evento.
# Per favore, controllare che esistano delle scommesse per quell'evento!
./entry analyze

# Chiama `gradle run`, passando $ARGS nella flag `args`.
./entry raw $ARGS
```
