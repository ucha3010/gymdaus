package com.gymdaus.core.service;



import com.gymdaus.core.model.CountryModel;

import java.util.List;

public interface CountryService {

    List<CountryModel> findAll();

    CountryModel findById(Long id);

    void add(CountryModel countryModel);

    void update(CountryModel countryModel);

    void delete(Long idCountry);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();
}
