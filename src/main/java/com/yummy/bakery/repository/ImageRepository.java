package com.yummy.bakery.repository;

import com.yummy.bakery.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alalwani on 03/09/17.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
