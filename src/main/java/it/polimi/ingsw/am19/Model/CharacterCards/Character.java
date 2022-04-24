package it.polimi.ingsw.am19.Model.CharacterCards;

/**
 * Enum for the 12 characters card, with price and a description of the effect that they cause
 */
public enum Character {
    MONACO(1,"Prendi 1 studente dalla carta e piazzalo su un'isola a tua scelta. Poi, pesca 1 studente dal sacchetto e rimettilo su questa carta",true,true,false),
    CONTADINO(2, "Durante questo turno, prendi il controllo dei Professori anche se nella tua sala hai lo stesso numero di Studenti del giocatore che li controlla in quel momento",false,false,false),
    ARALDO(3,"Scegli un'isola e calcola la maggioranza come se Madre Natura avesse terminato il suo movimento lì. In questo turno Madre Natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata",false, true,false),
    POSTINO_MAGICO(1,"Puoi muovere Madre Natura fino a 2 Isole addizionali rispetto a quanto indicato sulla carta Assistente che hai giocato.", false, false, false),
    NONNA_ERBE(2,"Piazza una tessera Divieto su un'Isola a tua scelta. La prima volta che Madre Natura termina il suo movimento lì, rimettete la tessera Divieto sulla carta SENZA calcolare l'influenza su quell'Isola né piazzare Torri.",false,true,false),
    CENTAURO(3,"Durante il conteggio dell'influenza su un'isola (o su un gruppo di Isole), le Torri presenti non vengono calcolate.",false,false,false),
    GIULLARE(1,"Puoi prendere fino a 3 Studenti da questa carta e scambiarli con altrettanti Studenti presenti nel tuo Ingresso.",false,false,true),
    CAVALIERE(2,"In questo turno, durante il calcolo dell'influenza hai 2 punti di influenza addizionali.",false,false,false),
    FUNGARO(3,"Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza.",true,false,true),
    MENESTRELLO(1,"Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.",false,false,true),
    PRINCIPESSA_VIZIATA(2,"Prendi 1 Studente da questa carta e piazzalo nella tua Sala. Poi pesca un nuovo Studente dal sacchetto e posizionalo su questa carta.",true,false,false),
    LADRO(3,"Scegli un colore di Studente; ogni giocatore (incluso te) deve rimettere nel sacchetto 3 Studenti di quel colore presenti nella sua Sala. Chi avesse meno di 3 Studenti di quel colore, rimetterà tutti quelli che ha.",true,false,false);

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequiringPieceColor() {
        return requiringPieceColor;
    }

    public boolean isRequiringIsland() {
        return requiringIsland;
    }

    public boolean isRequiringPieceColorList() {
        return requiringPieceColorList;
    }

    private final int price;
    private final String description;
    private final boolean requiringPieceColor;
    private final boolean requiringIsland;
    private final boolean requiringPieceColorList;

    Character(int price, String description, boolean requirePieceColor, boolean requireIsland, boolean requirePieceColorList){
        this.price=price;
        this.description=description;
        this.requiringPieceColor=requirePieceColor;
        this.requiringIsland=requireIsland;
        this.requiringPieceColorList=requirePieceColorList;
    }
}

