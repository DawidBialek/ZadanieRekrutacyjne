package pl.dawidbialek;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static ArrayList<Point> loadPoints(String fileName) {
        ArrayList<Point> pointsList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(fileName));
            sc.useDelimiter(";|\r\n");

            while (sc.hasNext()) {
                float x = sc.nextFloat();
                float y = sc.nextFloat();
                int id = sc.nextInt();

                Point point = new Point(x, y, id);
                pointsList.add(point);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return pointsList;
    }

    public static ArrayList<Line> loadLines(String fileName, ArrayList<Point> pointsList) {
        ArrayList<Line> linesList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(fileName));
            sc.useDelimiter(";|\r\n");

            int index = 0;
            while (sc.hasNext()) {
                ArrayList<Point> tempPoint = new ArrayList<>();
                for (int i = 0; i < pointsList.size(); i++) {
                    if (pointsList.get(i).getLineId() == index) {
                        tempPoint.add(pointsList.get(i));
                    }
                }

                index++;
                Point[] points = {tempPoint.get(0), tempPoint.get(1)};

                int id = sc.nextInt();
                boolean flag = sc.nextBoolean();
//
                Line line = new Line(points, flag);
                linesList.add(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return linesList;
    }

    public static void writeLinesToByteFile(ArrayList<Line> linesList) {
        Path path = Paths.get("ByteLines.dat");

        try {
            FileOutputStream outputStream = new FileOutputStream(path.toString());

            for (int i = 0; i < linesList.size(); i++) {
                if (linesList.get(i).getSomeFlag() == true) {
                    ArrayList<byte[]> lineBytes = linesList.get(i).toByte();
                    for (int j = 0; j < lineBytes.size(); j++) {
                        outputStream.write(lineBytes.get(j));
                        if (j + 1 < lineBytes.size()) {
                            outputStream.write(",".getBytes());
                        }
                    }
                    outputStream.write("\r\n".getBytes());
                }
            }

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Line> loadLinesFromByteFile(String name) {
        ArrayList<Line> lineList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(name));
            sc.useDelimiter(",|\r\n");

            int index = 0;

            ArrayList<String> input = new ArrayList<String>();

            while (sc.hasNext()) {
                for (int i = 0; i < 7; i++) {
                    if (sc.hasNext()) {
                        String str = sc.next();
                        if (str.charAt(0) == '\u0000') {
                            break;
                        }
                        input.add(str);
                    }
                }
            }
            lineList = Line.fromString(input);

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return lineList;
    }

    public static void main(String[] args) {

        ArrayList<Point> pointsList = loadPoints("points.csv");

        ArrayList<Line> linesList = loadLines("lines.csv", pointsList);

        writeLinesToByteFile(linesList);

        List<Line> linesFromFile = loadLinesFromByteFile("ByteLines.dat");

        System.out.println("Lines in a List<>: " + linesFromFile);
    }
}
