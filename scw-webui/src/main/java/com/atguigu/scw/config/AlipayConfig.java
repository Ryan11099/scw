package com.atguigu.scw.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
		public static String app_id = "2016101200671263";
		
		// 商户私钥，您的PKCS8格式RSA2私钥
	    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6Cy6ImUQZ0BusSX8wdu9k/+3JYO6dCvRYMbQOJfM7PGGV1J7j4JhRYy73jRb0yD7ntxSW8WmUNzSGBdBuxMv1ldFAw6J03/th3zBjPf+/0lKQTjCXGZAjS0QSwtUwfAeKAGWJBYgwA9yp1Z/zMTT3wVCvIRWoH19ufqPc8YB98f8A2WE1phHocGwNGuPSHpFiz2liJz04W2X2aABlpVaHH8rfslPMA1tedZ1rkqJCGFDNQ4f4h3aR/9Ia4qRBuJ/UvNXPgKYJ8PKOFAPEpiNZbrSK2YF/ClvPqs4p7119erqqFUoDxgoIKymkLc9nuaRD3+i5X3aua8spqRV3DI5NAgMBAAECggEATyOOieDYjTpvaxxglFcjgo0zs2QeXINaaWHLO/F8xAftYvTtGfZiFMziH7/OshW0XJEOmx7aUIgjOZejkFr6f99ZAfm2U40ru/ha9vxanFh1cpkwJjZjj//mVyy1z0gTVOrSRjUqN3KNHVmYDrUx2OpMzhJ+yeN+jtwkDEpW5DugZY6s4Oh5SucQQuY51XVb2odb6gfTrSlq81RLS1p7XMlyPEYNsCCKykqDKNGzMA51FST4PoYCH0Y9I0fniLEzoSdpZQGfzxpIuiItKgv038vA7wlUyMEzySUq5QJunx22TeNzsT/bRTYm1uQ+lWWZrNkyAN0A7yONDQ6GWtjqgQKBgQDuBNBAPSwaCNkjZq+5RBbMa3PPxX/yBTKInjOXV84T17aSiZH3yWyZADBVYmEwELZDVI5kH0AFb/j2rX2rHhxCmh8NCmK1fGHmUINukQiFjvqvT8vkRMjtKRX2Um3v1GhKEDx7Em5EKDMdCzoKLDgvv34/zX1XrGrW3Gjy1JAp/QKBgQDIGTDLdqxhXzYQMbMr6rFiH3UbIfkGIrZVuZ+fsgOk1qH/XlVO5TOc0i7mCt/5wKz+I8iv0ZTdrlZzUP3OLRlgdURWjLTX6vdPySw9J3voIzo3fY2WmMQLXMLB0dY1Prs57gmljuzOXk/pEYzRIwEM6XCs2eTcBtRXmYb+5pe+kQKBgQCNp9e5I68Hn5R7eTXD8T4QUzMNyOEozOcSX/UccWl4bb1+4Nk6u2oidPZPGsn0rT2MPNqxw9d6ZPWq+f38xWLUUJ2CSVgN4wTw6aIPP8nukvWnOLqEFFvwqa8kdlJvld6rlZYpOQLfFX22H2RfgYm/ARzGJCog78B2N59ORiu2kQKBgBQjlOmldAwIzSQsHGBbKqc5i0vDy5B2hety10H3ZK5+e6d8ExHxt5QdA2NbzNcSMVcSuuwXJ+h313Obvy+1EkzHAK3FU543o9R/5tfw1WOzx/Q1yr5Recm6+E8Rk4GgYq+d6ZKkI+lRTLGkdovF/iESjaiX9ZS/izeV2CRniPZBAoGATJvMtAXlg1JjdK1eIJ6aXMTTV4Uovc4nKNXXmv1qcR7zkXcvyxjGR77X5OEFwq6D2xzZx3S9xdHBHG+B/RfJUEKVO6wkTbScHI+SEqmKRWxQLBlrsfyDR1UAy/GIcEiRV0CNtZMcMiQ64MkV0VU6T4rf5AJ2e6KW8W2ud+d7pz4=";
		
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjgJNAeydx2DuDl07YAEEwAZvXgDyf1ZiXAv+8IED2zBXdwoxg2uIoVl5+mivnZH1OrOnVXQ9t/fOC/pAIb+jN+b8Cv/T7qQfWopBqzOsuNVVYLEz6Gek7xd9J1HdiMsrSSbpJ1DDxtn5xQxhQi5EhhBa8f8QSOs5R+9GJZQlQk3F0rwwrGKygFuXcip8GfQtHuh+dd20ztjwfREcpnlNumJl5R+NcjdBvbYa8TSAyUzNvkd2MYveNjetrP1E+9VIE89/qI7WCtxSxfA6qae2IaeUC8HayvearQiNQFE6bgOlv1ADK96CS2eb8oWaLPC5J+Qsq1e0Z63aUXzZcwQQowIDAQAB";

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://269n5c7124.qicp.vip/order/notify_url";
		//http://269n5c7124.qicp.vip/
		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://269n5c7124.qicp.vip/order/return_url";

		// 签名方式
		public static String sign_type = "RSA2";
		
		// 字符编码格式
		public static String charset = "utf-8";
		
		// 支付宝网关（如果是测试一定要加上dev）
		public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
		
		// 保存日志的地址
		public static String log_path = "F:\\";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	    /** 
	     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	     * @param sWord 要写入日志里的文本内容
	     */
	    public static void logResult(String sWord) {
	        FileWriter writer = null;
	        try {
	            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
	            writer.write(sWord);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	}