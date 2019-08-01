package com.util;

/**
 * 令牌桶限流算法
 */
public class TokenBudget {

	private int bucketNums = 100 ;		// 桶容量
	private int rate = 1 ;				// 流速
	private int nowTokens ;				// 当前令牌数量
	private long timestamp = getNowTime() ;

	private long getNowTime(){
		return System.currentTimeMillis() ;
	}

	private int min(int tokens){
		return bucketNums > tokens ? tokens : bucketNums ;
	}

	public boolean getToken(){
		// 记录来拿令牌的时间
		long nowTime = getNowTime() ;
		// 添加令牌（判断有多少个令牌）
		nowTokens += (int)((nowTime - timestamp) * rate) ;
		// 获取小的
		nowTokens = min(nowTokens) ;
		// 修改时间戳为拿令牌的时间
		timestamp = nowTime ;
		// 判断令牌是否足够长
		if(nowTokens < 1){
			return false ;
		}else{
			nowTokens -= 1 ;
			return true ;
		}
	}

	public static void main(String[] args) {
		TokenBudget t = new TokenBudget() ;
		System.out.println(t.getToken()) ;
	}
}
