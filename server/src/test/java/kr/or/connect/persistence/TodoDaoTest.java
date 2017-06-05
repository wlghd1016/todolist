package kr.or.connect.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.TodoApplication;
import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

//@ContextConfiguration(classes = TodoApplication.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodoApplication.class)
@Transactional
public class TodoDaoTest {
	@Autowired
	private TodoDao dao;

	@Test
	public void shouldCount() {
		int count = dao.countTodos();
		System.out.println(count);
	}
	
	@Test
	public void shouldSelectAll() {
		List<Todo> allTodos = dao.selectAll();
		assertThat(allTodos, is(notNullValue()));
		System.out.println(allTodos);
	}
	
	@Test
	public void shouldInsertAndSelect() throws SQLException {


		// given
		Todo todo = new Todo(2, "집에가기");

		// when
		Integer id = dao.insert(todo);

		// then
		Todo selected = dao.selectById(id);
		System.out.println(selected);
		System.out.println(selected.getTodo());
		assertThat(selected.getTodo(), is("집에가기"));
	}

	@Test
	public void shouldDelete() {
		// given
		Todo todo = new Todo(2, "운동");
		Integer id = dao.insert(todo);

		// when
		int affected = dao.deleteById(id);

		// Then
		assertThat(affected, is(1));
	}

	@Test
	public void shouldUpdate() {
		// Given
		Todo todo = new Todo(2, "운동");
		Integer id = dao.insert(todo);

		// When
		todo.setId(id);
		todo.setTodo("팔굽혀펴기");
		int affected = dao.update(todo);

		// Then
		assertThat(affected, is(1));
		Todo updated = dao.selectById(id);
		assertThat(updated.getTodo(), is("팔굽혀펴기"));
	}


}
