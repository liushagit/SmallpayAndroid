package com.ygxhj.smallpay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ygxhj.smallpay.dao.SumDao;
import com.ygxhj.smallpay.dao.SumResult;
import com.ygxhj.smallpay.util.DBHandler;
import com.ygxhj.smallpay.util.SqlUtil;

public class SumDaoImpl implements SumDao{

	@Override
	public int sumByDay(String begin ,String end) {
		int sum = 0;
		sum += sumLTDX(begin, end);
		sum += sumSFYJ(begin, end);
		sum += sumYS(begin, end);
		sum += sumZYHD(begin, end);
		return sum;
	}
	
	private DBHandler<SumResult> SUM_HANDLER = new DBHandler<SumResult>() {

		@Override
		public SumResult handler(ResultSet rs) throws SQLException {
			SumResult result = null;
			if (rs.next()) {
				result = new SumResult();
				result.setSum(rs.getInt("sum"));
			}
			return result;
		}
	};
	
	
	private String selectSum = "select sum(fee) sum from ";
	private String whereSum = " where create_time>? and create_time<? ";
	/**
	 * 朗天电信
	 * @param begin
	 * @param end
	 * @return
	 */
	private int sumLTDX(String begin ,String end){
		StringBuffer sql = new StringBuffer();
		sql.append(selectSum).append(" t_fee_ltdx ").append(whereSum).append(" and result='DELIVRD'");
		SumResult result = SqlUtil.query(sql.toString(), SUM_HANDLER, begin, end);
		return result == null ? 0 :result.getSum();
	}
	
	
	/**
	 * 盛峰远景
	 * @param begin
	 * @param end
	 * @return
	 */
	private int sumSFYJ(String begin ,String end){
		StringBuffer sql = new StringBuffer();
		sql.append(selectSum).append(" t_fee_sfyj ").append(whereSum);
		SumResult result = SqlUtil.query(sql.toString(), SUM_HANDLER, begin, end);
		return result == null ? 0 :result.getSum();
	}
	
	
	/**
	 * 宜搜
	 * @param begin
	 * @param end
	 * @return
	 */
	private int sumYS(String begin ,String end){
		StringBuffer sql = new StringBuffer();
		sql.append(selectSum).append(" t_fee_ys ").append(whereSum);
		SumResult result = SqlUtil.query(sql.toString(), SUM_HANDLER, begin, end);
		return result == null ? 0 :result.getSum() / 100;
	}
	
	
	
	
	
	/**
	 * 掌游互动
	 * @param begin
	 * @param end
	 * @return
	 */
	private int sumZYHD(String begin ,String end){
		StringBuffer sql = new StringBuffer();
		sql.append(selectSum).append(" t_fee_zyhd ").append(whereSum);
		SumResult result = SqlUtil.query(sql.toString(), SUM_HANDLER, begin, end);
		return result == null ? 0 :result.getSum() / 100;
	}

}
