#r "nuget: Fli"

open Fli
open System.Text

let args = System.Environment.GetCommandLineArgs()

if Array.length args < 3 then
    failwith "Devi passare almeno un argomento!"

let command = args[2]

let events =
    [ [ "4562"; "first-event"; "02032024"; "0912"; "200"; "150"; "250" ]
      [ "1254"; "second-event"; "06042023"; "0815"; "150"; "400"; "145" ]
      [ "7896"; "third-event"; "11252025"; "1030"; "300"; "250"; "175" ]
      [ "2357"; "fourth-event"; "07212024"; "1345"; "100"; "300"; "200" ]
      [ "7412"; "fifth-event"; "03152024"; "1000"; "250"; "200"; "300" ]
      [ "3698"; "sixth-event"; "09012023"; "1430"; "400"; "350"; "100" ] ]


let bets =
    [ [ "1254"; "marzianito"; "50"; "X" ]
      [ "3698"; "bingus-meow"; "25"; "1" ]
      [ "7412"; "dame-tu-cosita"; "75"; "2" ]
      [ "2357"; "feliz-navidad"; "30"; "X" ]
      [ "2357"; "mina"; "40"; "2" ]
      [ "4562"; "chatgpt"; "60"; "2" ]
      [ "7896"; "simone-ciniltani"; "90"; "1" ]
      [ "4562"; "franceschino"; "55"; "1" ] ]

let addEventData args =
    let join = String.concat " "

    cli {
        Shell BASH
        Command $"gradle run --args='add-event {join args}'"
    }
    |> Command.execute
    |> Output.toText

let addBetData args =
    let join = String.concat " "

    cli {
        Shell BASH
        Command $"gradle run --args='add-bet {join args}'"
    }
    |> Command.execute
    |> Output.toText

let cleanEventData () =
    cli {
        Shell BASH
        Command "gradle run --args='clean-events'"
    }
    |> Command.execute
    |> Output.throwIfErrored
    |> Output.toText

let cleanBetData () =
    cli {
        Shell BASH
        Command "gradle run --args='clean-bets'"
    }
    |> Command.execute
    |> Output.throwIfErrored
    |> Output.toText

let showEvents () =
    cli {
        Shell BASH
        Command "gradle run --args='show-events'"
    }
    |> Command.execute
    |> Output.throwIfErrored
    |> Output.toText

let result =
    let separator = "\n---\n"

    match command.ToLower() with
    | "add-event" -> addEventData events[0]
    | "add-events" -> events |> List.map addEventData |> String.concat separator
    | "add-bet" -> addBetData bets[0]
    | "add-bets" -> bets |> List.map addBetData |> String.concat separator
    | "show-events" -> showEvents ()
    | "clean-events" -> cleanEventData ()
    | "clean-bets" -> cleanBetData ()
    | "clean" -> [ cleanEventData (); cleanBetData () ] |> String.concat separator
    | _ -> failwith "Comando non riconosciuto!"

printfn $"{result}"
