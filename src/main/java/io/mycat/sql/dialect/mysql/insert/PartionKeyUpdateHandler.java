package io.mycat.sql.dialect.mysql.insert;

import java.sql.SQLNonTransientException;
import java.util.Iterator;

import com.alibaba.druid.sql.ast.SQLExpr;

public interface PartionKeyUpdateHandler {
	
	public void handle(Iterator<SQLExpr> it, String tableName, String partionKey) throws SQLNonTransientException;

}
