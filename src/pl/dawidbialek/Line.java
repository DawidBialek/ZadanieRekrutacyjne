package pl.dawidbialek;

import java.util.ArrayList;
import java.util.Arrays;

public class Line {
    private final Point[] points;
    private final boolean someFlag;

    public Line(Point[] points, boolean someFlag) {
        this.points = points;
        this.someFlag = someFlag;
    }

    public Point[] getPoints() {
        return points;
    }

    public boolean getSomeFlag() {
        return someFlag;
    }

    public ArrayList<byte[]> toByte() {

        ArrayList<byte[]> byteList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Point point = points[i];
            String x = String.valueOf(point.getX());
            String y = String.valueOf(point.getY());
            String id = String.valueOf(point.getLineId());

            byteList.add(x.getBytes());
            byteList.add(y.getBytes());
            byteList.add(id.getBytes());
        }

        byteList.add(String.valueOf(this.someFlag).getBytes());

        return byteList;
    }

    static public ArrayList<Line> fromString(ArrayList<String> input) {

        ArrayList<Line> linesList = new ArrayList<>();

        for (int i = 0; i < input.size(); i = i + 7) {
            ArrayList<Point> points = new ArrayList<>();
            for (int j = 0; j < 6; j = j + 3) {
                float x = Float.valueOf(input.get(j + i));
                float y = Float.parseFloat(input.get(j + i + 1));
                int id = Integer.parseInt(input.get(j + i + 2));

                Point point = new Point(x, y, id);
                points.add(point);
            }
            boolean someFlag = Boolean.valueOf(String.valueOf(input.get(6 + i)));
            linesList.add(new Line(new Point[]{points.get(0), points.get(1)}, someFlag));
        }
        return linesList;
    }

    @Override
    public String toString() {
        return "Line{" +
                "points=" + Arrays.toString(points) +
                ", someFlag=" + someFlag +
                '}';
    }
}
