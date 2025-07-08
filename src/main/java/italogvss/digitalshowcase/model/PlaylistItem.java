package italogvss.digitalshowcase.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "playlist_item")
@Entity(name = "playlist_item")
public class PlaylistItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media_id", nullable = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Media media;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "priceTable_id", nullable = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PriceTable priceTable;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "template_id", nullable = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Template template;

  @Column(name="item_order",nullable = false)
  private int order;

  @Column(nullable = false)
  private int seconds;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "playlist_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Playlist playlist;

  
  
  @Override
  public String toString() {
    return "PlaylistItem [id=" + id + ", type=" + type + ", media=" + media + ", priceTable=" + priceTable
        + ", template=" + template + ", order=" + order + ", seconds=" + seconds;
  }

  public PlaylistItem() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

  public PriceTable getPriceTable() {
    return priceTable;
  }

  public void setPriceTable(PriceTable priceTable) {
    this.priceTable = priceTable;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getSeconds() {
    return seconds;
  }

  public void setSeconds(int seconds) {
    this.seconds = seconds;
  }

  public Playlist getPlaylist() {
    return playlist;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((media == null) ? 0 : media.hashCode());
    result = prime * result + ((priceTable == null) ? 0 : priceTable.hashCode());
    result = prime * result + ((template == null) ? 0 : template.hashCode());
    result = prime * result + order;
    result = prime * result + seconds;
    result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
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
    PlaylistItem other = (PlaylistItem) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (media == null) {
      if (other.media != null)
        return false;
    } else if (!media.equals(other.media))
      return false;
    if (priceTable == null) {
      if (other.priceTable != null)
        return false;
    } else if (!priceTable.equals(other.priceTable))
      return false;
    if (template == null) {
      if (other.template != null)
        return false;
    } else if (!template.equals(other.template))
      return false;
    if (order != other.order)
      return false;
    if (seconds != other.seconds)
      return false;
    if (playlist == null) {
      if (other.playlist != null)
        return false;
    } else if (!playlist.equals(other.playlist))
      return false;
    return true;
  }

  
}
