import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;

public class Demineur {
    public static void main(String[] args) {
        int[][] plateauSol = new int[10][10];
        String[][] plateauHidden = new String[10][10];
        int BombesInGame = 1;
        placerBombes(plateauSol, BombesInGame);
        placerNums(plateauSol);
        hide(plateauHidden);
        affichage(plateauHidden);
        int nbBombes = searchChar(" 8 ", plateauHidden);
        int nbTirets = searchChar(" - ", plateauHidden);
        while (BombesInGame - nbBombes <= nbTirets) {
            play(plateauSol, plateauHidden);
            nbBombes = searchChar(" 8 ", plateauHidden);
            nbTirets = searchChar(" - ", plateauHidden);
            if (nbBombes > BombesInGame / 2) {
                System.out.println("\n\n Tu as fais explosé plus de " + (BombesInGame / 2) + " bombes, tu as perdu");
                break;
            }
            else if (nbTirets - BombesInGame + nbBombes <= 0) {
                System.out.println("\n\n Bravo tu as gagné !!");
            }
            else {
                affichage(plateauHidden);
            }
        }
    }

    static void play(int[][] plateauSol, String[][] plateauHidden) {
        System.out.println();
        Scanner enter = new Scanner(System.in);
        char[] pos = enter.nextLine().toCharArray();
        int x = Character.getNumericValue(pos[0]);;
        int y = Character.getNumericValue(pos[1]);
        while (plateauHidden[x][y] != " - " || pos.length > 2) {
            pos = enter.nextLine().toCharArray();
            x = Character.getNumericValue(pos[0]);
            y = Character.getNumericValue(pos[1]);
        }
        reveal(plateauSol, plateauHidden, x, y);
    }
    
    static void reveal(int[][] plateauSol, String[][] plateauHidden, int x, int y) {
        ArrayList<Integer> traitement = new ArrayList<Integer>();
        traitement.add(x);
        traitement.add(y);
            do {
                x = traitement.get(0);
                y = traitement.get(1);

                if (plateauSol[x][y] == 0) {
                    if (x != 0) {
                        if (plateauSol[x - 1][y] == 0 && plateauHidden[x - 1][y] == " - ") {
                            traitement.add(x - 1);
                            traitement.add(y);
                        }
                    }
                    if (x != plateauSol.length - 1) {
                        if (plateauSol[x + 1][y] == 0 && plateauHidden[x + 1][y] == " - ") {
                            traitement.add(x + 1);
                            traitement.add(y);
                        }
                    }
                    if (y != 0) {
                        if (plateauSol[x][y - 1] == 0 && plateauHidden[x][y - 1] == " - ") {
                            traitement.add(x);
                            traitement.add(y - 1);
                        }
                    }
                    if (y != plateauSol.length - 1) {
                        if (plateauSol[x][y + 1] == 0 && plateauHidden[x][y + 1] == " - ") {
                            traitement.add(x);
                            traitement.add(y + 1);
                        }
                    }
                }
                plateauHidden[x][y] = " " + Integer.toString(plateauSol[x][y]) + " ";
                traitement.remove(0);
                traitement.remove(0);
                if (traitement.size() == 0) {
                    break;
                }
            } while (traitement.size() > 1);
    }

    static int searchChar(String search, String[][] plateau) {
        int count = 0;
        for (int x = 0; x < plateau.length; x++) {
            for (int y = 0; y < plateau.length; y++) {
                if (plateau[x][y] == search) {
                    count++;
                }
            }
        }
        return count;
    }

    static void placerBombes(int[][] plateau, int BombesInGame) {
        int x;
        int y;
        for (int i = 0; i < BombesInGame;) {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);
            if (plateau[x][y] != 8) {
                plateau[x][y] = 8;
                i++;
            }
        }
    }

    static void hide(String[][] plateau) {
        for (int x = 0; x < plateau.length; x++) {
            for (int y = 0; y < plateau.length; y++) {
                plateau[x][y] = " - ";
            }
        }
    }


    static void placerNums(int[][] plateau) {
        for (int x = 0; x < plateau.length; x++) {
            for (int y = 0; y < plateau.length; y++) {
                if (plateau[x][y] == 8) {
                    if (x != 0) {
                        if (plateau[x - 1][y] != 8) {
                            plateau[x - 1][y] += 1;
                        }
                    }
                    if (x != plateau.length - 1) {
                        if (plateau[x + 1][y] != 8) {
                            plateau[x + 1][y] += 1;
                        }
                    }
                    if (y != 0) {
                        if (plateau[x][y - 1] != 8) {
                            plateau[x][y - 1] += 1;
                        }
                    }
                    if (y != plateau.length - 1) {
                        if (plateau[x][y + 1] != 8) {
                            plateau[x][y + 1] += 1;
                        }
                    }
                    if (y != 0 && x != 0) {
                        if (plateau[x - 1][y - 1] != 8) {
                            plateau[x - 1][y - 1] += 1;
                        }
                    }
                    if (y != 0 && x != plateau.length - 1) {
                        if (plateau[x + 1][y - 1] != 8) {
                            plateau[x + 1][y - 1] += 1;
                        }
                    }
                    if (y != plateau.length - 1 && x != 0) {
                        if (plateau[x - 1][y + 1] != 8) {
                            plateau[x - 1][y + 1] += 1;
                        }
                    }
                    if (y != plateau.length - 1 && x != plateau.length - 1) {
                        if (plateau[x + 1][y + 1] != 8) {
                            plateau[x + 1][y + 1] += 1;
                        }
                    }
                }
            }
        }
    }

    static void affichage(String[][] plateau) {
        for (int x = 0; x < plateau.length; x++) {
            if (x == 0) {
                System.out.print("     ");
            }
            System.out.print(" " + x + "0  ");
        }
        for (int x = 0; x < plateau.length; x++) {
            System.out.println();
            System.out.print(" 0" + x + " ");
            for (int y = 0; y < plateau.length; y++) {
                System.out.print(" " + plateau[y][x] + " ");
            }
        }
    }
}
