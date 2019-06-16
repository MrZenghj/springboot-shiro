package com.example.demo.repo;

import com.example.demo.entity.QuartzConfig;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuartzConfigRepo extends JpaRepository<QuartzConfig,String>{
}
