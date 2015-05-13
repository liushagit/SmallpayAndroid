package com.ygxhj.smallpay.linster;

import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ygxhj.smallpay.dao.impl.SumDaoImpl;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SumOkLinster implements OnClickListener{
	private EditText startTime;
	private EditText endTime;
	private EditText text;
	private Calendar cal = Calendar.getInstance();
	public SumOkLinster(EditText startTime,EditText endTime,EditText text){
		this.startTime = startTime;
		this.endTime = endTime;
		this.text = text;
	}
	private SumDaoImpl imp = new SumDaoImpl();
	private ExecutorService executor = Executors.newCachedThreadPool();
	@Override
	public void onClick(View v) {
		int sum = 0,sumMonth = 0,sumLastMonth = 0;
		Future<Integer> futureSe = executor.submit(sumSe);
		Future<Integer> futureSumMonth = executor.submit(sumMonthCall);
		Future<Integer> futureSumLastMonth = executor.submit(sumLastMonthCall);
		try {
			sum = futureSe.get(120, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			sumMonth = futureSumMonth.get(120, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			sumLastMonth = futureSumLastMonth.get(120, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		StringBuffer sb = new StringBuffer();
		sb.append("当前收入：").append(sum).append("元\n");
		sb.append("本月收入：").append(sumMonth).append("元\n");
		sb.append("上月收入：").append(sumLastMonth).append("元\n");
		text.setText(sb.toString());
		
	}
	
	private Callable<Integer> sumSe = new Callable<Integer>() {

		@Override
		public Integer call() throws Exception {
			String begin = startTime.getText().toString();
			String end = endTime.getText().toString();
			return imp.sumByDay(begin, end);
		}
	};
	private Callable<Integer> sumMonthCall = new Callable<Integer>() {
		
		@Override
		public Integer call() throws Exception {
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			StringBuffer begin = new StringBuffer();
			begin.append(year).append("-").append(month + 1).append("-01 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(year).append("-").append(month + 1).append("-31 23:59:59");
			return imp.sumByDay(begin.toString(), end.toString());
		}
	};
	private Callable<Integer> sumLastMonthCall = new Callable<Integer>() {
		
		@Override
		public Integer call() throws Exception {
			int year = cal.get(Calendar.YEAR);
			int month = (cal.get(Calendar.MONTH) + 12 - 1) % 12;
			System.out.println("month===========" + month);
			StringBuffer begin = new StringBuffer();
			begin.append(year).append("-").append(month + 1).append("-01 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(year).append("-").append(month + 1).append("-31 23:59:59");
			return imp.sumByDay(begin.toString(), end.toString());
		}
	};

}
