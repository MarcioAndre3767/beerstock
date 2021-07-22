package com.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus( HttpStatus.BAD_REQUEST )
public class BeerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	
	
	public BeerNotFoundException( String beerName )  {
		super( String.format( "Beers with %s not found in the system.", beerName));
	}



	public BeerNotFoundException(Long id) {
		super( String.format( "Beer with id %s not found in the system.", id ));
	}
	


}
