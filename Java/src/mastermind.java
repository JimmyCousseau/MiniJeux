package src;
import java.util.Scanner;

public class mastermind {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Essai de trouver la combinaison à 4 couleurs différentes, tu as jusqu'au tour 8");
        System.out.println("R : Rouge     V : Vert    B : Bleu    N : Noir    O : Orange  G : Gris    M : Marron  J : Jaune");
        Elements e = new Elements();
        e = couleur();
        turn(e);
    }

    public static class Elements {
        int nb_couleur;
        char[] couleur = new char[4];
        String guess_color;
        int find_well;
        int find_wrong;
        int nb_turn;
    }

    
    /** 
     * @param e
     */
    public static void turn(Elements e) {
        Scanner enter = new Scanner(System.in);
        e.guess_color = enter.nextLine();
        verify(e);
        enter.close();
    }

    
    /** 
     * @param e
     */
    public static void verify(Elements e) {
        e.find_well = 0;
        e.find_wrong = 0;
        for (int i = 0; i < 4; i++) {
            /* System.out.println(e.guess_color.charAt(i) == e.couleur[i].charAt(i));
            System.out.println(e.guess_color.charAt(i));
            System.out.println(e.couleur[i].charAt(i)); */
            if (e.guess_color.charAt(i) == e.couleur[i]) {
                e.find_well += 1;
            }
        }
        if (e.find_well == 4) {
            victory(e);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (e.guess_color.charAt(i) == e.couleur[j] && i != j) {
                    e.find_wrong += 1;
                }
            }
        }
        e.nb_turn++;
        System.out.println("Tour numéro : " + e.nb_turn);
        System.out.println("Bien placé : " + e.find_well + "      Mal placées : " + e.find_wrong);
        if (e.nb_turn == 8) {
            lose(e);
        }
        else {
            turn(e);
        }
    }

    
    /** 
     * @param e
     */
    public static void victory(Elements e) {
        System.out.println(
                "Bravo tu as gagné !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.exit(0);
    }

    
    /** 
     * @param e
     */
    public static void lose(Elements e){
        System.out.println("Tu as perdu, la combinaison était :");
        System.out.println(e.couleur[0] + " " + e.couleur[1] + " " + e.couleur[2] + " " + e.couleur[3] + " ");
        System.exit(0);
    }

    
    /** 
     * Retourne la couleur des pions en fonction du nombre tiré
     * @return Elements
     */
    public static Elements couleur() {
        Elements e = new Elements();
        int[] prev_couleur = new int[4];
        for (int i = 0; i < 4; i++) {
            do {
                e.nb_couleur = (int) (Math.random() * 8);
            } while (e.nb_couleur == prev_couleur[0] || e.nb_couleur == prev_couleur[1]
                    || e.nb_couleur == prev_couleur[2] || e.nb_couleur == prev_couleur[3]);
            prev_couleur[i] = e.nb_couleur;

            switch (e.nb_couleur) {
                case 1:
                    e.couleur[i] = 'R';
                    break;
                case 2:
                    e.couleur[i] = 'B';
                    break;
                case 3:
                    e.couleur[i] = 'O';
                    break;
                case 4:
                    e.couleur[i] = 'G';
                    break;
                case 5:
                    e.couleur[i] = 'V';
                    break;
                case 6:
                    e.couleur[i] = 'N';
                    break;
                case 7:
                    e.couleur[i] = 'J';
                    break;
                default:
                    e.couleur[i] = 'M';
                    break;
            }
        }
        System.out.println("Le jeu commence");
        e.nb_turn = 0;
        return e;
    }
}
