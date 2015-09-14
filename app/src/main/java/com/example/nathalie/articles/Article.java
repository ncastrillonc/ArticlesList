package com.example.nathalie.articles;

/**
 * Created by Nathalie on 13/09/2015.
 */
import android.os.Parcelable;
import android.os.Parcel;

public class Article implements Parcelable, Comparable<Article>{

    private String website;
    private String title;
    private String image;
    private int img;
    private String content;
    private String authors;
    private String date;
    static int op = 3;

    public Article(String website, String title, String image, int i, String content, String authors, String date){
        this.website = website;
        this.title = title;
        this.image = image;
        this.img = i;
        this.content = content;
        this.authors = authors;
        this.date = date;
    }

    @Override
    public int compareTo(Article ar) {

        if(op == 1){
            if (this.title.compareTo(ar.getTitle()) < 0) {
                return -1;
            } else if (this.title.compareTo(ar.getTitle()) > 0) {
                return 1;
            }
        } else if(op == 2){
            if (this.authors.compareTo(ar.getAuthors()) < 0) {
                return -1;
            } else if (this.authors.compareTo(ar.getAuthors()) > 0) {
                return 1;
            }
        } else{
            if (this.website.compareTo(ar.getWebsite()) < 0) {
                return -1;
            } else if (this.website.compareTo(ar.getWebsite()) > 0) {
                return 1;
            }
        }
        return 0;
    }

    public void setWebsite(String newWeb){
        this.website = newWeb;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setImage(String newImage){
        this.image = newImage;
    }

    public void setImg(int image) {
        this.img = image;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public void setAuthors(String newAuthors){
        this.authors = newAuthors;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }

    public String getWebsite(){
        return this.website;
    }

    public String getTitle(){
        return this.title;
    }

    public String getImage(){
        return this.image;
    }

    public int getImg() {
        return img;
    }

    public String getContent(){
        return this.content;
    }

    public String getAuthors(){
        return this.authors;
    }

    public String getDate(){
        return this.date;
    }

    public Article(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {

            return new Article[size];
        }

    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.website);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeInt(this.img);
        dest.writeString(this.content);
        dest.writeString(this.authors);
        dest.writeString(this.date);
    }

    public void readFromParcel(Parcel in) {
        this.website = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.img = in.readInt();
        this.content = in.readString();
        this.authors = in.readString();
        this.date = in.readString();
    }

}