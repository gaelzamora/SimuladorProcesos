package project;

public class Formula {
    public int[] values;
    public float totalValues;
    public int index;
    public int size;

    public Formula(int size) {
        this.values = new int[size];
        this.index = 0;
        this.size = size;
    }

    public void addValue(int value) {
        this.values[this.index] = value;
        this.index++;
    }

    public float totalFormula() {
        for(int value : this.values) {
            System.out.println(value);
            totalValues += value;
        }

        totalValues /= this.size;

        return totalValues;
    }
}
