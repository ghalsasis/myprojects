/**
 * 
 */
package com.example.xchgratedemo.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author supri
 *
 */
@Entity
@Table(name="XCHGRT")
public class XchgRate {


	public XchgRate() {

    }
    
    public XchgRate(int id, Date dt, String currency, BigDecimal xchgrate) {
        super();
        this.id = id;
        this.dt = dt;
        this.currency = currency;
        this.xchgrate = xchgrate;
    }
    
    @Id
    @Column(name = "ID", unique = true, nullable = false)
	private int id;
    
    @Column(name = "DT")
	private Date dt;
    
    @Column(name = "CURRENCY")
	private String currency;
    
    @Column(precision=10, scale=4, name = "XCHGRATE")
	private BigDecimal xchgrate;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return dt;
	}

	public void setDate(Date dt) {
		this.dt = dt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getXchgrate() {
		return xchgrate;
	}

	public void setXchgrate(BigDecimal xchgrate) {
		this.xchgrate = xchgrate;
	}
	
    @Override
    public String toString() {
        return "Xchgrate [id=" + id + ", currency=" +  currency + ", Value=" + xchgrate + ", for date =" + dt + "]";
    }


}
