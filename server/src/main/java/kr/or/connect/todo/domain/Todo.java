package kr.or.connect.todo.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Todo {
	private Integer id;
	private Integer completed;
	private String todo;
	private Timestamp date;
	
	public Todo() {
	}

	public Todo(Integer completed, String todo ) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Calendar cal = Calendar.getInstance();
	String today = null;
	today = sdf.format(cal.getTime());
	Timestamp ts = Timestamp.valueOf(today);
	
	this.completed = completed;
	this.todo = todo;
	this.date = ts;
}
	
	public Todo(Integer id, Integer completed, String todo ) {
		this(completed, todo);
		this.id = id;
	}


	@Override
	public String toString() {
		return "Todo [id=" + id + ", completed=" + completed + ", todo=" + todo + ", date=" + date + ", toString()="
				+ super.toString() + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
}
