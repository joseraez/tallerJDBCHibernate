package org.springframework.samples.petclinic.owner;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;

@Entity
@Table(name = "facturas")
public class Bill /*extends BaseEntity*/ { //No por limitaciones de tamaño

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Digits(integer=10, fraction=0)
	private Long id;
	
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date fechaPago;

	@Digits(integer=10, fraction=2)
	private Float cuantia;
    
	/*
    @ManyToOne 
    @JoinColumn(name = "owner_id")
    private Owner owner;
    */
    @OneToOne (mappedBy = "bill")
    private Visit visit;

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Float getCuantia() {
		return cuantia;
	}

	public void setCuantia(Float cuantia) {
		this.cuantia = cuantia;
	}
	/*
	public Owner getOwner() {
		return owner;
	}
	*/
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
    
    
}