package com.ygxhj.smallpay.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBHandler<T> {

	T handler(ResultSet rs) throws SQLException;
}
