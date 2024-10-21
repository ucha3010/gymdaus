package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Country;
import com.gymdaus.core.mapper.MapperCountry;
import com.gymdaus.core.model.CountryModel;
import com.gymdaus.core.repository.CountryRepository;
import com.gymdaus.core.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MapperCountry mapperCountry;

    @Override
    public List<CountryModel> findAll() {
        List<CountryModel> countryModelList = new ArrayList<>();
        for (Country country: countryRepository.findAllByOrderByPositionAsc()) {
            countryModelList.add(mapperCountry.entity2Model(country));
        }
        return countryModelList;
    }

    @Override
    public CountryModel findById(Long id) {
        try {
            return mapperCountry.entity2Model(countryRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new CountryModel();
        }
    }

    @Override
    public void add(CountryModel countryModel) {
        countryRepository.save(mapperCountry.model2Entity(countryModel));
    }

    @Override
    public void update(CountryModel countryModel) {
        countryRepository.save(mapperCountry.model2Entity(countryModel));
    }

    @Override
    public void delete(Long idCountry) {
        countryRepository.deleteById(idCountry);
        List<Country> countryList = countryRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getPosition() != i) {
                countryList.get(i).setPosition(i);
                countryRepository.save(countryList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        Country country = countryRepository.findByPosition(initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(i, false);
            }
        }
        country.setPosition(finalPosition);
        countryRepository.save(country);
    }

    @Override
    public int findMaxPosition() {
        Country country = countryRepository.findTopByOrderByPositionDesc();
        if (country != null) {
            return country.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        Country country = countryRepository.findByPosition(position);
        country.setPosition(position + (moveUp ? 1 : -1));
        countryRepository.save(country);
    }
}
