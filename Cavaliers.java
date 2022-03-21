import scite.*;

public class Cavaliers {
    public static void main(String[] args) {
        Piece e = definitions();
        plateau(e);
    }

    static class Piece {
        // L'emplacement des pièges
        int vague;
        int trou1;
        int trou2;
        int mortOuVif;
        ////////////////////////////////
        int nb_cavalier;
        int[] totaldéesJ = { 0, 0, 0, 0 }; // Stock l'avancement du cavalier (language Joueur)
        int[] totaldéesP = { 0, 0, 0, 0 }; // Stock l'avancement du cavalier (language Plateau)
        int dées; // Les dées tirés
        int[] attente = { 0, 0, 0, 0 }; // Stock l'attente d'un cavalier avant de pouvoir jouer si il est tombé sur un
                                        // trou
        int turn; // Défini le tour pour savoir à qui c'est de jouer
        int totalturn; // Stock le nombre de tours pour savoir si le cavalier a gagné ou pas (mode
                       // solo)
        // défini un caractère pour chaques cavaliers
        String[] lettre = { "\u001B[31m" + "R" + "\u001B[0m", "\u001B[34m" + "B" + "\u001B[0m",
                "\u001B[32m" + "V" + "\u001B[0m", "\u001B[33m" + "J" + "\u001B[0m" };
        int taille; // Taille du plateau
    }

    static Piece definitions() {
        Piece e = new Piece();
        do {
            Ecran.afficherln("Voulez-vous jouer à 1, 2, 3 ou 4 joueurs ?");
            e.nb_cavalier = Clavier.saisirInt();
        } while (e.nb_cavalier < 1 || e.nb_cavalier > 4);
        do {
            Ecran.afficherln("Quelle dimension du plateau voulez-vous (entre 6 et 10)");
            e.taille = Clavier.saisirInt();
        } while (e.taille < 6 || e.taille > 10);
        e.vague = (int) (Math.random() * (e.taille - 2) + 1);
        e.mortOuVif = (int) (Math.random() * (e.taille - 2) + e.taille * 3 - 3);
        do {
            e.trou1 = (int) (Math.random() * (2 * (e.taille - 2)) + e.taille + 1);
            e.trou2 = (int) (Math.random() * (2 * (e.taille - 2)) + e.taille + 1);
        } while (e.trou1 == e.trou2);
        e.turn = 0;
        return e;
    }

    static void plateau(Piece e) {
        // Stocke chaques cases dans une liste pour pouvoir les identifiers
        String[] haut = new String[e.taille * 4 - 4];
        for (int i = 0; i < haut.length; i++) {
            haut[i] = "___";
        }
        ////////////////////////////////
        haut[e.taille] = "_A_"; // Point à atteindre pour finir la course
        haut[0] = "_D_"; // Point de départ de la course
        // Place les pièges
        haut[e.vague] = "_~_";
        haut[e.trou1] = "_O_";
        haut[e.trou2] = "_O_";
        haut[e.mortOuVif] = "OXO";
        ////////////////////////////////

        for (int i = 0; i < e.nb_cavalier; i++) {
            haut[e.totaldéesP[i]] = "_" + e.lettre[i] + "_";
        }
        ////////////////////////////////

        // L'affichage du reste du plateau avec les variables prisent en compte
        // Pour faire la ligne du haut du plateau :
        Ecran.afficher(" ");
        for (int i = 0; i <= e.taille * 4 - 2; i++) {
            Ecran.afficher("_");
        }
        Ecran.afficherln("");
        ////////////////////////////////
        for (int i = 0; i < haut.length; i++) {
            if (i >= e.taille && i < (e.taille - 3) * 2 + e.taille) {
                if (i == e.taille) {
                    Ecran.afficher("|");
                }
                Ecran.afficherln("");
                Ecran.afficher("|" + haut[i] + "|");
                for (int x = 0; x <= e.taille * 4 - 10; x++) {
                    Ecran.afficher(" ");
                }
                i++;
                Ecran.afficher("|" + haut[i] + "|");

            } else if (i == (e.taille - 3) * 2 + e.taille) {
                Ecran.afficherln("");
                Ecran.afficher("|" + haut[i] + "|");
                for (int x = 0; x <= e.taille * 4 - 10; x++) {
                    Ecran.afficher("_");
                }
                i++;
                Ecran.afficher("|" + haut[i] + "|");
                Ecran.afficherln("");
            } else {
                Ecran.afficher("|" + haut[i]);
            }
        }
        Ecran.afficher("|");
        askToPlay(e);
    }

    static void askToPlay(Piece e) {
        if (e.nb_cavalier == 1) {
            Ecran.afficherln("\nÀ ton tour");
        } else {
            Ecran.afficherln("\nAu tour de " + e.lettre[e.turn]);
        }
        Ecran.afficherln("\n\nAppuyez sur un caractère puis entrez pour lancer les dés");
        Ecran.afficherln("Si vous souhaitez arrêter la partie saisissez s");
        char valid = Clavier.saisirChar();
        if (valid != 's') {
            e.dées = (int) (Math.random() * 6 + 1); // Tire les dés
            Ecran.afficherln("Dées tiré : " + e.dées);
            conditions(e);
        } else {
            Ecran.afficherln("Jeu arrêté");
            System.exit(0);
        }
    }

    static void conditions(Piece e) {
        
        // Vérification si le cavalier est tombé dans un trou ou non
        if (e.totaldéesP[e.turn] == e.trou1 || e.totaldéesP[e.turn] == e.trou2) {
            if (e.attente[e.turn] != 2 && e.attente[e.turn] == 0) {
                e.attente[e.turn] = 2;
                Ecran.afficherln("\u001B[31m" + "Veuillez attendre " + e.attente[e.turn] + " tours," + "\u001B[0m");
            } else {
                e.attente[e.turn] -= 1;
                if (e.attente[e.turn] != 0) {
                    Ecran.afficherln("\u001B[31m" + "Veuillez attendre " + e.attente[e.turn] + " tours," + "\u001B[0m");
                } else {
                    Ecran.afficherln("\u001B[32m" + "Vous êtes enfin libéré" + "\u001B[0m");
                    e.totaldéesJ[e.turn] += e.dées; // Stocke la somme des dées tirés tout au long de la partie pour
                                                    // situer
                }
            }
        }
        ////////////////////////////////
        // Si un cavalier tombe sur la case mortOuVif
        else if (e.totaldéesP[e.turn] == e.mortOuVif) {
            if (e.dées % 2 == 0) {
                Ecran.afficherln("\u001B[32m"
                        + "Vous êtes tombé sur la case Mort ou Vif\nEt vous avez fait un nombre pair, vous multipliez donc\nvotre vitesse par 2 pour ce tour-ci"
                        + "\u001B[0m");
                e.dées *= e.dées;
                e.totaldéesJ[e.turn] += e.dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
            } else {
                Ecran.afficherln("\u001B[31m"
                        + "Vous êtes tombé sur la case Mort ou Vif\nEt vous avez fait un nombre impair\nRetournez à la case départ"
                        + "\u001B[0m");
                e.totaldéesJ[e.turn] = 0;
            }
        }
        ////////////////////////////////
        // Si un cavalier tombe sur la rivière
        else if (e.totaldéesP[e.turn] == e.vague) {
            Ecran.afficherln(
                    "Vous êtes dans la rivière, vous ne pouvez pas sortir tant que vous ne faites pas un chiffre pair");
            if (e.dées % 2 == 0) {
                Ecran.afficherln(
                        "\u001B[32m" + "Vous pouvez sortir de la rivière, vous avez fait un nombre pair" + "\u001B[0m");
                e.totaldéesJ[e.turn] += e.dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
            } else {
                Ecran.afficherln("\u001B[31m"
                        + "Vous n'avez pas fait de nombre pair, vous restez bloqué dans la rivière" + "\u001B[0m");
            }
        }
        ////////////////////////////////
        else {
            e.totaldéesJ[e.turn] += e.dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
        }
        // Si un cavalier tombe sur un autre cavalier il recule
        for (int i = 0; i <= e.nb_cavalier - 1; i++) {
            if (e.totaldéesJ[e.turn] == e.totaldéesJ[i] && i != e.turn) {
                e.totaldéesJ[e.turn] -= e.taille / 2;
                Ecran.afficherln("\u001B[31m" + e.lettre[e.turn] + " est tombé sur " + e.lettre[i]
                        + " ,\nIl recule donc de " + e.taille / 2 + "\u001B[0m");
                if (e.totaldéesJ[e.turn] < 1) {
                    e.totaldéesJ[e.turn] = 1;
                }
            }
        }
        ////////////////////////////////
        change(e);
    }

    static void change(Piece e) {
        // Vérifie si le cavalier est sur la case d'arrivée
        if (e.totaldéesP[e.turn] == e.taille) {
            if (e.nb_cavalier == 1) {
                if (e.totalturn <= e.taille) {
                    Ecran.afficherln(
                            "\n\n\nFin de la partie,\nBravo !\nTu as parcouru le plateau en moins de tours que la dimension du plateau !\n\n\n");
                    System.exit(0);
                } else {
                    Ecran.afficherln(
                            "\n\n\nFin de la partie,\nPerdu  :(\nTu as parcouru le plateau en faisant plus de tours que la dimension du plateau !\n\n\n");
                    System.exit(0);
                }
            } else {
                Ecran.afficherln("\n\n\nFin de la partie le joueur " + (e.turn + 1) + " a gagné !\n\n\n");
                System.exit(0);
            }
        } 
        ////////////////////////////////
        // Si n'est pas sur case d'arrivée, il recule
        else if (e.totaldéesJ[e.turn] > e.taille * 4 - 5) {
            int b = e.taille * 4 - 5;
            e.totaldéesJ[e.turn] = 2 * b - e.totaldéesJ[e.turn];
        }
        ////////////////////////////////

        
        remisePlateau(e); // Remet le plateau dans l'ordre des chiffres, sens horaire.


        if (e.totaldéesP[e.turn] == e.mortOuVif) {
            Ecran.afficherln("\u001B[31m"
                    + "Vous êtes tombé sur la case Mort ou Vif, au prochain tour si vous faites un nombre pair, vous multiplierez votre vitesse par trois, sinon vous retournez à la case départ"
                    + "\u001B[0m");
        }
        if (e.totaldéesP[e.turn] == e.vague) {
            Ecran.afficherln("\u001B[31m"
                    + "Vous êtes tombé dans la rivière, si vous faites un nombre pair au prochain tour, vous pourrez sortir"
                    + "\u001B[0m");
        }
        if (e.totaldéesP[e.turn] == e.trou1 || e.totaldéesP[e.turn] == e.trou2) {
            Ecran.afficherln("\u001B[31m" + "Vous êtes tombé dans la fosse" + "\u001B[0m");
        }
        // Fait la transition entre les cavaliers
        if (e.turn >= e.nb_cavalier - 1) {
            e.turn = 0;
        } else if (e.turn < e.nb_cavalier - 1) {
            e.turn += 1;
            e.totalturn += 1;
        }
        plateau(e);
    }

    static void remisePlateau(Piece e) {
        // Le plateau n'a pas le même sens de lecture que nous (le sens horaire), nous
        // avons donc
        // repris tous les numéros de cases pour les remettres dans le bonne ordre
        ////////////////////////////////
        e.totaldéesP[e.turn] = e.totaldéesJ[e.turn];
        // Côté droit du plateau
        if (2 * e.taille - 2 > e.totaldéesJ[e.turn] && e.totaldéesJ[e.turn] >= e.taille) {
            for (int i = 0; i <= e.taille; i++) {
                if (e.totaldéesJ[e.turn] == e.taille + i) {
                    e.totaldéesP[e.turn] = (e.totaldéesP[e.turn] - e.taille) * 2 + e.taille + 1;
                    i = e.taille + 1;
                }
            }
        }
        ////////////////////////////////
        // Côté bas du plateau
        else if (3 * e.taille - 3 > e.totaldéesJ[e.turn] && e.totaldéesJ[e.turn] >= 2 * e.taille - 2) {
            for (int i = 0; i <= e.taille + 1; i++) {
                if (e.totaldéesJ[e.turn] == e.taille * 2 + i - 2) {
                    e.totaldéesP[e.turn] = 3 * e.taille - 5 - (i - e.taille);
                    i = e.taille + 2;
                }
            }
        }
        ////////////////////////////////
        // Côté gauche du plateau
        else if (4 * e.taille - 4 > e.totaldéesJ[e.turn] && e.totaldéesJ[e.turn] >= 3 * e.taille - 3) {
            for (int i = 0; i <= e.taille; i++) {
                if (e.totaldéesJ[e.turn] == 3 * e.taille + i - 3) {
                    e.totaldéesP[e.turn] = 3 * e.taille - 4 - 2 * i;
                    i = e.taille + 1;
                }
            }
        }
    }
}
