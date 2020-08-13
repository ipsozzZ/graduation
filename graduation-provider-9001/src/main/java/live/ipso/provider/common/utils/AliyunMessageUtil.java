package live.ipso.provider.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import live.ipso.provider.common.enums.RedisEnum;
import live.ipso.provider.exception.SystemException;


/**
 * 阿里云短信服务工具类
 * @author ipso
 * 2020-01-04
 */
public class AliyunMessageUtil {

   /**
    * 发送短信
    * @param  mobile 手机号
    * @param  num 生成验证码的长度
    * @return SendSmsResponse
    */
   public static String sendSms(String mobile, Integer num) {
      // 设置超时时间-可自行调整
      System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
      System.setProperty("sun.net.client.defaultReadTimeout", "10000");

      // 初始化ascClient需要的几个参数
      final String product = "Dysmsapi"; // 短信API产品名称（短信产品名固定，无需修改）
      final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）

      // 替换成你的AK
      final String yuoAccessKeyId     = "";
      final String yuoAccessKeySecret = "";
      final String yuoTemplate_code   = "";
      final String yuoSign_name       = "";


      try {
         // 初始化ascClient,暂时不支持多region（请勿修改）
         IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", yuoAccessKeyId, yuoAccessKeySecret);
         DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

         IAcsClient acsClient = new DefaultAcsClient(profile);

         // 组装阿里云短信服务请求对象
         SendSmsRequest request = new SendSmsRequest();
         // 使用post提交
         request.setMethod(MethodType.POST);
         // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
         request.setPhoneNumbers(mobile);
         // 短信签名-可在短信控制台中找到
         request.setSignName(yuoSign_name);
         // 短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
         request.setTemplateCode(yuoTemplate_code);

         // 验证码，使用json字符串的方式传递短信模板参数
         String param = createRandomNum(num);
         JSONObject jsonParam = new JSONObject();
         jsonParam.put("number", param);

         // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
         // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
         request.setTemplateParam(jsonParam.toJSONString());

         // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
         // request.setSmsUpExtendCode("90997");
         // 请求失败这里会抛ClientException异常
         SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
         if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
            // 将验证码加入缓存，并设置10分钟(600秒)后过期
            String redis_key = RedisEnum.SHORTMESSAGE_CACHE_PREFIX.getCode() + mobile;
            RedisUtil.set(redis_key, param, 600000L);
            return param; // 由于redis原因将短信验证交给前端
         }
         return null;
      } catch (ClientException e) {
         e.printStackTrace();
      }
      throw new SystemException("1小时只允许发送5次验证码");
   }

   /**
    * 判断短信验证码是否过期
    * @param  mobile 接收短信的手机号码
    * @param  code   短信验证码
    * @return Boolean
    */
   public static Boolean isExpire(String mobile, String code){
      String redis_key = RedisEnum.SHORTMESSAGE_CACHE_PREFIX.getCode() + mobile;
      Long getExpire = RedisUtil.getExpire(redis_key);
      String redis_value = "";
      redis_value = (String) RedisUtil.get(redis_key);

      // 判断key是否存在
      if (RedisUtil.get(redis_key) == null)
         return false;

      // 判断缓存是否过期
      if (getExpire < 0)
         return false;

      // 判断验证码是否正确
      assert redis_value != null;
      return redis_value.equals(code);
   }

   /**
    * 生成随机数
    * @param  num 位数
    * @author ipso
    * @return String
    */
   private static String createRandomNum(int num){
      String randomNumStr = "";
      for(int i = 0; i < num;i ++){
         int randomNum = (int)(Math.random() * 10);
         randomNumStr += randomNum;
      }
      return randomNumStr;
   }
}
