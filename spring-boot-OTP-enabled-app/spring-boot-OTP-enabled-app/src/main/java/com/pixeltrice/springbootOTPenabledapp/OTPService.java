package com.pixeltrice.springbootOTPenabledapp;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OTPService {

	private static final Integer EXPIRE_MINS = 1;//We are assigning the Expiry time of OTP to 1 minutes.
    private LoadingCache<String, Integer> otpCache;//With the use of LoadingCache, the values are automatically loaded as a cache which stored on your local system.
    												//Caches stored in the form of key-value pairs.
    public OTPService(){
      super();
      //we storing the caches as a username is key and OTP as a value
      otpCache = CacheBuilder.newBuilder().
      expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
      .build(new CacheLoader<String, Integer>() {
				      public Integer load(String key) {
				             return 0;
				       }
				   });
      //The above code will store the expiry time of OTP as well,
      //and we already declared the EXPIRE_MINS = 1,
      //which means after 4 minutes the stored OTP will not be valid.
 }
	//This method is used to push the opt number against Key(username). Rewrite the OTP if username exists
	 //Using user id  as key
	public int generateOTP(String username){
		Random random = new Random();
		//// Generate random integers in range 0 to 899999
		int otp = 100000 + random.nextInt(900000);//
		// 6 digit otp  will generate
		otpCache.put(username, otp);
		return otp;
	 }
	//This method is used to return the OPT number against Key->Key values is username
	 public int getOtp(String username){ 
		try{
		 return otpCache.get(username); 
		}catch (Exception e){
		 return 0; 
		}
	 } 
	//This method is used to clear the OTP catched already
	public void clearOTP(String key){ 
		otpCache.invalidate(key);
	 }
	//Note: You can see your cache files on the following path for windows 
	//C:\Users\Username\AppData\Local\Google\Chrome\User Data\Default\Cache
}
