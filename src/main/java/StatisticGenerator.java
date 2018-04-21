import model.Line;
import model.MyFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StatisticGenerator {


    public static void main(String args[]) {

        String fileResource = "text.txt";

        MyFile file;
        Line lineObj;

        String wordPattern = "[a-zA-Z]+[-]?[a-zA-Z]?";

        List<String> lines = new ArrayList<>();
        List<Line> lineObjList = new ArrayList<>();

        try (Stream<String> text = Files.lines(Paths.get(fileResource))){

            lines = text.filter(s -> !s.isEmpty()).collect(Collectors.toList());

        } catch (IOException e) {e.printStackTrace();}


        for(String line: lines){
            IntSummaryStatistics statistics = Stream.of(line.split(" "))
                    .filter(word -> word.matches(wordPattern))
                    .mapToInt(String::length).summaryStatistics();
            lineObj = new Line(line,
                    line.length(),
                    statistics.getMin(),
                    statistics.getMax(),
                    (int) statistics.getAverage());
            lineObjList.add(lineObj);
        }

        String stringOfFile = String.join("", lines);

        file = new MyFile(stringOfFile, lineObjList);

        System.out.println(storeTheData(file));

    }


    private static String storeTheData(MyFile file){

        String report = "File was stored successfully";

        String queryFile = "INSERT INTO files(text) values(?)";
        String queryLines = "INSERT INTO lines_statistic (line, line_length, min_word, max_word, average_word, file_id) values(?, ?, ?, ?, ?, ?)";

        int fileId = 0;

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/streamexample", "name", "password");
            PreparedStatement fileStatement = connection.prepareStatement(queryFile, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement linesStatement = connection.prepareStatement(queryLines)){

            Class.forName("com.mysql.jdbc.Driver");

            fileStatement.setString(1, file.getText());
            fileStatement.executeUpdate();

            try (ResultSet generatedKeys = fileStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fileId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating file failed, no ID obtained.");
                }
            }

            for(Line line: file.getLines()){
                linesStatement.setString(1, line.getLine());
                linesStatement.setInt(2, line.getLineLength());
                linesStatement.setInt(3, line.getMinWord());
                linesStatement.setInt(4, line.getMaxWord());
                linesStatement.setInt(5, line.getAverageWord());
                linesStatement.setInt(6, fileId);
                linesStatement.addBatch();
            }

            linesStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure: SQLException!";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Failure: ClassNotFoundException!";
        }

        return report;
    }

}
