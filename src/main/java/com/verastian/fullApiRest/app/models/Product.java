package com.verastian.fullApiRest.app.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 4,max = 255, message = "El Nombre debe tener entre 4 y 255 caracteres")
	private String name;
	
	@Size(max = 255, message = "La Descripcion no puede superar los 255 caracteres")
	private String description;
	
	@Min(value = 0, message = "El valor del Precio debe ser mayor o igual a 0")
	private double precio;
	
	@Min(value = 0, message = "El valor del Stock debe ser mayor o igual a 0")
	private long stock;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)//LAZY:Evitar se carge al realizar las consultas PERSIST: que en cascada un producto sin presentacion se guarde igual
	@NotNull(message = "EL campo Presentacion no debe ser Nulo")
	private Presentation presentation;
}
