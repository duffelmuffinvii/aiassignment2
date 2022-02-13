public class TowerPiece {
    String type;
    int width;
    int strength;
    int cost;

    public TowerPiece(String type, int width, int strength, int cost) {
        this.type = type;
        this.width = width;
        this.strength = strength;
        this.cost = cost;
    }

    public String toString() {
        return this.type + ", " + this.width + ", " + this.strength + ", " + this.cost;
    }

    public boolean equals(TowerPiece p) {
        return type.equals(p.type) && width == p.width && strength == p.strength && cost == p.cost;
    }
}
