package org.demo.myapp.persistence.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPA entity class for "Ticket"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="ticket" )
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Ticket_Gen")
    @SequenceGenerator(name="Ticket_Gen", sequenceName="Ticket_Seq")
    @Column(name="id", nullable=false)
    private long       id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="title")
    private String     title ;

    //--- ENTITY LINKS ( RELATIONSHIP )

    /**
     * Constructor
     */
    public Ticket() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setId( long id ) {
        this.id = id ;
    }
    public long getId() {
        return this.id;
    }

    public void setTitle( String v ) {
        this.title = v ;
    }
    public String getTitle() {
        return this.title;
    }


    //--- GETTERS FOR LINKS
    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(title);
        return sb.toString(); 
    } 

}
