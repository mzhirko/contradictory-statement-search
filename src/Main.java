//********************************************************************************************
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант 3:Проверить, является ли формула невыполнимой(противоречивой).
// Выполнена студенткой группы 821701 БГУИР Жирко Марией Сергеевной
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import config.Config;
import parser.ExpressionTree;
import parser.Parser;
import truthTable.TruthTable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String expression = "";
        StringBuilder builder = new StringBuilder(expression);
        try (FileInputStream fin = new FileInputStream(Config.IN_FILE_PATH)) {
            while (fin.available() > 0) {
                int oneByte = fin.read();
                builder.append(((char) oneByte));
            }
            expression = builder.toString();
        } catch (FileNotFoundException ex) {
            System.out.println("File's not found!");
        }
        if (expression.equals("")) {
            System.out.println("Formula length = 0");
            System.out.println("There is no subformulas!");
            System.exit(0);
        }

        System.out.println("\nExpression : " + expression + "\n");
        Parser parser = new Parser(expression);
        ExpressionTree tree = parser.CreateTree();
        TruthTable truthTable = new TruthTable(expression);
        System.out.println(parser.getMessage());
        truthTable.countResults(tree);
        truthTable.print();
        truthTable.checkIFExpressionISNeutral();
    }
}
