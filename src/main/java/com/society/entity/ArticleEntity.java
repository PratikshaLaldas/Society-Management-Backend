package com.society.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "article")

public class ArticleEntity {
	
  
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;
    private String title;
    private String description;
    private String addedBy;
    private LocalDate date;
    public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	  public ArticleEntity() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "ArticleEntity [articleId=" + articleId + ", title=" + title + ", description=" + description
					+ ", addedBy=" + addedBy + ", date=" + date + ", getArticleId()=" + getArticleId() + ", getTitle()="
					+ getTitle() + ", getDescription()=" + getDescription() + ", getAddedBy()=" + getAddedBy()
					+ ", getDate()=" + getDate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
					+ ", toString()=" + super.toString() + "]";
		}
		public ArticleEntity(Integer articleId, String title, String description, String addedBy, LocalDate date) {
			super();
			this.articleId = articleId;
			this.title = title;
			this.description = description;
			this.addedBy = addedBy;
			this.date = date;
		}

}

