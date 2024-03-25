package project;

public class Prueba {
    public static void main(String[] args) {
        float number = 19.2f;

        if(number != (int) number) {
            number = (int) number;
            System.out.println(number);
        } else {
            System.out.println(number);
        }
    }
}
