package com.yummy.bakery.service.impl;

import com.yummy.bakery.entity.Image;
import com.yummy.bakery.repository.ImageRepository;
import com.yummy.bakery.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alalwani on 03/09/17.
 */
@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image getById(long id) {
        return imageRepository.findOne(id);
    }
}
