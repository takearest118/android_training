package com.topicinside.girlsday;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
	
	private String id;
	private String source;
	private String picture;
	private String writerId;
	private String writerName;
	private String writerPhoto;
	private String createdtime;
	private String name;
	private String link;
	/*
	private int countLike;
	private int countComment;
	private String url;
	*/

	public Image(String id) {
		super();
		this.id = id;
	}
	
	public Image(String id, String source, String picture) {
		super();
		this.id = id;
		this.source = source;
		this.picture = picture;
		this.writerId = writerId;
		this.writerName = writerName;
		this.writerPhoto = writerPhoto;
		this.createdtime = createdtime;
		this.name = name;
	}
	
	public Image(String id, String source, String picture, 
			String writerId, String writerName,
			String writerPhoto, String createdtime, String name) {
		super();
		this.id = id;
		this.source = source;
		this.picture = picture;
		this.writerId = writerId;
		this.writerName = writerName;
		this.writerPhoto = writerPhoto;
		this.createdtime = createdtime;
		this.name = name;
	}
	public Image(String id, String source, String picture, 
			String createdtime, String name) {
		super();
		this.id = id;
		this.source = source;
		this.picture = picture;
		this.writerId = writerId;
		this.writerName = writerName;
		this.writerPhoto = writerPhoto;
		this.createdtime = createdtime;
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Image(Parcel in) {
		this.readFromParcel(in);
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getWriterPhoto() {
		return writerPhoto;
	}

	public void setWriterPhoto(String writerPhoto) {
		this.writerPhoto = writerPhoto;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	public int getCountLike() {
		return countLike;
	}

	public void setCountLike(int countLike) {
		this.countLike = countLike;
	}

	public int getCountComment() {
		return countComment;
	}

	public void setCountComment(int countComment) {
		this.countComment = countComment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	*/

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(source);
		dest.writeString(picture);
		dest.writeString(writerId);
		dest.writeString(writerName);
		dest.writeString(writerPhoto);
		dest.writeString(createdtime);
		dest.writeString(name);
	}
	
	public void readFromParcel(Parcel in) {
		id = in.readString();
		source = in.readString();
		picture = in.readString();
		writerId = in.readString();
		writerName = in.readString();
		writerPhoto = in.readString();
		createdtime = in.readString();
		name = in.readString();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Image createFromParcel(Parcel in) {
             return new Image(in);
       }

       public Image[] newArray(int size) {
            return new Image[size];
       }
   };
}
