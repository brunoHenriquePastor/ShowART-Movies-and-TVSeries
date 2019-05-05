package br.ufop.tomaz.model;

public interface SerieAndMovieInterface {

    void loadInformationFromInternet();

    enum Category{
        ACTION("Action"), ADVENTURE("Adventure"), ANIMATION("Animation"), BIOGRAPHY("Biography"),
        COMEDY("Comedy"), CRIME("Crime"), DOCUMENTARY("Documentary"), DRAMA("Drama"),
        FAMILY("Family"), FANTASY("Fantasy"), GAME_SHOW("Game Show"),HISTORY("History"),
        HORROR("Horror"),MUSIC("Music"),MUSICAL("Musical"),MYSTERY("Mystery"),NEWS("News"),
        REALITY_TV("Reality-TV"), ROMANCE("Romance"), SCI_FI("Sci-Fi"),SPORT("Sport"),SUPERHERO("Superhero"),
        TALK_SHOW("Talk Show"),THRILLER("Thriller"),WAR("War"),WESTERN("Western");

        private final String category;

        Category(String category){
               this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }
}
