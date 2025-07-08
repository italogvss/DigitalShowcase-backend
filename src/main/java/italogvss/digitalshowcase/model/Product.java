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

@Table(name = "product")
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idproduct")
    private Long idProduct;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idpricetable", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private PriceTable pricetable;

    public Product(Long id, Long idProduct, String name, Float price, User user, PriceTable pricetable) {
        this.id = id;
        this.idProduct = idProduct;
        this.user = user;
        this.name = name;
        this.price = price;
        this.pricetable = pricetable;
    }

    public Product() {

    }

    public PriceTable getPricetable() {
        return pricetable;
    }

    public void setPricetable(PriceTable pricetable) {
        this.pricetable = pricetable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User idUser) {
        this.user = idUser;
    }

    public Long getId() {
        return id;
    }

}