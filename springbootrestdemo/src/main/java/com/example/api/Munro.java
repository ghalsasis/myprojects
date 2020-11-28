package com.example.api;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity

@Table(name="MUNRO")
public class Munro {

	public Munro() {

    }
    
    public Munro(int id, String name, double heightM, String category, String gridRef) {
        super();
        this.id = id;
        this.name = name;
        this.heightM = heightM;
        this.category = category;
        this.gridRef = gridRef;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private int id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "HEIGHTM")
    private double heightM;
    
    @Column(name = "CATEGORY")
    private String category;
    
    @Column(name = "GRID_REF")
    private String gridRef;

   

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public double getheightM() {
        return heightM;
    }

    public void setheightM(int heightM) {
        this.heightM = heightM;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }
    
    public String getgridRef() {
        return gridRef;
    }

    public void setgridRef(String gridRef) {
        this.gridRef = gridRef;
    }
    
    public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return "Munro [id=" + id + ", name=" +  name + ", heightM=" + heightM + ", category=" + category + ", gridRef=" + gridRef + "]";
    }
}
