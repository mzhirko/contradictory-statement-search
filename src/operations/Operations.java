//********************************************************************************************
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант 3:Проверить, является ли формула невыполнимой(противоречивой).
// Выполнена студенткой группы 821701 БГУИР Жирко Марией Сергеевной
// Класс предназначен для выполнения логических оперций

package operations;

public class Operations {

    public int callOperation(String operation, String firstParamString, String secondParamString){
        byte firstParam = stringToByte(firstParamString);
        byte secondParam = stringToByte(secondParamString);

        switch (operation) {
            case "/\\":
                return conjunction(firstParam, secondParam);
            case "\\/":
                return disjunction(firstParam, secondParam);
            case "!":
                return negative(firstParam);
            case "~":
                return equivalence(firstParam, secondParam);
            case "->":
                return implication(firstParam, secondParam);
            default:
                System.out.println("Invalid operation parsed!");
                System.exit(0);
        }
        return 0;

    }

    public int conjunction(int x, int y){
        return x & y;
    };

    public int disjunction(int x, int y){
        return x | y;
    };

    public int negative(int z){
        if (z == 1) {
            return 0;
        } else{
            return 1;
        }
    }

    public int equivalence(int x, int y){
        return disjunction(conjunction(negative(x), negative(y)), conjunction(x, y));
    }

    public int implication(int x, int y){
        return disjunction(negative(x), y);
    }

    private byte stringToByte(String param){
        byte tmp = 0;
        try {
            tmp = Byte.parseByte(param);
        } catch (NumberFormatException e) {
            System.err.println("Wrong format of the first param!");
        }
        return tmp;
    }

}
