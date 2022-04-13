package it.polimi.ingsw.am19.Model.CharacterCards;

/**
 * Enum for the 12 characters card, with price and a description of the effect that they cause
 */
public enum Character {
    MONACO(1,"Prendi 1 studente dalla carta e piazzalo su un'isola a tua scelta. Poi, pesca 1 studente dal sacchetto e rimettilo su questa carta"),
    CONTADINO(2, "Durante questo turno, prendi il controllo dei Professori anche se nella tua sala hai lo stesso numero di Studenti del giocatore che li controlla in quel momento"),
    ARALDO(3,"Scegli un'isola e calcola la maggioranza come se Madre Natura avesse terminato il suo movimento lì. In questo turno Madre Natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata"),
    POSTINO_MAGICO(1,"Puoi muovere Madre Natura fino a 2 Isole addizionali rispetto a quanto indicato sulla carta Assistente che hai giocato."),
    NONNA_ERBE(2,"Piazza una tessera Divieto su un'Isola a tua scelta. La prima volta che Madre Natura termina il suo movimento li, rimettete la tessera Divieto sulla carta SENZA calcolare l'influenza su quell'Isola né piazzare Torri."),
    CENTAURO(3,"Durante il conteggio dell'influenza su un'isola (o su un gruppo di Isole), le Torri presenti non vengono calcolate."),
    GIULLARE(1,"Puoi prendere fino a 3 Studenti da questa carta e scambiarli con altrettanti Studenti presenti nel tuo Ingresso."),
    CAVALIERE(2,"In questo turno, durante il calcolo dell'influenza hai 2 punti di influenza addizionali."),
    FUNGARO(3,"Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza."),
    MENESTRELLO(1,"Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso."),
    PRINCIPESSA_VIZIATA(2,"Prendi 1 Studente da questa carta e piazzalo nella tua Sala. Poi pesca un nuovo Studente dal sacchetto e posizionalo su questa carta."),
    LADRO(3,"Scegli un colore di Studente; ogni giocatore (incluso te) deve rimettere nel sacchetto 3 Studenti di quel colore presenti nella sua Sala. Chi avesse meno di 3 Studenti di quel colore, rimetterà tutti quelli che ha.");

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    private final int price;
    private final String description;

    Character(int price, String description){
        this.price=price;
        this.description=description;
    }
}

