# document-diff-checker

Projekt realizowany w ramach przedmiotu: Projekt Inżynierski II

Temat: Przeszukiwanie dokumentów algorytmami Boyera-Moore’a oraz Knutha-Morrisa-Pratta

Realizowane przez: Wojciech Bać, Dawid Dzień

Opis projektu
Aplikacja służy do porównywania ze sobą dokumentów. Wybieramy dwa dokumenty, dokument wzorcowy, oraz dokument z którym go porównujemy. Dokumenty do porównania wczytujemy z bazy którą jest wybrany przez użytkownika folder. Pliki z wybranego folderu są sortowane algorytmem Boyera-Moora na podstawie podobieństwa z dokumentem wzorcowym, według ich nazwy. Funkcjonalność ta pozwala użytkownikowi na wybranie najbardziej prawdopodobnych dokumentów do porównywania. Pliki z wybranej bazy ładują się do listy umożliwiającej przełączanie się między porównywanym dokumentem. Treść pliku wzorcowego i porównywanego są ładowane w postaci tekstowej i wyświetlane użytkownikowi. Gdy obydwa pliki zostaną wybrane następuje porównywanie ich treści, według korespondujących w nich linii, porównanie odbywa się za pomocą algorytmu Knutha-Morrisa Pratta. Na ekranie są zaznaczone kolorem zielonym linie, które są zgodne w obu dokumentach a kolorem czerwonym linie które nie są identyczne. Użytkownik może również porównać dokumenty według zawartych w nich słów oraz zdań, otrzymując konkretną informacje w miejscu ich wystąpienia w każdym z dokumentów.


Wykorzystane technologie:  
Środowisko programistyczne - IntelliJ IDEA  
Język programowania - Java, biblioteka JavaFX  
Narzędzie do zarządzania bibliotekami - Maven  
Narzędzie kontroli wersji - Git  
Prywatna tablica – Trello

