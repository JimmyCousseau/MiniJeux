package src.Cavalier;

import java.util.Scanner;

public class Cavaliers {
    
    // L'emplacement des pièges
    private int vague;
    private int trou1;
    private int trou2;
    private int mortOuVif;
    ////////////////////////////////
    private int nb_cavalier;
    private int[] totaldéesJ = { 0, 0, 0, 0 }; // Stock l'avancement du cavalier (language Joueur)
    private int[] totaldéesP = { 0, 0, 0, 0 }; // Stock l'avancement du cavalier (language Plateau)
    private int dées; // Les dées tirés
    private int[] attente = { 0, 0, 0, 0 }; // Stock l'attente d'un cavalier avant de pouvoir jouer si il est tombé sur un
                                    // trou
    private int turn; // Défini le tour pour savoir à qui c'est de jouer
    private int totalturn; // Stock le nombre de tours pour savoir si le cavalier a gagné ou pas (mode
                   // solo)
    // défini un caractère pour chaques cavaliers
    private String[] lettre = { "\u001B[31m" + "R" + "\u001B[0m", "\u001B[34m" + "B" + "\u001B[0m",
            "\u001B[32m" + "V" + "\u001B[0m", "\u001B[33m" + "J" + "\u001B[0m" };
    private int taille; // Taille du plateau
    private Scanner enter = new Scanner(System.in);

    public Cavaliers() {
        do {
            System.out.println("Voulez-vous jouer à 1, 2, 3 ou 4 joueurs ?");
            nb_cavalier = enter.nextInt();
        } while (nb_cavalier < 1 || nb_cavalier > 4);
        do {
            System.out.println("Quelle dimension du plateau voulez-vous (entre 6 et 10)");
            taille = enter.nextInt();
        } while (taille < 6 || taille > 10);
        vague = (int) (Math.random() * (taille - 2) + 1);
        mortOuVif = (int) (Math.random() * (taille - 2) + taille * 3 - 3);
        do {
            trou1 = (int) (Math.random() * (2 * (taille - 2)) + taille + 1);
            trou2 = (int) (Math.random() * (2 * (taille - 2)) + taille + 1);
        } while (trou1 == trou2);
        turn = 0;
    }

    public void plateau() {
        // Stocke chaques cases dans une liste pour pouvoir les identifiers
        String[] haut = new String[taille * 4 - 4];
        for (int i = 0; i < haut.length; i++) {
            haut[i] = "___";
        }
        ////////////////////////////////
        haut[taille] = "_A_"; // Point à atteindre pour finir la course
        haut[0] = "_D_"; // Point de départ de la course
        // Place les pièges
        haut[vague] = "_~_";
        haut[trou1] = "_O_";
        haut[trou2] = "_O_";
        haut[mortOuVif] = "OXO";
        ////////////////////////////////

        for (int i = 0; i < nb_cavalier; i++) {
            haut[totaldéesP[i]] = "_" + lettre[i] + "_";
        }
        ////////////////////////////////

        // L'affichage du reste du plateau avec les variables prisent en compte
        // Pour faire la ligne du haut du plateau :
        System.out.print(" ");
        for (int i = 0; i <= taille * 4 - 2; i++) {
            System.out.print("_");
        }
        System.out.println("");
        ////////////////////////////////
        for (int i = 0; i < haut.length; i++) {
            if (i >= taille && i < (taille - 3) * 2 + taille) {
                if (i == taille) {
                    System.out.print("|");
                }
                System.out.println("");
                System.out.print("|" + haut[i] + "|");
                for (int x = 0; x <= taille * 4 - 10; x++) {
                    System.out.print(" ");
                }
                i++;
                System.out.print("|" + haut[i] + "|");

            } else if (i == (taille - 3) * 2 + taille) {
                System.out.println("");
                System.out.print("|" + haut[i] + "|");
                for (int x = 0; x <= taille * 4 - 10; x++) {
                    System.out.print("_");
                }
                i++;
                System.out.print("|" + haut[i] + "|");
                System.out.println("");
            } else {
                System.out.print("|" + haut[i]);
            }
        }
        System.out.print("|");
        askToPlay();
    }

    public void askToPlay() {
        if (nb_cavalier == 1) {
            System.out.println("\nÀ ton tour");
        } else {
            System.out.println("\nAu tour de " + lettre[turn]);
        }
        System.out.println("\n\nAppuyez sur entrer pour lancer les dés");
        System.out.println("Si vous souhaitez arrêter la partie saisissez s");
        String valid = enter.nextLine();
        if (valid.length() == 0 || valid != "s") {
            dées = (int) (Math.random() * 6 + 1); // Tire les dés
            System.out.println("Dées tiré : " + dées);
            conditions();
        } else {
            System.out.println("Jeu arrêté");
            System.exit(0);
        }
    }

    public void conditions() {
        
        // Vérification si le cavalier est tombé dans un trou ou non
        if (totaldéesP[turn] == trou1 || totaldéesP[turn] == trou2) {
            if (attente[turn] != 2 && attente[turn] == 0) {
                attente[turn] = 2;
                System.out.println("\u001B[31m" + "Veuillez attendre " + attente[turn] + " tours," + "\u001B[0m");
            } else {
                attente[turn] -= 1;
                if (attente[turn] != 0) {
                    System.out.println("\u001B[31m" + "Veuillez attendre " + attente[turn] + " tours," + "\u001B[0m");
                } else {
                    System.out.println("\u001B[32m" + "Vous êtes enfin libéré" + "\u001B[0m");
                    totaldéesJ[turn] += dées; // Stocke la somme des dées tirés tout au long de la partie pour
                                                    // situer
                }
            }
        }
        ////////////////////////////////
        // Si un cavalier tombe sur la case mortOuVif
        else if (totaldéesP[turn] == mortOuVif) {
            if (dées % 2 == 0) {
                System.out.println("\u001B[32m"
                        + "Vous êtes tombé sur la case Mort ou Vif\nEt vous avez fait un nombre pair, vous multipliez donc\nvotre vitesse par 2 pour ce tour-ci"
                        + "\u001B[0m");
                dées *= dées;
                totaldéesJ[turn] += dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
            } else {
                System.out.println("\u001B[31m"
                        + "Vous êtes tombé sur la case Mort ou Vif\nEt vous avez fait un nombre impair\nRetournez à la case départ"
                        + "\u001B[0m");
                totaldéesJ[turn] = 0;
            }
        }
        ////////////////////////////////
        // Si un cavalier tombe sur la rivière
        else if (totaldéesP[turn] == vague) {
            System.out.println(
                    "Vous êtes dans la rivière, vous ne pouvez pas sortir tant que vous ne faites pas un chiffre pair");
            if (dées % 2 == 0) {
                System.out.println(
                        "\u001B[32m" + "Vous pouvez sortir de la rivière, vous avez fait un nombre pair" + "\u001B[0m");
                totaldéesJ[turn] += dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
            } else {
                System.out.println("\u001B[31m"
                        + "Vous n'avez pas fait de nombre pair, vous restez bloqué dans la rivière" + "\u001B[0m");
            }
        }
        ////////////////////////////////
        else {
            totaldéesJ[turn] += dées; // Stocke la somme des dées tirés tout au long de la partie pour situer
        }
        // Si un cavalier tombe sur un autre cavalier il recule
        for (int i = 0; i <= nb_cavalier - 1; i++) {
            if (totaldéesJ[turn] == totaldéesJ[i] && i != turn) {
                totaldéesJ[turn] -= taille / 2;
                System.out.println("\u001B[31m" + lettre[turn] + " est tombé sur " + lettre[i]
                        + " ,\nIl recule donc de " + taille / 2 + "\u001B[0m");
                if (totaldéesJ[turn] < 1) {
                    totaldéesJ[turn] = 1;
                }
            }
        }
        ////////////////////////////////
        change();
    }

    public void change() {
        // Vérifie si le cavalier est sur la case d'arrivée
        if (totaldéesP[turn] == taille) {
            if (nb_cavalier == 1) {
                if (totalturn <= taille) {
                    System.out.println(
                            "\n\n\nFin de la partie,\nBravo !\nTu as parcouru le plateau en moins de tours que la dimension du plateau !\n\n\n");
                    System.exit(0);
                } else {
                    System.out.println(
                            "\n\n\nFin de la partie,\nPerdu  :(\nTu as parcouru le plateau en faisant plus de tours que la dimension du plateau !\n\n\n");
                    System.exit(0);
                }
            } else {
                System.out.println("\n\n\nFin de la partie le joueur " + (turn + 1) + " a gagné !\n\n\n");
                System.exit(0);
            }
        } 
        ////////////////////////////////
        // Si n'est pas sur case d'arrivée, il recule
        else if (totaldéesJ[turn] > taille * 4 - 5) {
            int b = taille * 4 - 5;
            totaldéesJ[turn] = 2 * b - totaldéesJ[turn];
        }
        ////////////////////////////////

        
        remisePlateau(); // Remet le plateau dans l'ordre des chiffres, sens horair


        if (totaldéesP[turn] == mortOuVif) {
            System.out.println("\u001B[31m"
                    + "Vous êtes tombé sur la case Mort ou Vif, au prochain tour si vous faites un nombre pair, vous multiplierez votre vitesse par trois, sinon vous retournez à la case départ"
                    + "\u001B[0m");
        }
        if (totaldéesP[turn] == vague) {
            System.out.println("\u001B[31m"
                    + "Vous êtes tombé dans la rivière, si vous faites un nombre pair au prochain tour, vous pourrez sortir"
                    + "\u001B[0m");
        }
        if (totaldéesP[turn] == trou1 || totaldéesP[turn] == trou2) {
            System.out.println(")\u001B[31m" + "Vous êtes tombé dans la fosse" + "\u001B[0m");
        }
        // Fait la transition entre les cavaliers
        if (turn >= nb_cavalier - 1) {
            turn = 0;
        } else if (turn < nb_cavalier - 1) {
            turn += 1;
            totalturn += 1;
        }
        plateau();
    }

    public void remisePlateau() {
        // Le plateau n'a pas le même sens de lecture que nous (le sens horaire), nous
        // avons donc
        // repris tous les numéros de cases pour les remettres dans le bonne ordre
        ////////////////////////////////
        totaldéesP[turn] = totaldéesJ[turn];
        // Côté droit du plateau
        if (2 * taille - 2 > totaldéesJ[turn] && totaldéesJ[turn] >= taille) {
            for (int i = 0; i <= taille; i++) {
                if (totaldéesJ[turn] == taille + i) {
                    totaldéesP[turn] = (totaldéesP[turn] - taille) * 2 + taille + 1;
                    i = taille + 1;
                }
            }
        }
        ////////////////////////////////
        // Côté bas du plateau
        else if (3 * taille - 3 > totaldéesJ[turn] && totaldéesJ[turn] >= 2 * taille - 2) {
            for (int i = 0; i <= taille + 1; i++) {
                if (totaldéesJ[turn] == taille * 2 + i - 2) {
                    totaldéesP[turn] = 3 * taille - 5 - (i - taille);
                    i = taille + 2;
                }
            }
        }
        ////////////////////////////////
        // Côté gauche du plateau
        else if (4 * taille - 4 > totaldéesJ[turn] && totaldéesJ[turn] >= 3 * taille - 3) {
            for (int i = 0; i <= taille; i++) {
                if (totaldéesJ[turn] == 3 * taille + i - 3) {
                    totaldéesP[turn] = 3 * taille - 4 - 2 * i;
                    i = taille + 1;
                }
            }
        }
    }
}
