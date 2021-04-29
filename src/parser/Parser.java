//********************************************************************************************
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант 3:Проверить, является ли формула невыполнимой(противоречивой).
// Выполнена студенткой группы 821701 БГУИР Жирко Марией Сергеевной
// Оказал помощь в реализации некоторых функций Клевцевич Александр Викторович
// Класс предназначен для парсинга формул

package parser;

import config.Config;

import java.beans.Expression;
import java.util.*;

public class Parser {
    private final String EXPRESSION;

    private String message;

    private final List<String> subFormulas;
    private final List<String> uniqueSubFormulas;
    private final Set<String> ELEMENTS;
    private final List<String> ATOMS;

    public Parser(String expression) {
        this.EXPRESSION = expression;
        ELEMENTS = new HashSet<>();
        ATOMS = new ArrayList<>();
        subFormulas = new ArrayList<>();
        uniqueSubFormulas = new ArrayList<>();
        message = "";

    }

    public ExpressionTree CreateTree(){
        try {
            // checks if bracket placement is correct
            checkBrackets();

            // checks if all symbols are correct
            checkSymbols();

            // create parse tree of the expression
            ExpressionTree tree = new ExpressionTree(EXPRESSION, this);


            if (ELEMENTS.size() == 0) {
                System.out.println("Invalid syntax!");
                System.exit(0);
            }

            // finds
            searchSubFormulas(tree);


            return tree;
        }
        catch (FormulaException FormulaException){
            message = "Formula Error: ";
            message += FormulaException.getMessage();
            return null;
        }

    }

    private void checkSymbols() throws FormulaException {
        if (EXPRESSION.length() == 1) {
            if (!Config.SYMBOLS.contains(EXPRESSION)) {
                throw new FormulaException(6);
            }
        }
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (!(Config.SYMBOLS.contains("" + EXPRESSION.charAt(i)) || Config.SIGNS.contains("" + EXPRESSION.charAt(i)))) {
                String sign = searchSign(EXPRESSION, i);
                if (!Config.SIGNS.contains(sign)) {
                    throw new FormulaException(6);
                } else {
                    if (sign.length() == 2) {
                        i++;
                    }
                }
            }
        }
    }

    private String searchSign(String expression, int pointer) {
        if (expression.charAt(pointer) == '!' || expression.charAt(pointer) == '~')
            return expression.charAt(pointer) + "";
        return "" + expression.charAt(pointer) + expression.charAt(pointer + 1);
    }

    // Автор: Клевцевич А.В, гр 821701.
    private void checkBrackets() throws FormulaException {
        if (EXPRESSION.contains(")(")) {
            throw new FormulaException(3);
        }
        if (EXPRESSION.charAt(0) == ')') {
            throw new FormulaException(3);
        }
        if (EXPRESSION.charAt(0) != '(' && EXPRESSION.length() != 1) {
            throw new FormulaException(3);
        }
        if (EXPRESSION.charAt(0) == '(' && EXPRESSION.charAt(EXPRESSION.length() - 1) != ')') {
            throw new FormulaException(3);
        }
        int checkOpen = 0;
        int checkClose = 0;
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (EXPRESSION.charAt(i) == '(') {
                checkOpen++;
            } else if (EXPRESSION.charAt(i) == ')') {
                checkClose++;
            }
        }
        if (checkOpen > checkClose) {
            throw new FormulaException(1);
        }
        if (checkClose > checkOpen) {
            throw new FormulaException(2);
        }
    }

    private void searchSubFormulas(ExpressionTree tree) {
        if (!"!".equals(tree.getOperation())) {
            if (!tree.getExpression().equals("1") && !tree.getExpression().equals("0")){
                subFormulas.add(tree.getExpression());
            }
            if (tree.getOperation() != null) {
                searchSubFormulas(tree.getLeft());
            }
            if (tree.getOperation() != null) {
                searchSubFormulas(tree.getRight());
            }
        } else {
            if (!tree.getExpression().equals("1") && !tree.getExpression().equals("0")){
                subFormulas.add(tree.getExpression());
            }
            subFormulas.add(tree.getExpression());
            if (tree.getOperation() != null) {
                searchSubFormulas(tree.getLeft());
            }
        }

    }

    public String getMessage() {
        return message;
    }

    public void addElements(String element) {
        if (Config.SYMBOLS.contains(element)) {
            ELEMENTS.add(element);
        }
    }

}
