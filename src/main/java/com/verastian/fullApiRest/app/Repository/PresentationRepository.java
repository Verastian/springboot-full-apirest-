package com.verastian.fullApiRest.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verastian.fullApiRest.app.models.Presentation;

public interface PresentationRepository  extends JpaRepository<Presentation, Long>{

}
