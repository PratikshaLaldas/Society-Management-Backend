package com.society.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
}
