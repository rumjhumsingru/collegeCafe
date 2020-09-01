package com.cafeteria.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafeteria.domain.Announcement;
import com.cafeteria.repository.AnnouncementRepository;
import com.cafeteria.service.AnnouncementService;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{
	
	@Autowired
	private AnnouncementRepository announcementRepository;
		
	public List<Announcement> findAll(){
		return (List<Announcement>) announcementRepository.findAll();
	}
	
	
	
}
