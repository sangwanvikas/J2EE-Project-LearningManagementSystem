package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@Cacheable(false)
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="post_id")
	private int postId;

	@Lob
	@Column(name="post_content")
	private String postContent;

	@Temporal(TemporalType.DATE)
	@Column(name="post_date")
	private Date postDate;

	@Column(name="post_time")
	private Time postTime;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="thread_id")
	private int threadId;

	

	//bi-directional many-to-one association to Thread
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="thread_id",insertable=false,updatable=false)
	private Thread thread;

	@JsonIgnore
	//bi-directional many-to-one association to Thread
	@ManyToOne
	@JoinColumn(name="user_id",insertable=false,updatable=false)
	private User user;
	
	

    
	//bi-directional many-to-many association to Tag
		@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH})
		@JoinTable(name="user_like_post", 
					joinColumns=@JoinColumn (name="post_id"),
					inverseJoinColumns=@JoinColumn(name="user_id"))
		private List<User> usersWhoHaveLikedPosts;
	

	public Post() {
	}
	
	public Post(int postId, String postContent, Date postDate, Time postTime,
			int userId,int threadId, Thread thread) {
		super();
		this.postId = postId;
		this.postContent = postContent;
		this.postDate = postDate;
		this.postTime = postTime;
		this.userId = userId;
		this.threadId=threadId;
		this.thread = thread;
	}

	public Post(String postContent, Date postDate, Time postTime, int userId,
			int threadId,Thread thread) {
		super();
		this.postContent = postContent;
		this.postDate = postDate;
		this.postTime = postTime;
		this.userId = userId;
		this.threadId=threadId;
		this.thread = thread;
	}
	
	public Post(String postContent, Date postDate, Time postTime, int userId,
			int threadId) {
		super();
		this.postContent = postContent;
		this.postDate = postDate;
		this.postTime = postTime;
		this.userId = userId;
		this.threadId=threadId;
	}

	public int getPostId() {
		return this.postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostContent() {
		return this.postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Time getPostTime() {
		return this.postTime;
	}

	public void setPostTime(Time postTime) {
		this.postTime = postTime;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Thread getThread() {
		return this.thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	
	
	public List<User> getUsersWhoHaveLikedPosts() {
		return usersWhoHaveLikedPosts;
	}

	public void setUsersWhoHaveLikedPosts(List<User> usersWhoHaveLikedPosts) {
		this.usersWhoHaveLikedPosts = usersWhoHaveLikedPosts;
	}


}