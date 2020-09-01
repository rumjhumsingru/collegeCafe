package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.Announcement;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long>{

}
