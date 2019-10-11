package com.atguigu.scw.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.scw.common.utils.AppDateUtils;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.config.AlipayConfig;
import com.atguigu.scw.response.vo.OrderConfirmVo;
import com.atguigu.scw.response.vo.ReturnPayConfirmVo;
import com.atguigu.scw.response.vo.TMemberAddress;
import com.atguigu.scw.service.OrderServiceFeign;
import com.atguigu.scw.service.ProjectServiceFeign;
import com.atguigu.scw.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderController {

	@Autowired
	ProjectServiceFeign projectServiceFeign;
	@Autowired
	UserServiceFeign userServiceFeign;
	@Autowired
	OrderServiceFeign orderServiceFeign;

	@GetMapping("/order/notify_url")
	public String notifyUrl(HttpServletRequest request) {

		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名

			// ——请在这里编写您的程序（以下代码仅作参考）——

			/*
			 * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
			 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			 * 4、验证app_id是否为该商户本身。
			 */
			if (signVerified) {// 验证成功
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知
				}

				// out.println("success");

			} else {// 验证失败
					// out.println("fail");
				return "";
				// 调试用，写文本函数记录程序运行情况是否正常
				// String sWord = AlipaySignature.getSignCheckContentV1(params);
				// AlipayConfig.logResult(sWord);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	// 。支付成功的回调方法
	@ResponseBody
	@GetMapping(value = "/order/return_url", produces = "text/html")
	public String returnUrl(HttpSession session, HttpServletRequest request) {

		try {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名

			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (signVerified) {
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 付款金额
				String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

				// 。更新订单状态
				AppResponse<String> response = orderServiceFeign.updateOrderStatus(out_trade_no, 1);
				if (response.getCode() != 10000) {
					return "订单状态更新失败";
				}
				// 。删除域中不需要的数据
				session.removeAttribute("confirmReturn");
				return "trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:"
						+ total_amount;
				// out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
			} else {
				return "验签失败";
				// out.println("验签失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "支付发生异常";
		}
	}

	// 。处理结账请求方法
	@ResponseBody // 。告诉浏览器SprignMvc不要去进行解析
	@PostMapping(value = "/order/checkout", produces = "text/html") // 指定响应体的返回类型
	public String checkout(HttpSession session, OrderConfirmVo vo) {

		// 。获取其他参数
		ReturnPayConfirmVo confirmReturn = (ReturnPayConfirmVo) session.getAttribute("confirmReturn");
		Integer returnid = confirmReturn.getReturnId();
		Map member = (Map) session.getAttribute("member");

		// 。生成唯一的订单编号
		String ordernum = System.currentTimeMillis() + ""
				+ UUID.randomUUID().toString().replace("_", "").substring(0, 10);
		String createdate = AppDateUtils.getFormatTime();// 。生成对应的时间
		Integer status = 0;// 。初始化支付状态0代表已经支付，1代表未支付，2代表支付失败
		Integer projectid = confirmReturn.getProjectId();
		Integer price = confirmReturn.getPrice();
		Integer freight = confirmReturn.getFreight();
		String projectName = confirmReturn.getProjectName();
		String returnContent = confirmReturn.getReturnContent();

		// 。将这些信息全部封装到confirmReturnVo里面
		vo.setProjectid(projectid);
		vo.setStatus(status);
		vo.setCreatedate(createdate);
		vo.setOrdernum(ordernum);
		vo.setReturnid(returnid);
		vo.setMoney(vo.getRtncount() * price + freight);
		vo.setAccessToken((String) member.get("accessToken"));
		// 。在用户支付前将所有数据存到数据库中去，以遍持久化保存，此时的支付状态为0 未支付
		AppResponse<String> appResponse = orderServiceFeign.createOrder(vo);
		if (appResponse.getCode() != 10000) {
			return "error/order_error";
		}
		// 。获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = vo.getOrdernum();
		// 付款金额，必填
		String total_amount = vo.getRtncount() * price + freight + "";// 。使用订单提交的参数来计算得出
		// 订单名称，必填
		String subject = projectName;// 。使用订单的名称
		// 商品描述，可空
		String body = returnContent;// 。使用订单的回报内容

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		// alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
		// + "\"total_amount\":\""+ total_amount +"\","
		// + "\"subject\":\""+ subject +"\","
		// + "\"body\":\""+ body +"\","
		// + "\"timeout_express\":\"10m\","
		// + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		// 请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

		// 请求

		String result = "";

		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 。用户支付成功了，则将支付状态修改为1 已经支付

		System.out.println("接收到的订单信息为：" + confirmReturn);

		return result;
	}

	@GetMapping("/order/payStep1/{returnId}")
	public String payStep1(HttpSession session, @PathVariable("returnId") Integer returnId) {
		// 查询订单详情
		AppResponse<ReturnPayConfirmVo> response = projectServiceFeign.returnInfo(returnId);
		// 保存到域中
		session.setAttribute("confirmReturn", response.getData());
		log.info("查询到的返回值数据为：" + response.getData());
		// 跳转至页面
		return "order/pay-step-1.html";
	}

	@GetMapping("/order/paystep2/{count}")
	public String payStep2(@RequestHeader("referer") String referer, Model model, HttpSession session,
			@PathVariable("count") Integer count) {
		// 。获取session中保存的确认回报信息，
		ReturnPayConfirmVo vo = (ReturnPayConfirmVo) session.getAttribute("confirmReturn");
		Integer totalPrice = vo.getFreight() + vo.getPrice() * vo.getNum();
		vo.setTotalPrice(new BigDecimal(totalPrice + ""));
		vo.setNum(count);

		// 。获取当前用户的收货地址
		Map member = (Map) session.getAttribute("member");
		if (member == null) {
			// 。获取refer头信息，将头信息保存到session域中去
			session.setAttribute("path", referer);

			// 。未登录
			model.addAttribute("errorMsg", "结账操作必须登录");

			return "user/login";
		}
		// 。已经登录，获取地址信息
		AppResponse<List<TMemberAddress>> response = userServiceFeign.addressInfo((String) member.get("accessToken"));
		model.addAttribute("addresses", response.getData());

		log.info("查询到的信息为 ：" + response.getData());

		// 。跳转到下一步的页面
		return "order/pay-step-2.html";

	}

}
