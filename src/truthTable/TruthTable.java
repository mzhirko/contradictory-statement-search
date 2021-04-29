//********************************************************************************************
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант 3:Проверить, является ли формула невыполнимой(противоречивой).
// Выполнена студенткой группы 821701 БГУИР Жирко Марией Сергеевной
// Для построения таблицы истинности

package truthTable;

import config.Config;
import operations.Operations;
import parser.ExpressionTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruthTable {

    private Map<String, ArrayList<String>> formulaValue;
    private ArrayList<String> results;
    private ArrayList<String> symbols;
    private int rowAmount;
    private int columnAmount;
    private Operations operations;

    // TruthTable generates truth table (without result column) which represents map
    // with key - formula(symbol A, B, C, etc.)
    // with value - array of values of this formula
    public TruthTable(String expression){
        operations = new Operations();
        results = new ArrayList<String>();
        formulaValue = new HashMap<String, ArrayList<String>>();
        getSymbols(expression);

        for (int i = 0; i < columnAmount; i++){
            formulaValue.put(symbols.get(i), new ArrayList<String>());
        }

        fillInTable();
    }

    // getSymbols writes all formulas(symbol A, B, C, etc.) in the expression to the array,
    // counts column and row amount of the truth table (without result column).
    public void getSymbols(String expression){
        symbols = new ArrayList<String>();
        for (int i = 0; i < Config.SYMBOLS.size(); i++){
            if (expression.contains(Config.SYMBOLS.get(i)) && !Config.SYMBOLS.get(i).equals("0") && !Config.SYMBOLS.get(i).equals("1")) {
                symbols.add(Config.SYMBOLS.get(i));
            }
        }
        columnAmount = symbols.size();
        rowAmount = 1;
        for (int i = 0; i < columnAmount; i++){
            rowAmount *= 2;
        }
    }

    // fillInTable fills in truth table(without result column)
    public void fillInTable(){
        String binaryRowNumber;
        int tmp;
        for (int i = 0; i < rowAmount; i++){
            binaryRowNumber = Integer.toBinaryString(i);
            tmp = columnAmount - binaryRowNumber.length();
            for (int j = 0; j < tmp; j++){
                binaryRowNumber = "0" + binaryRowNumber;
            }
            for (int j = 0; j < columnAmount; j++){
                if (j < binaryRowNumber.length()) {
                    formulaValue.get(symbols.get(j)).add("" + binaryRowNumber.charAt(j));
                }
                else{
                    formulaValue.get(symbols.get(j)).add("" + "0");
                }
            }
        }
    }

    public String countResult(ExpressionTree tree, int truthRowNumber) {
        String firstParam = "0";
        String secondParam = "0";
        if (tree.getOperation() != null) {
            if (tree.getOperation().equals("!")) {
                firstParam = countResult(tree.getLeft(), truthRowNumber);
            } else {
                firstParam = countResult(tree.getLeft(), truthRowNumber);
                secondParam = countResult(tree.getRight(), truthRowNumber);
            }
        } else{
            if (tree.getExpression().equals("1")){
                firstParam = "1";
            }
            else if (tree.getExpression().equals("0")){
                firstParam = "0";
            }
            else {
                firstParam = formulaValue.get(tree.getExpression()).get(truthRowNumber);
            }
            return firstParam;
        }
        return Integer.toString(operations.callOperation(tree.getOperation(), firstParam, secondParam));
    }

    public void countResults(ExpressionTree tree){
        for (int i = 0; i < rowAmount; i++){
            results.add(countResult(tree, i));
        }
    }

    public void print() {
        for (int i = 0; i < columnAmount; i++){
            System.out.print(symbols.get(i) + "|");
        }
        System.out.println("result");

        for (int i = 0; i < rowAmount; i++){
            for (int j = 0; j < columnAmount; j++){
                System.out.print(formulaValue.get("" + symbols.get(j)).get(i) + "|");
            }
            System.out.println(results.get(i));
        }
    }

    public void checkIFExpressionISNeutral(){
        if (results.contains("0") && results.contains("1")){
            System.out.println("Statement is neutral! (Not contradictory)");
        }
        else if (!results.contains("0")){
            System.out.println("Statement is universally valid! (Not contradictory)");
            }
        else if (!results.contains("1")){
            System.out.println("Statement is contradictory!");
        }
    }
}
