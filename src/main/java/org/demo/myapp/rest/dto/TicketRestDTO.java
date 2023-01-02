package org.demo.myapp.rest.dto;

/**
 *
 */
public class TicketRestDTO {
	
    private long       id ;
    private String     title ;

    /**
     * Constructor
     */
    public TicketRestDTO() {
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

}
