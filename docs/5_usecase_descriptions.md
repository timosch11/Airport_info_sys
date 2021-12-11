# Anwendungsfallbeschreibung

In diesem Dokument sind die Beschreibungen aller Anwendungsfälle des Flughafensystems festgehalten.

## Anwendungsfall 'Personaldaten verwalten'

- **Initiierender Aktor**
  Mitarbeiter

- **Beteiligte Aktoren**
  Keine

- **Vorbedingung**
  Keine

- **Basisablauf**
  Initial sollen alle bereits persistierten Personaldaten ermittelt und dargestellt werden.
  Zudem sollen alle persistierten Berufsbezeichnungen ermittelt und dargestellt werden.
  Neue Personaldaten sollen unter Angabe von Vorname, Nachname und Berufsbezeichnung angelegt werden.
  Ein Personaldatensatz soll nach erfolgreicher Prüfung persistiert werden.
    Der Name soll nicht leer sein.
    Die Berufsbezeichnung soll einer bekannten Berufsbezeichnung entsprechen.
  Im Anschluss soll die Darstellung aller persistierten Personaldaten aktualisiert werden.

- **Nachbedingung**
  Keine

- **Alternativablauf 1**
  Bei nicht erfolgreicher Prüfung sollen die Daten nicht persistiert, aber eine Fehlermeldung mit Nennung der Ursache dargestellt werden.

- **Alternativablauf 2**
  Vorhandene Personaldaten sollen unter Angabe der Identifikationsnummer gelöscht werden.

## Anwendungsfall 'Gebühren verwalten'

- **Initiierender Aktor**
  Mitarbeiter

- **Beteiligte Aktoren**
  Keine

- **Vorbedingung**
  Keine

- **Basisablauf**
  Initial sollen alle persistierten Gebühren ermittelt und dargestellt werden.
  Neue Gebühren sollen unter Angabe des Flugzeugtyps, der Start-, Lande- und Parkgebühren angelegt werden.
  Eine Gebühr soll nach erfolgreicher Prüfung persistiert werden.
    Die Flugzeugtypbezeichung soll nicht leer sein.
    Die Startgebühr soll positiv sein.
    Die Landegebühr soll positiv sein.
    Die Parkgebühr soll positiv sein.
  Im Anschluss soll die Darstellung aller persistierten Flugzeugtypen inkl. Gebühren aktualisiert werden.

- **Nachbedingung**
  Keine

- **Alternativablauf 1**
  Bei nicht erfolgreicher Prüfung sollen die Daten nicht persistiert, aber eine Fehlermeldung mit Nennung der Ursache dargestellt werden.

- **Alternativablauf 2**
  Vorhandene Gebühren sollen unter Angabe der Identifikationsnummer gelöscht werden.

## Anwendungsfall 'Fluggesellschaften verwalten'

- **Initiierender Aktor**
  Mitarbeiter

- **Beteiligte Aktoren**
  Keine

- **Vorbedingung**
  Keine

- **Basisablauf**
  Initial sollen alle persistierten Fluggesellschaften ermittelt und dargestellt werden.
  Neue Fluggesellschaften sollen unter Angabe der Fluggesellschaftbezeichnung angelegt werden.
  Eine Fluggesellschaft soll nach erfolgreicher Prüfung persistiert werden.
    Die Fluggesellschaftbezeichnung soll nicht leer sein.
  Im Anschluss soll die Darstellung aller persistierten Fluggesellschaften aktualisiert werden.

- **Nachbedingung**
  Keine

- **Alternativablauf 1**
  Bei nicht erfolgreicher Prüfung sollen die Daten nicht persistiert, aber eine Fehlermeldung mit Nennung der Ursache dargestellt werden.

- **Alternativablauf 2**
  Vorhandene Gebühren sollen unter Angabe der Identifikationsnummer gelöscht werden.

## Anwendungsfall 'Flugzeuge verwalten'

- **Initiierender Aktor**
  Mitarbeiter

- **Beteiligte Aktoren**
  Keine

- **Vorbedingung**
  Gebühren verwalten, Fluggesellschaften verwalten

- **Basisablauf**
  Initial sollen alle persistierten Flugzeuge ermittelt und dargestellt werden.
  Zudem sollen alle bereits persistierten Fluggesellschaften ermittelt und dargestellt werden.
  Zudem sollen alle bereits persistierten Flugzeugtypen ermittelt und dargestellt werden.
  Neue Flugzeuge sollen unter Angabe der Fluggesellschaft, des Flugzeugtyps und des Luftfahrtkennzeichens angelegt werden.
  Ein Flugzeug soll nach erfolgreicher Prüfung persistiert werden.
    Die Fluggesellschaft soll einer bekannten Fluggesellschaft entsprechen.
    Der Flugzeugtyp soll einem bekannten Flugzeugtyp entsprechen.
    Das Luftfahrtkennzeichen soll aus ein oder zwei Buchstaben gefolgt von einem "-" und vier alphanumerischen Zeichen bestehen (yy-xxxx).
  Im Anschluss soll die Darstellung aller persistierten Flugzeuge aktualisiert werden.

- **Nachbedingung**
  Keine

- **Alternativablauf 1**
  Bei nicht erfolgreicher Prüfung sollen die Daten nicht persistiert, aber eine Fehlermeldung mit Nennung der Ursache dargestellt werden.

- **Alternativablauf 2**
  Vorhandene Gebühren sollen unter Angabe der Identifikationsnummer gelöscht werden.

### Anwendungsfall 'Vorgänge planen'

- **Initiierender Aktor**
  Fluglotse

- **Beteiligte Aktoren**
  Keine

- **Vorbedingung**
  Flugzeuge verwalten

- **Basisablauf**
  Initial sollen alle persistierten Vorgänge ermittelt und dargestellt werden.
  Zudem sollen alle verfügbaren Bahnen ermittelt und dargestellt werden.
  Neue Vorgänge sollen unter Angabe des zu landenden Flugzeugs, einer Bahn und eines Zeitfensters angelegt werden.
  Ein Vorgang soll nach erfolgreicher Prüfung persistiert werden.
    Das Flugzeug soll bekannt sein.
    Die Bahn soll bekannt sein.
    Das Zeitfenster soll in der Zukunft liegen.
    Die Bahn soll in diesem Zeitfenster nicht in einem weiteren Vorgang verplant sein.
    Das Flugzeug soll nicht in einem weiteren Vorgang verplant sein.
    Das Flugzeug soll nicht bereits gelandet bzw. gestartet sein.
  Im Anschluss soll die Darstellung aller persistierten Vorgänge aktualisiert werden.

- **Nachbedingung**
  Keine

- **Alternativablauf**
  Bei nicht erfolgreicher Prüfung sollen die Daten nicht persistiert, aber eine Fehlermeldung mit Nennung der Ursache dargestellt werden.

### Anwendungsfall 'Bahnenauswahl vorfiltern'

- **Initiierender Aktor**

- **Beteiligte Aktoren**

- **Vorbedingung**

- **Basisablauf**

- **Nachbedingung**

- **Alternativablauf**

### Anwendungsfall 'Rechnungen erstellen'

- **Initiierender Aktor**

- **Beteiligte Aktoren**

- **Vorbedingung**

- **Basisablauf**

- **Nachbedingung**

- **Alternativablauf**
