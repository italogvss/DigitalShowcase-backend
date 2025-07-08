package italogvss.digitalshowcase.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "price_table_layout")
@Entity(name = "price_table_layout")
public class PriceTableLayout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_playlist", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Playlist playlist;

  @ManyToOne
  @JoinColumn(name = "id_priceTable", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PriceTable priceTable;

  @Column(nullable = false, columnDefinition = "TEXT")
  @Lob
  private String html;

  @Column(nullable = false)
  private Integer part;
  
  @Column(nullable = false)
  private Integer orderIndex;

  public PriceTableLayout() {

  }

  public PriceTableLayout(Long id, Playlist playlist, PriceTable priceTable, String html, Integer part, Integer orderIndex) {
    this.id = id;
    this.playlist = playlist;
    this.priceTable = priceTable;
    this.html = html;
    this.part = part;
    this.orderIndex = orderIndex;
  }

  public Long getId() {
    return id;
  }

  

  public void setId(Long id) {
    this.id = id;
  }

  public Playlist getPlaylist() {
    return playlist;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  public PriceTable getPriceTable() {
    return priceTable;
  }

  public void setPriceTable(PriceTable priceTable) {
    this.priceTable = priceTable;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public Integer getPart() {
    return part;
  }

  public void setPart(Integer part) {
    this.part = part;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
    result = prime * result + ((priceTable == null) ? 0 : priceTable.hashCode());
    result = prime * result + ((html == null) ? 0 : html.hashCode());
    result = prime * result + ((part == null) ? 0 : part.hashCode());
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
    PriceTableLayout other = (PriceTableLayout) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (playlist == null) {
      if (other.playlist != null)
        return false;
    } else if (!playlist.equals(other.playlist))
      return false;
    if (priceTable == null) {
      if (other.priceTable != null)
        return false;
    } else if (!priceTable.equals(other.priceTable))
      return false;
    if (html == null) {
      if (other.html != null)
        return false;
    } else if (!html.equals(other.html))
      return false;
    if (part == null) {
      if (other.part != null)
        return false;
    } else if (!part.equals(other.part))
      return false;
    return true;
  }

  public Integer getOrder() {
    return orderIndex;
  }

  public void setOrder(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

}
