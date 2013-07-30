package com.zyy.po;

import java.io.Serializable;
import java.util.Date;

public class Store implements Serializable{

	
	private String id;
	private String title;
	private String release_date;
	private String cover_URL;
	private boolean free;
	private String download_URL;
	private String folder;
	private String bundle_identifier;
	private String download_date;
	private String version;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String releaseDate) {
		release_date = releaseDate;
	}
	public String getCover_URL() {
		return cover_URL;
	}
	public void setCover_URL(String coverURL) {
		cover_URL = coverURL;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	public String getDownload_URL() {
		return download_URL;
	}
	public void setDownload_URL(String downloadURL) {
		download_URL = downloadURL;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getBundle_identifier() {
		return bundle_identifier;
	}
	public void setBundle_identifier(String bundleIdentifier) {
		bundle_identifier = bundleIdentifier;
	}
	public String getDownload_date() {
		return download_date;
	}
	public void setDownload_date(String downloadDate) {
		download_date = downloadDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((bundle_identifier == null) ? 0 : bundle_identifier
						.hashCode());
		result = prime * result
				+ ((cover_URL == null) ? 0 : cover_URL.hashCode());
		result = prime * result
				+ ((download_URL == null) ? 0 : download_URL.hashCode());
		result = prime * result
				+ ((download_date == null) ? 0 : download_date.hashCode());
		result = prime * result + ((folder == null) ? 0 : folder.hashCode());
		result = prime * result + (free ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((release_date == null) ? 0 : release_date.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		if (bundle_identifier == null) {
			if (other.bundle_identifier != null)
				return false;
		} else if (!bundle_identifier.equals(other.bundle_identifier))
			return false;
		if (cover_URL == null) {
			if (other.cover_URL != null)
				return false;
		} else if (!cover_URL.equals(other.cover_URL))
			return false;
		if (download_URL == null) {
			if (other.download_URL != null)
				return false;
		} else if (!download_URL.equals(other.download_URL))
			return false;
		if (download_date == null) {
			if (other.download_date != null)
				return false;
		} else if (!download_date.equals(other.download_date))
			return false;
		if (folder == null) {
			if (other.folder != null)
				return false;
		} else if (!folder.equals(other.folder))
			return false;
		if (free != other.free)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (release_date == null) {
			if (other.release_date != null)
				return false;
		} else if (!release_date.equals(other.release_date))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Store [bundle_identifier=" + bundle_identifier + ", cover_URL="
				+ cover_URL + ", download_URL=" + download_URL
				+ ", download_date=" + download_date + ", folder=" + folder
				+ ", free=" + free + ", id=" + id + ", release_date="
				+ release_date + ", title=" + title + ", version=" + version
				+ "]";
	}
	
	
}
