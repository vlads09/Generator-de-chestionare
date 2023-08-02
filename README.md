# Generator-de-chestionare

Utilizatorii acestui program se vor autentifica în aplicație la orice apel din sistem (pentru simplificare), în afară de acela de creare de
utilizator. Utilizatorii autentificați în sistem vor putea: crea întrebări (cu un singur răspuns corect, sau cu
mai multe răspunsuri corecte), crea chestionare în baza întrebărilor adăugate anterior și vor putea
răspunde chestionarelor celorlalți, doar o singură dată.
	Pentru fiecare comanda primită, printez mesajul de eroare daca parametrii primiți in comandă sunt corecți, altfel se apelează metodele specifice comenzii, 
câteva pe care le voi detalia in cele ce urmează:
1. Crearea utilizatorului – în momentul în care un nou utilizator vrea să preia un nume care deja aparține altuia, se printează eroare. Dacă crearea a avut succes, datele legate de utilizator sunt stocate in fișierul text ”users.txt”.
2. Crearea întrebării – dacă răspunsurile au fost furnizate cum trebuie, argumentele le salvez într-o listă de răspunsuri. Trebuie verificat mai întâi dacă nu s-a dat doar un răspuns si daca prezintă toate detaliile necesare. În lista de răspunsuri, merg din doi în doi, deoarece pe o poziție se află descrierea și pe următoarea valoarea de adevăr. Știu că începutul este de forma ”-answer-i”, unde i reprezintă al câtelea răspuns este. Astfel, verific mai departe cu ajutorul listei dacă un răspuns a mai fost scris o dată, dacă sunt suficiente răspunsuri corecte in raport cu tipul întrebării. Dacă nu au existat erori, atunci se va crea cu succes o întrebare ce va fi stocată în fișierul ”questions.txt” in formatul cerut.
3. Preluarea textului unei întrebări după id – dacă id-ul există în fișier, afișez textul, altfel, eroare. 
4. Preluarea tuturor întrebărilor – dacă nu sunt probleme, vor fi afișate din fișierul ”questions.txt” numele și id-ul fiecărei întrebări.
5. Crearea chestionarului – se verifică prin calculul lungimii argumentelor date dacă sunt mai mult de zece întrebări date, afișând o eroare. Vor fi salvate
întrebările ce conțin id-ul într-o listă și se verifică dacă id-ul există. Chestionarul se va crea cu succes și informațiile vor fi salvate în fișierul ”quizzes.txt”, în formatul cerut.
6. Preluarea unui chestionar după nume – această comandă este asemănătoare comenzii cu numărul 3, doar că de data asta parametrul este numele și se va afișa id-ul 
dacă a fost găsit cu succes.
7. Preluarea tuturor chestionarelor – se vor afișa numele și id-ul fiecărui chestionar + dacă a fost făcut sau nu de utilizator parcurgând fișierul de încărcării
ale chestionarelor ”submissions.txt”, explicat la comanda cu numărul 9.
8. Preluarea detaliilor unui chestionar – aceeași verificare de început, urmând să fie căutat, după id, quiz-ul și va fi parcurs pe linia din fișier, id-ul
întrebărilor astfel încât să preiau detaliile răspunsurilor din fișierul ”questions.txt”.
9. Încărcarea chestionarului – dacă utilizatorul își răspunde la propriul chestionar, se printează o eroare. Pentru a verifica răspunsurile date de utilizator
dacă sunt corecte sau nu, parcurg toate răspunsurile din chestionar care au un id fiecare și în funcție de id, verific dacă este corect sau nu răspunsul. Pentru punctaj, fiecare întrebare are punctajul egal cu raportul dintre punctajul maxim și numărul de întrebări. Pentru o întrebare de tip ”single”, răspunsul corect valorează punctajul întrebării, iar cel greșit punctajul negativ. Pentru tipul ”multiple”, răspunsul corect valorează raportul dintre punctajul întrebării și al numărului de răspunsuri corecte la întrebare, iar cel greșit este raportul dintre punctajul întrebării și al numărului de răspunsuri greșite, sub formă negativă. Rezultatul unui chestionar este însumarea punctajelor și la final, se va scrie în ”submissions.txt”, datele necesare.
10. Ștergerea chestionarului – caut linia în fișier având datele chestionarului și pentru a o șterge, creez un alt fișier în care scriu restul liniilor. 
Șterg fișierul vechi și pe cel nou îl redenumesc cu numele anterior fișierului șters.
11. Preluarea soluțiilor – cu ajutorul numelui utilizatorului, caut în ”submissions.txt” fiecare apariție a utilizatorului și voi afișa numele chestionarului, id-ul
acestuia și rezultatul obținut în urma încărcării.
