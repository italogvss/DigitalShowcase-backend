package italogvss.digitalshowcase.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
public class Media implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmedia")
	private Long id;

	@Column(nullable = false, length = 100)
	private String description;

	@Column(nullable = false, length = 20)
	private String type;

	@Column(nullable = false, length = 20)
	private Long size;

	@Column(nullable = false, length = 150)
	private String url;

	@Column(nullable = true, length = 150)
	private String urlPreview;

	@Column(nullable = false, length = 10)
	private Integer  sec;

	@ManyToOne
	@JoinColumn(name = "iduser", nullable = false)
	private User user;

	public Media() {
	}

	
	public Integer getSec() {
		return sec;
	}


	public void setSec(Integer sec) {
		this.sec = sec;
	}


	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setDescription(String name) {
		this.description = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String directory) {
		this.url = directory;
	}

	public String getUrlPreview() {
		return urlPreview;
	}

	public void setUrlPreview(String thumbnailDirectory) {
		this.urlPreview = thumbnailDirectory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlPreview == null) ? 0 : urlPreview.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Media other = (Media) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (urlPreview == null) {
			if (other.urlPreview != null)
				return false;
		} else if (!urlPreview.equals(other.urlPreview))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}