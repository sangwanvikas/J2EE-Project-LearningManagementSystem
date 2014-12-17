package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tag_id")
	private int tagId;

	@Column(name="tag_text")
	private String tagText;

	//bi-directional many-to-many association to Thread
	
	@JsonIgnore
	@ManyToMany(mappedBy="tags", fetch=FetchType.EAGER)
	private List<Thread> threads;

	public Tag() {}
	
	public Tag(String tagText) {
		this.tagText= tagText;
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagText() {
		return this.tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

	public List<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tag [tagId=");
		builder.append(tagId);
		builder.append(", tagText=");
		builder.append(tagText);
		builder.append(", threads=");
		builder.append(threads.size());
		builder.append("]");
		return builder.toString();
	}

}