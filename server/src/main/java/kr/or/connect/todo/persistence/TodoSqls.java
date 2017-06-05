package kr.or.connect.todo.persistence;

public class TodoSqls {
	
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	
	static final String UPDATE =
			"UPDATE todo SET\n"
					+ "completed = :completed,"
					+ "todo = :todo\n"
					+ "WHERE id = :id";
	
	static final String SELECT_ALL =
			"SELECT id, completed, todo, date FROM todo";

	static final String SELECT_BY_ID =
			"SELECT id, Completed, todo, date FROM todo where id = :id";
	
	static final String COUNT_TODO = "SELECT COUNT(*) FROM todo";
	
	
}
