package org.demo.myapp.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="book" )
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="id", nullable=false)
	private long id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="title")
	private String title ;

    @Column(name="price")
	private BigDecimal price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    
}
