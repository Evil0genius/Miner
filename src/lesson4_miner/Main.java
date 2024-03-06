package lesson4_miner;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        System.out.println("Выберите сложность, больше число - меньше сложность:");
        int bound = input()+2;
        System.out.println("Введите размеры поля - ряды,затем столбцы :");
        int[][] mineField = new int[input()][input()];
        Main.generateField(mineField, bound);
        String[][] fieldReady = mineCount(mineField);
        String[][] closedField = new String[mineField.length][mineField[0].length];
        //Main.drawField(fieldReady);
        System.out.println();
        Main.fillClosedField(fieldReady, closedField);
        Main.mineFlag(mineField, fieldReady, closedField);
    }

    static void generateField(int[][] mineField, int bound) {   //расставляем мины
        int cell = 0;
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                cell = ThreadLocalRandom.current().nextInt(0, bound);
                if (cell == 0 | cell == 1) mineField[i][j] = cell;
                //System.out.print(mineField[i][j] + "  ");
            }
            //System.out.println();
        }
        System.out.println();
    }

    static int input() {  //принимаем ввод
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static String[][] mineCount(int[][] mineField) {  //считаем цифры в клетках
        int[][] mineCount = new int[mineField.length][mineField[0].length];
        String[][] fieldReady = new String[mineField.length][mineField[0].length];
        int cell = 0;
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                try {
                    cell += mineField[i - 1][j - 1];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i - 1][j];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i - 1][j + 1];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i][j - 1];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i][j + 1];
                } catch (Exception ignored) {
                }
                try {
                    assert mineField[i + 1] != null;
                    cell += mineField[i + 1][j - 1];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i + 1][j];
                } catch (Exception ignored) {
                }
                try {
                    cell += mineField[i + 1][j + 1];
                } catch (Exception ignored) {
                }
                mineCount[i][j] = cell;
                cell = 0;
            }
        }
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (mineField[i][j] == 1) {
                    fieldReady[i][j] = "X";
                } else fieldReady[i][j] = String.valueOf(mineCount[i][j]);
            }
        }
        return fieldReady;

    }

    static void mineFlag(int[][] mineField, String[][] fieldReady, String[][] closedField) {  //играем
        System.out.println("Укажите координаты мины:");
        for (int i = 0; i < mineField.length * mineField[0].length; i++) {
            int row = input()-1;
            int col = input()-1;
            System.out.println("открыть(0) или флаг(1)");
            int choose = input();
            if (mineField[row][col] == 0) {
                if (choose == 0) {
                    closedField[row][col] = fieldReady[row][col];
                } else closedField[row][col] = "F";
                if (Main.win(closedField)) {
                    System.out.println("Вы выиграли");
                    break;
                } else Main.drawField(closedField);
            } else if (mineField[row][col] == 1) {
                if (choose == 0) {
                    System.out.println("БАХ! Вы проиграли");
                    break;
                } else closedField[row][col] = "F";
                if (Main.win(closedField)) {
                    System.out.println("Вы выиграли");
                    break;
                } else Main.drawField(closedField);
            }
        }
    }

    static void drawField(String[][] closedField) {  //рисуем поле
        for (String[] dr : closedField) {
            for (String anDr : dr) {
                System.out.print(anDr + "  ");
            }
            System.out.println();
        }
    }

    static void fillClosedField(String[][] fieldReady, String[][] closedField) {  //прячем мины
        for (int i = 0; i < fieldReady.length; i++) {
            for (int j = 0; j < fieldReady[i].length; j++) {
                if (fieldReady[i][j].contains("0") | fieldReady[i][j].contains("1")) {
                    closedField[i][j] = fieldReady[i][j];
                } else closedField[i][j] = "X";
                System.out.print(closedField[i][j] + "  ");
            }
            System.out.println();
        }
    }

    static boolean win(String[][] closedField) {  //определяем победу
        int check = 0;
        for (String[] dr : closedField) {
            for (String anDr : dr) {
                if (anDr.contains("X")) {
                    check += 1;
                }
            }
        }
        if (check > 0) {
            return false;
        } else return true;
    }
}


