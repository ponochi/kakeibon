package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository<S> extends JpaRepository<Shop, Long> {

    List<Shop> findAll();

    S findByShopId(Long id);

    Shop saveAndFlush(Shop entity);
}
