package italogvss.digitalshowcase.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "template")
@Entity(name = "template")
public class Template {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(name = "num_rows", nullable = false)
  private int rows;
  @Column(nullable = false)
  private int columns;

  @ManyToOne
  @JoinColumn(name = "iduser", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  // colors
  private String color1;
  @Column(nullable = false)
  private String color2;
  @Column(nullable = false)
  private String textColor;
  @Column(nullable = false)
  private String priceColor;
  @Column(nullable = false)
  private boolean isTranslucid;
  @Column(nullable = false)
  private int translucid;
  @Column(nullable = false)
  private boolean isStriped;
  @Column(nullable = false)

  // text
  private int textSize;
  @Column(nullable = false)
  private String textPos;
  @Column(nullable = false)
  private String pricePos;
  @Column(nullable = false)
  private String cipherPos;
  @Column(nullable = false)
  private String cipher;
  @Column(nullable = false)
  private boolean showName;
  @Column(nullable = false)

  // image
  private boolean haveImage;
  @Column(nullable = false)
  private int imagePos;
  @Column(nullable = false)
  private int imageSize;
  @ManyToOne
  @JoinColumn(name = "idImage", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Media image;
  @Column(nullable = false)
  private boolean haveBackground;
  @ManyToOne
  @JoinColumn(name = "idBackground", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Media background;
  // gri
  @Column(nullable = false)
  private boolean haveGrid;
  @Column(nullable = false)
  private String grid;
  @Column(nullable = false)
  private String gridColor;
  @Column(nullable = false)
  private Boolean isDefault;


  
  public Template() {
  }
  
  
  public Template(Long id, String name, int rows, int columns, User user, String color1, String color2,
      String textColor, String priceColor, boolean isTranslucid, int translucid, boolean isStriped, int textSize,
      String textPos, String pricePos, String cipherPos, String cipher, boolean haveImage, int imagePos, int imageSize,
      Media image, boolean haveBackground, Media background, boolean haveGrid, String grid, String gridColor,
      Boolean isDefault, Boolean showName) {
    this.id = id;
    this.name = name;
    this.rows = rows;
    this.columns = columns;
    this.user = user;
    this.color1 = color1;
    this.color2 = color2;
    this.textColor = textColor;
    this.priceColor = priceColor;
    this.isTranslucid = isTranslucid;
    this.translucid = translucid;
    this.isStriped = isStriped;
    this.textSize = textSize;
    this.textPos = textPos;
    this.pricePos = pricePos;
    this.cipherPos = cipherPos;
    this.cipher = cipher;
    this.haveImage = haveImage;
    this.imagePos = imagePos;
    this.imageSize = imageSize;
    this.image = image;
    this.haveBackground = haveBackground;
    this.background = background;
    this.haveGrid = haveGrid;
    this.grid = grid;
    this.gridColor = gridColor;
    this.isDefault = isDefault;
    this.showName = showName;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getRows() {
    return rows;
  }
  public void setRows(int rows) {
    this.rows = rows;
  }
  public int getColumns() {
    return columns;
  }
  public void setColumns(int columns) {
    this.columns = columns;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public String getColor1() {
    return color1;
  }
  public void setColor1(String color1) {
    this.color1 = color1;
  }
  public String getColor2() {
    return color2;
  }
  public void setColor2(String color2) {
    this.color2 = color2;
  }
  public String getTextColor() {
    return textColor;
  }
  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }
  public String getPriceColor() {
    return priceColor;
  }
  public void setPriceColor(String priceColor) {
    this.priceColor = priceColor;
  }
  public boolean isTranslucid() {
    return isTranslucid;
  }
  public void setTranslucid(boolean isTranslucid) {
    this.isTranslucid = isTranslucid;
  }
  public int getTranslucid() {
    return translucid;
  }
  public void setTranslucid(int translucid) {
    this.translucid = translucid;
  }
  public boolean isStriped() {
    return isStriped;
  }
  public void setStriped(boolean isStriped) {
    this.isStriped = isStriped;
  }
  public int getTextSize() {
    return textSize;
  }
  public void setTextSize(int textSize) {
    this.textSize = textSize;
  }
  public String getTextPos() {
    return textPos;
  }
  public void setTextPos(String textPos) {
    this.textPos = textPos;
  }
  public String getPricePos() {
    return pricePos;
  }
  public void setPricePos(String pricePos) {
    this.pricePos = pricePos;
  }
  public String getCipherPos() {
    return cipherPos;
  }
  public void setCipherPos(String cipherPos) {
    this.cipherPos = cipherPos;
  }
  public String getCipher() {
    return cipher;
  }
  public void setCipher(String cipher) {
    this.cipher = cipher;
  }
  public boolean isHaveImage() {
    return haveImage;
  }
  public void setHaveImage(boolean haveImage) {
    this.haveImage = haveImage;
  }
  public int getImagePos() {
    return imagePos;
  }
  public void setImagePos(int imagePos) {
    this.imagePos = imagePos;
  }
  public int getImageSize() {
    return imageSize;
  }
  public void setImageSize(int imageSize) {
    this.imageSize = imageSize;
  }
  public Media getImage() {
    return image;
  }
  public void setImage(Media image) {
    this.image = image;
  }
  public boolean isHaveBackground() {
    return haveBackground;
  }
  public void setHaveBackground(boolean haveBackground) {
    this.haveBackground = haveBackground;
  }
  public Media getBackground() {
    return background;
  }
  public void setBackground(Media background) {
    this.background = background;
  }
  public boolean isHaveGrid() {
    return haveGrid;
  }
  public void setHaveGrid(boolean haveGrid) {
    this.haveGrid = haveGrid;
  }
  public String getGrid() {
    return grid;
  }
  public void setGrid(String grid) {
    this.grid = grid;
  }
  public String getGridColor() {
    return gridColor;
  }
  public void setGridColor(String gridColor) {
    this.gridColor = gridColor;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + rows;
    result = prime * result + columns;
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    result = prime * result + ((color1 == null) ? 0 : color1.hashCode());
    result = prime * result + ((color2 == null) ? 0 : color2.hashCode());
    result = prime * result + ((textColor == null) ? 0 : textColor.hashCode());
    result = prime * result + ((priceColor == null) ? 0 : priceColor.hashCode());
    result = prime * result + (isTranslucid ? 1231 : 1237);
    result = prime * result + translucid;
    result = prime * result + (isStriped ? 1231 : 1237);
    result = prime * result + textSize;
    result = prime * result + ((textPos == null) ? 0 : textPos.hashCode());
    result = prime * result + ((pricePos == null) ? 0 : pricePos.hashCode());
    result = prime * result + ((cipherPos == null) ? 0 : cipherPos.hashCode());
    result = prime * result + ((cipher == null) ? 0 : cipher.hashCode());
    result = prime * result + (haveImage ? 1231 : 1237);
    result = prime * result + imagePos;
    result = prime * result + imageSize;
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    result = prime * result + (haveBackground ? 1231 : 1237);
    result = prime * result + ((background == null) ? 0 : background.hashCode());
    result = prime * result + (haveGrid ? 1231 : 1237);
    result = prime * result + ((grid == null) ? 0 : grid.hashCode());
    result = prime * result + ((gridColor == null) ? 0 : gridColor.hashCode());
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
    Template other = (Template) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (rows != other.rows)
      return false;
    if (columns != other.columns)
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    if (color1 == null) {
      if (other.color1 != null)
        return false;
    } else if (!color1.equals(other.color1))
      return false;
    if (color2 == null) {
      if (other.color2 != null)
        return false;
    } else if (!color2.equals(other.color2))
      return false;
    if (textColor == null) {
      if (other.textColor != null)
        return false;
    } else if (!textColor.equals(other.textColor))
      return false;
    if (priceColor == null) {
      if (other.priceColor != null)
        return false;
    } else if (!priceColor.equals(other.priceColor))
      return false;
    if (isTranslucid != other.isTranslucid)
      return false;
    if (translucid != other.translucid)
      return false;
    if (isStriped != other.isStriped)
      return false;
    if (textSize != other.textSize)
      return false;
    if (textPos == null) {
      if (other.textPos != null)
        return false;
    } else if (!textPos.equals(other.textPos))
      return false;
    if (pricePos == null) {
      if (other.pricePos != null)
        return false;
    } else if (!pricePos.equals(other.pricePos))
      return false;
    if (cipherPos == null) {
      if (other.cipherPos != null)
        return false;
    } else if (!cipherPos.equals(other.cipherPos))
      return false;
    if (cipher == null) {
      if (other.cipher != null)
        return false;
    } else if (!cipher.equals(other.cipher))
      return false;
    if (haveImage != other.haveImage)
      return false;
    if (imagePos != other.imagePos)
      return false;
    if (imageSize != other.imageSize)
      return false;
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    if (haveBackground != other.haveBackground)
      return false;
    if (background == null) {
      if (other.background != null)
        return false;
    } else if (!background.equals(other.background))
      return false;
    if (haveGrid != other.haveGrid)
      return false;
    if (grid == null) {
      if (other.grid != null)
        return false;
    } else if (!grid.equals(other.grid))
      return false;
    if (gridColor == null) {
      if (other.gridColor != null)
        return false;
    } else if (!gridColor.equals(other.gridColor))
      return false;
    return true;
  }


  public Boolean getIsDefault() {
    return isDefault;
  }


  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }


  public boolean isShowName() {
    return showName;
  }


  public void setShowName(boolean showName) {
    this.showName = showName;
  }

  


}
