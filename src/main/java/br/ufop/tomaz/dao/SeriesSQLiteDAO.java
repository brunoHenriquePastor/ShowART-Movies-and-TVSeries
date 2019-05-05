package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.Actor;
import br.ufop.tomaz.model.SerieAndMovieInterface.Category;
import br.ufop.tomaz.model.Series;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.util.CustomImage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SeriesSQLiteDAO extends SeriesDAO {


    public SeriesSQLiteDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Series> retrieveSeries(String title, User user) {
        return null;
    }

    @Override
    public List<Series> retrieveSeries(User user) {
        List<Series> seriesList = new LinkedList<>();
        String sql = "SELECT S.* FROM SERIES S WHERE S.User_owner = ?";
        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,user.getUsername());
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                Series series = getSeries(rst);
                seriesList.add(series);
            }
            return seriesList;
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean addSeriesToDB(Series series, User user) {
        String sqlSeries = "INSERT INTO SERIES VALUES (?,?,?,?,?,?,?)";
        String title = series.getTitle();
        String description = series.getDescription();
        String releaseDateStr = new SimpleDateFormat("d-MM-YYYY")
                                    .format(series.getReleaseDate().getTime());
        int rating = series.getRating();
        String imageCoverPath = ((CustomImage)series.getImgCover()).getUrl();
        String userOwner = user.getUsername();
        StringBuilder categories = new StringBuilder();
        series.getCategories().forEach(category -> categories.append(category).append(" "));
        try {
            //Insert series in table series
            PreparedStatement pst = this.connection.prepareStatement(sqlSeries);
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setInt(3, rating);
            pst.setString(4,releaseDateStr);
            pst.setString(5,imageCoverPath);
            pst.setString(6,userOwner);
            pst.setString(7,categories.toString().trim());

            pst.execute();

            //Insert cast
            series.getCast().forEach((actor -> this.insertCast(actor,series,user)));

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            //TODO -- Handle situation where the series is already in DB
        }
        return false;
    }

    @Override
    public boolean deleteSeriesFromDB(Series series, User user) {
        String sql = "DELETE FROM SERIES WHERE Title = ? AND User_owner = ?";
        String title = series.getTitle();
        String username = user.getUsername();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,title);
            pst.setString(2,username);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSeriesOnDB(Series series, User user) {
        return false;
    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Occurred some error.The connection continue open !");
            e.printStackTrace();
        }
    }

    private void insertCast(Actor actor,Series series,User user){
        String name = actor.getName();
        String title = series.getTitle();
        String userOwner = user.getUsername();

        try {
            if(!isRegistered(actor)){
                String imgUrl = actor.getImage().getUrl();
                String webUrl = actor.getWebPageLink();
                String sqlRegister = "INSERT INTO ACTOR VALUES (?,?,?)";
                PreparedStatement pstRegister = connection.prepareStatement(sqlRegister);
                pstRegister.setString(1,name);
                pstRegister.setString(2,webUrl);
                pstRegister.setString(3,imgUrl);
                pstRegister.execute();
            }

            String sqlCasting = "INSERT INTO CASTING VALUES (?,?,?)";
            PreparedStatement pstCasting = connection.prepareStatement(sqlCasting);
            pstCasting.setString(1,name);
            pstCasting.setString(2,title);
            pstCasting.setString(3,userOwner);
            pstCasting.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean isRegistered(Actor actor) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT Name) FROM ACTOR WHERE Name = ?";
        String name = actor.getName();

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1,name);
        ResultSet rst = pst.executeQuery();
        rst.next();
        return rst.getInt(1) != 0;
    }

    private Series getSeries(ResultSet rst) throws SQLException, ParseException, IOException {
        String title = rst.getString("title");
        String description = rst.getString("description");
        int rating = rst.getInt("rating");
        Date releaseDate = new SimpleDateFormat("d-MM-YYYY")
                                    .parse(rst.getString("release_date"));
        String imgCoverUrl = rst.getString("image_cover_src");
        InputStream is = Files.newInputStream(new File(imgCoverUrl).toPath());
        Image cover = new CustomImage(is,imgCoverUrl);
        is.close();
        List<Category> categories = Arrays.stream(rst.getString("categories").split(" "))
                                          .map(Category::valueOf)
                                          .collect(Collectors.toList());
        return new Series(title,description,categories,rating,cover,null,releaseDate);
    }
}
