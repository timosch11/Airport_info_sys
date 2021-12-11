# Anforderungen

In diesem Dokument sind die Anforderungen an das Flughafensystem festgehalten.

## Toplevel-Anforderung

Durch das Flughafensystem sollen Fluglotsen Lande- und Startvorgänge auch nach der Migration auf Windows 10 und mit demselben Zeitaufwand wie dem Altsystem planen und überwachen können. Zudem sollen Angestellte der Flughafenverwaltung Start- und Landegebühren für die geplanten Vorgänge erheben können, sodass die Informationen nicht mehr in getrennten Systemen gepflegt werden müssen.

## Funktionale Anforderungen

### Anwendungsfall 'Personaldaten verwalten'

* Anforderung 1000 Ein Mitarbeiter soll die Namen von neuen Mitarbeitern eingeben können.
* Anforderung 1010 Ein Mitarbeiter soll die Beschäftigungsart von neuen Mitarbeitern festlegen können.
* Anforderung 1020 Ein Mitarbeiter soll nur "Fluglotse" oder "Angestellter der Flughafenverwaltung" als Beschäftigungsart eingeben können.
* Anforderung 1030 Ein Mitarbeiter soll Mitarbeiter löschen können.

### Anwendungsfall 'Gebühren verwalten'

* Anforderung 1100 Ein Mitarbeiter soll neue Flugzeugtypen registrieren können.
* Anforderung 1110 Ein Mitarbeiter soll die Bezeichnung eines Flugzeugtyps angeben können.
* Anforderung 1120 Ein Mitarbeiter soll die Start-, Lande- und Parkgebühren für ein Flugzeugtyp angeben können.
* Anforderung 1130 Ein Mitarbeiter soll nur positive Start-, Lande-und Parkgebühren angeben können.
* Anforderung 1140 Ein Mitarbeiter soll Flugzeugtypen löschen können.

### Anwendungsfall 'Fluggesellschaften verwalten'

* Anforderung 1200 Ein Mitarbeiter soll neue Fluggesellschaften registrieren können.
* Anforderung 1210 Ein Mitarbeiter soll die Bezeichnung einer Fluggesellschaft angeben können.
* Anforderung 1220 Ein Mitarbeiter soll Fluggesellschaften löschen können.

### Anwendungsfall 'Flugzeuge verwalten'

* Anforderung 1300 Ein Mitarbeiter soll neue Flugzeuge registrieren können.
* Anforderung 1310 Ein Mitarbeiter soll die betreibende Fluggesellschaft für neue Flugzeuge angeben können.
* Anforderung 1320 Ein Mitarbeiter soll den Flugzeugtyp für neue Flugzeuge angeben können.
* Anforderung 1330 Ein Mitarbeiter soll das eindeutige Luftfahrzeugkennzeichen für neue Flugzeuge angeben können.
* Anforderung 1340 Ein Mitarbeiter soll nur Luftfahrzeugkennzeichen angeben können die ein oder zwei Buchstaben gefolgt von einem "-" und vier alphanumerischen Zeichen (yy-xxxx) aufweisen.
* Anforderung 1350 Ein Mitarbeiter soll Flugzeuge löschen können.

### Anwendungsfall 'Vorgänge planen'

* Anforderung 2000 Ein Fluglotse soll bei einer Landung sich selbst als betreuenden Lotsen angeben können.
* Anforderung 2010 Ein Fluglotse soll bei einer Landung das zu landende Flugzeug angeben können.
* Anforderung 2020 Ein Fluglotse soll bei einer Landung ein Zeitfenster angeben können.
* Anforderung 2030 Ein Fluglotse soll bei einer Landung eine Bahn angeben können.
* Anforderung 2040 Ein Fluglotse soll keine Bahn angeben können, die in dem Zeitfenster belegt ist.
* Anforderung 2100 Ein Fluglotse soll bei einem Start sich selbst als betreuenden Lotsen angeben können.
* Anforderung 2110 Ein Fluglotse soll bei einem Start das zu startende Flugzeug angeben können.
* Anforderung 2120 Ein Fluglotse soll bei einem Start ein Zeitfenster angeben können.
* Anforderung 2130 Ein Fluglotse soll bei einem Start eine Bahn angeben können.
* Anforderung 2140 Ein Fluglotse soll keine Bahn angeben können, die in dem Zeitfenster belegt ist.

### Anwendungsfall 'Bahnenauswahl vorfiltern'

* Anforderung 3000 Ein Fluglotse soll ein Zeitfenster angeben können.
* Anforderung 3010 Ein Fluglotse soll, beim Planen der Landevorgänge, nur freie Bahnen in dem angegeben Zeitfenster einsehen können.
* Anforderung 3020 Ein Fluglotse soll, beim Planen der Startvorgänge, nur freie Bahnen in dem angegeben Zeitfenster einsehen können.

### Anwendungsfall 'Rechnungen erstellen'

* Anforderung 4000 Ein Angestellter der Flughafenverwaltung soll die Summe aller Gebühren für alle Vorgänge je Fluggesellschaft eines Monats angezeigt bekommen.

## Nichtfunktionale Anforderungen

* Anforderung 9000 Die Zuverlässigkeit soll so gut wie die des Altsystems sein.
* Anforderung 9010 Die Benutzungsoberfläche soll intuitiv sein.
* Anforderung 9100 Fehler in der Bedienung sollen an die Anwender gemeldet werden.
* Anforderung 9110 Fehler der Software sollen an die Anwender gemeldet werden.
* Anforderung 9200 Das System soll unabhängig vom Betriebssystem betrieben werden können.
* Anforderung 9210 Das System soll mit Spring Boot realisiert werden.
* Anforderung 9220 Das System soll die Daten über mehrere Sitzungen hinweg persistent speichern.
* Anforderung 9300 Das System kann so gestaltet sein, dass die Anzahl von vier Bahnen fest hinterlegt ist.
