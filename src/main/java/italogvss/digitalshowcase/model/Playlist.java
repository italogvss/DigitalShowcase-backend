package italogvss.digitalshowcase.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;

@Table(name = "playlist")
@Entity(name = "playlist")
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "iduser", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true, unique = true, length = 5)
    private String code;

  @Column(nullable = false)
  private boolean isTimeFixed;

  @Column(nullable = false)
  private Integer isVertical;

  @Column(nullable = false)
  private boolean isTemplateFixed;

  @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PlaylistItem> items = new ArrayList<>();

  
  @Override
  public String toString() {
    return "Playlist [id=" + id + ", user=" + user + ", name=" + name + ", code=" + code + ", isTimeFixed="
        + isTimeFixed + ", isVertical=" + isVertical + ", isTemplateFixed=" + isTemplateFixed + ", items=" + items
        + "]";
  }

  @PostPersist
    public void generateCode() {
        this.code = String.format("%05d", this.id);
    }


  public Playlist() {
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean isTimeFixed() {
    return isTimeFixed;
  }

  public void setTimeFixed(boolean isTimeFixed) {
    this.isTimeFixed = isTimeFixed;
  }

  public boolean isTemplateFixed() {
    return isTemplateFixed;
  }

  

  public void setTemplateFixed(boolean isTemplateFixed) {
    this.isTemplateFixed = isTemplateFixed;
  }

  public List<PlaylistItem> getItems() {
    return items;
  }

  public void setItems(List<PlaylistItem> items) {
    this.items = items;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Integer isVertical() {
    return isVertical;
  }

  public void setVertical(Integer isVertical) {
    this.isVertical = isVertical;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + (isTimeFixed ? 1231 : 1237);
    result = prime * result + ((isVertical == null) ? 0 : isVertical.hashCode());
    result = prime * result + (isTemplateFixed ? 1231 : 1237);
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    Playlist other = (Playlist) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (isTimeFixed != other.isTimeFixed)
      return false;
    if (isVertical != other.isVertical)
      return false;
    if (isTemplateFixed != other.isTemplateFixed)
      return false;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

  
  
}
