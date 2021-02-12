package pl.dawidbialek;

public class Point {
    private final float x;
    private final float y;
    private final int lineId;

    public Point(float x, float y, int lineId) {
        this.x = x;
        this.y = y;
        this.lineId = lineId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getLineId() {
        return lineId;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", lineId=" + lineId +
                '}';
    }
}
