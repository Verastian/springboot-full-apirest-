package com.verastian.fullApiRest.app.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Presentation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 4,max = 255, message = "El Nombre debe tener entre 4 y 255 caracteres")
	private String name;
	
	@Size(max = 255, message = "La Descripcion no puede superar los 255 caracteres")
	private String description;
	
	@JsonIgnore//se agrego por que al agregar los getter and setter con lombok los aagrega a todos y debemos ignorar para este atributo 
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE,mappedBy = "presentation")
	private List<Product> products;
}
