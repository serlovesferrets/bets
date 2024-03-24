#r "nuget: Fli"

open Fli
open System.Text

let args = System.Environment.GetCommandLineArgs()

if Array.length args < 3 then
    failwith "Devi passare almeno un argomento!"

let command = args[2]

let bets =
    [ [ "4562"; "first-event"; "02032024"; "0912"; "200"; "150"; "250" ]
      [ "1254"; "second-event"; "06042023"; "0815"; "150"; "400"; "145" ]
      [ "7896"; "third-event"; "11252025"; "1030"; "300"; "250"; "175" ]
      [ "2357"; "fourth-event"; "07212024"; "1345"; "100"; "300"; "200" ]
      [ "7412"; "fifth-event"; "03152024"; "1000"; "250"; "200"; "300" ]
      [ "3698"; "sixth-event"; "09012023"; "1430"; "400"; "350"; "100" ] ]


let addBetData args =
    let join = String.concat " "

    cli {
        Shell BASH
        Command $"gradle run --args='add-bet {join args}'"
    }
    |> Command.execute
    |> Output.toText

let cleanBetData () =
    cli {
        Shell BASH
        Command "gradle run --args='clean'"
    }
    |> Command.execute
    |> Output.throwIfErrored
    |> Output.toText

let showBets () =
    cli {
        Shell BASH
        Command "gradle run --args='show-bets'"
    }
    |> Command.execute
    |> Output.throwIfErrored
    |> Output.toText

let result =
    match command.ToLower() with
    | "add-single-bet" -> addBetData bets[0]
    | "add-bet" -> bets |> List.map addBetData |> String.concat "\n---\n"
    | "show-bets" -> showBets ()
    | "clean-bets" -> cleanBetData ()
    | _ -> failwith "Comando non riconosciuto!"

printfn $"{result}"
