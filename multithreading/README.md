# Esempi Multithreading

Questo archivio zip contiene codice di esempio da cui si può prendere spunto
per capire le best-practices rispetto a come combinare multithreading e rete.

Ci sono 3 esempi, di complessità crescente, che implementano il semplice gioco
"Mastermind". L'utente deve scoprire un numero di 5 cifre in base al feedback
fornito dal computer. Se il numero non è corretto, il computer risponde con
tanti '+' quante cifre nel numero indovinato sono più basse del numero da
indovinare, e tanti '-' quante cifre sono più alte.

- **0_simple-server**
  
  Applicazione base, server supporta solo una partita contemporanea.
  
- **1_multi-client-server**
  
  '0_simple-server' modificata per servire più partite contemporanee.
  
- **2_client-with-bg-task**
  
  '1_multi-client-server' modificata per dimostrare architetture del client
  con thread multipli.
  
Due esempi aggiuntivi dimostrano l'utilizzo delle classi 
`ScheduledExecutorServer` e `Future<>`. Questi esempi non implementano il gioco
di esempio "Mastermind".
  
- **3_advanced-techs-1**

  Applicazione che dimostra l'utilizzo di `ScheduledExecutorService`.
  
- **4_advanced-techs-2**

  Applicazione che dimostra l'utilizzo di `Future<>`.

## Note conclusive:

- Tutti gli esempi fanno uso di `ObjectOutputStream` e `ObjectInputStream` per
  gestire la serializzazione nel protocollo di rete. 
  
  Se volete usare dei protocolli a base testuale,
  **non dovete usare `ObjectOutputStream` e `ObjectInputStream`** ma 
  `OutputStreamWriter`+`BufferedWriter` e `InputStreamReader`+`BufferedReader`.

- Questi non sono esempi di progetti completi, sono state fatte alcune
  semplificazioni per limitare il numero di righe di codice, specialmente nel
  client.
