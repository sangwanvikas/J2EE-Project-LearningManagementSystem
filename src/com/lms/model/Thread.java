package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the thread database table.
 * 
 */
@Entity
@Cacheable(false)
@NamedQuery(name="Thread.findAll", query="SELECT t FROM Thread t")
public class Thread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="thread_id")
	private int threadId;

	@Column(name="course_id")
	private int courseId;

	@Lob
	@Column(name="thread_content")
	private String threadContent;

	@Temporal(TemporalType.DATE)
	@Column(name="thread_date")
	private Date threadDate;

	@Column(name="thread_time")
	private Time threadTime;

	@Column(name="thread_title")
	private String threadTitle;

	@Column(name="user_id")
	private int userId;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="thread", fetch=FetchType.EAGER)
	private List<Post> posts;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id",insertable=false,updatable=false)
	private User user;
	
	//bi-directional many-to-many association to Tag
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name="thread_tag", 
				joinColumns=@JoinColumn (name="thread_id"),
				inverseJoinColumns=@JoinColumn(name="tag_id"))
	private List<Tag> tags;
	
	//@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinTable(name="fav_thread", 
				joinColumns=@JoinColumn (name="thread_id"),
				inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> favUsers;
	
	public Thread() {
	}
	
	public Thread(int threadId, int courseId, String threadContent,
			Date threadDate, Time threadTime, String threadTitle, int userId,
			List<Post> posts, List<Tag> tags) {
		super();
		this.threadId = threadId;
		this.courseId = courseId;
		this.threadContent = threadContent;
		this.threadDate = threadDate;
		this.threadTime = threadTime;
		this.threadTitle = threadTitle;
		this.userId = userId;
		this.posts = posts;
		this.tags = tags;
	}

	public Thread(int courseId, String threadContent, Date threadDate,
			Time threadTime, String threadTitle, int userId, List<Post> posts, List<Tag> tags) {
		super();
		this.courseId = courseId;
		this.threadContent = threadContent;
		this.threadDate = threadDate;
		this.threadTime = threadTime;
		this.threadTitle = threadTitle;
		this.userId = userId;
		this.posts = posts;
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public int getThreadId() {
		return this.threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getThreadContent() {
		return this.threadContent;
	}

	public void setThreadContent(String threadContent) {
		this.threadContent = threadContent;
	}

	public Date getThreadDate() {
		return this.threadDate;
	}

	public void setThreadDate(Date threadDate) {
		this.threadDate = threadDate;
	}

	public Time getThreadTime() {
		return this.threadTime;
	}

	public void setThreadTime(Time threadTime) {
		this.threadTime = threadTime;
	}

	public String getThreadTitle() {
		return this.threadTitle;
	}

	public void setThreadTitle(String threadTitle) {
		this.threadTitle = threadTitle;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<User> getFavUsers() {
		return favUsers;
	}

	public void setFavUsers(List<User> favUsers) {
		this.favUsers = favUsers;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setThread(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setThread(null);

		return post;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Thread [threadId=");
		builder.append(threadId);
		builder.append(", courseId=");
		builder.append(courseId);
		builder.append(", threadContent=");
		builder.append(threadContent);
		builder.append(", threadDate=");
		builder.append(threadDate);
		builder.append(", threadTime=");
		builder.append(threadTime);
		builder.append(", threadTitle=");
		builder.append(threadTitle);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", posts=");
		builder.append(posts != null ? posts.size() : "");
		builder.append(", user=");
		builder.append(user);
		builder.append(", tags=");
		builder.append(tags != null ? tags.size() : "");
		builder.append("]");
		return builder.toString();
	}

}