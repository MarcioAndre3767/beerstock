package com.beerstock.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerstock.dto.BeerDTO;
import com.beerstock.entity.Beer;
import com.beerstock.exception.BeerAlreadyRegisteredException;
import com.beerstock.exception.BeerNotFoundException;
import com.beerstock.exception.BeerStockExceededException;
import com.beerstock.mapper.BeerMapper;
import com.beerstock.repository.BeerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {
	
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper = BeerMapper.INSTANCE;
	
	
	
	//created beer
	public BeerDTO createBeer( BeerDTO beerDTO ) throws BeerAlreadyRegisteredException{
		verifyIfIsAlreadyRegistered( beerDTO.getName() );
		
		Beer beer = beerMapper.toModel(beerDTO);
		Beer savedBeer = beerRepository.save(beer);
		
		return beerMapper.toDTO(savedBeer);
	}
	
	
	//find name
	public BeerDTO findByName( String name ) throws BeerNotFoundException {
		Beer foundBeer = beerRepository.findByName(name)
							.orElseThrow( () -> new BeerNotFoundException(name) );
		
		return beerMapper.toDTO(foundBeer);
	}
	
	
	//List All
	public java.util.List<BeerDTO> listAll() {
		return beerRepository.findAll()
					.stream()
					.map( beerMapper::toDTO )
					.collect(Collectors.toList());
	}
	
	
	// delete by id
	public void deleteById( Long id ) throws BeerNotFoundException {
		verifyIfExists(id);
		beerRepository.deleteById(id);
	}
	
	
	
	private void verifyIfIsAlreadyRegistered( String name ) throws BeerAlreadyRegisteredException {
		Optional<Beer> optSavedBeer = beerRepository.findByName(name);
		if( optSavedBeer.isPresent() ) {
			throw new BeerAlreadyRegisteredException( name );
		}
	}
	
	
	private Beer verifyIfExists( Long id ) throws BeerNotFoundException {
		
		return beerRepository.findById(id)
							 .orElseThrow( () -> new BeerNotFoundException(id) );
	}
	
	
	public BeerDTO increment( Long id, int quantityToIncrement ) throws BeerNotFoundException, 
											BeerStockExceededException {
		Beer beerToIncrementStock = verifyIfExists(id);
		int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
		
		if ( quantityAfterIncrement <= beerToIncrementStock.getMax()) {
			beerToIncrementStock.setQuantity( beerToIncrementStock.getQuantity() + quantityToIncrement );
			Beer incremetedBeerStock = beerRepository.save( beerToIncrementStock );
			
			return beerMapper.toDTO(incremetedBeerStock);
		}
		
		throw new BeerStockExceededException(id, quantityToIncrement);
				
	}

	
	
	
	

}



























