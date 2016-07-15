package io.mycat.sql.dialect.mysql.insert;

import java.sql.SQLNonTransientException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.ast.SQLExpr;

public class DefaultPartionKeyUpdateHandler implements PartionKeyUpdateHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPartionKeyUpdateHandler.class);

	@Override
	public void handle(Iterator<SQLExpr> it, String tableName, String partionKey) throws SQLNonTransientException {
		String msg = "partion key can't be updated: " + tableName + " -> " + partionKey;
		LOGGER.warn(msg);
		throw new SQLNonTransientException(msg);
	}

}
