package kr.or.connect.todo.persistence;


import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Todo;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public int countTodos() {
		Map<String, Object> params = Collections.emptyMap();
		System.out.println(params);
		System.out.println(TodoSqls.COUNT_TODO);
		return jdbc.queryForObject(TodoSqls.COUNT_TODO , params, Integer.class);
	}

	public List<Todo> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		System.out.println(TodoSqls.SELECT_ALL);
		return jdbc.query(TodoSqls.SELECT_ALL, params, rowMapper);
	}
	
	public Todo selectById(Integer id) {
		RowMapper< Todo> rowMapper = BeanPropertyRowMapper.newInstance( Todo.class);

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, rowMapper);
	}
	
	public Integer insert(Todo todo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = sdf.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		todo.setDate(ts);
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
}
	
	public int update(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(TodoSqls.UPDATE, params);
	}	

}
