package project;

public class Value {
    public float[] values;
    public int index = 0;
    public int size;

    public Value(int size) {
        this.values = new float[size];
        this.size = size;
        this.index = 0;
    }

    public void addValue(float value) {
        this.values[this.index] = value;
        this.index++;
    }

    public float getValue(int indice) {
        return this.values[indice];
    }

    public int getSize() {
        return this.size;
    }

    public void printValues() {
        for ( float value : values ) {
            System.out.println(value);
        }
    }
}
