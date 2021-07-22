package com.beerstock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.beerstock.enums.BeerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data // Get, Set e equals and has
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable = false, unique = true) // cadastro único de cerveja
	private String name; //marca
	
	
	@Column(nullable = false) 
	private String brand; //marca
	
	
	@Column(nullable = false)
	private int max; 
	
	
	@Column(nullable = false)
	private int quantity; 
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BeerType type; 	

}


























